package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import bean.Student;
import bean.Subject;
import bean.Teacher;
import bean.TestListStudent;
import dao.ClassNumDao;
import dao.StudentDao;
import dao.SubjectDao;
import dao.TestListStudentDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestListStudentExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // 選択肢を再セット（エラー時に画面を再表示するため）
        int year = LocalDate.now().getYear();
        List<Integer> entYearSet = new ArrayList<>();
        for (int i = year; i >= year - 10; i--) {
            entYearSet.add(i);
        }
        ClassNumDao classNumDao = new ClassNumDao();
        List<String> classNumSet = classNumDao.filter(teacher.getSchool());
        SubjectDao subjectDao = new SubjectDao();
        List<Subject> subjectSet = subjectDao.filter(teacher.getSchool());

        req.setAttribute("ent_year_set", entYearSet);
        req.setAttribute("class_num_set", classNumSet);
        req.setAttribute("subject_set", subjectSet);

        // フォームから学生番号を取得
        String studentNo = req.getParameter("student_no");

        // 未入力チェック
        if (studentNo == null || studentNo.trim().isEmpty()) {
            req.setAttribute("student_error", "このフィールドを入力してください");
            req.getRequestDispatcher("test_list.jsp").forward(req, res);
            return;
        }

        // 学生情報を取得
        StudentDao studentDao = new StudentDao();
        Student student = studentDao.get(studentNo);

        if (student == null) {
            req.setAttribute("student_error", "学生情報が存在しませんでした");
            req.getRequestDispatcher("test_list.jsp").forward(req, res);
            return;
        }

        // 学生のschoolをセット
        student.setSchool(teacher.getSchool());

        // DB検索
        TestListStudentDao dao = new TestListStudentDao();
        List<TestListStudent> testList = dao.filter(student);

        if (testList.isEmpty()) {
            req.setAttribute("student_error", "成績情報が存在しませんでした");
            req.getRequestDispatcher("test_list.jsp").forward(req, res);
            return;
        }

        req.setAttribute("test_list_student", testList);
        req.setAttribute("student", student);
        req.setAttribute("student_no", studentNo);

        req.getRequestDispatcher("test_list_student.jsp").forward(req, res);
    }
}