package scoremanager.main;

import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Teacher;
import bean.Test;
import dao.StudentDao;
import dao.SubjectDao;
import dao.TestDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestRegistExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");
        School school = teacher.getSchool();

        int entYear = Integer.parseInt(req.getParameter("ent_year"));
        String classNum = req.getParameter("class_num");
        String subjectCd = req.getParameter("subject_cd");
        int no = Integer.parseInt(req.getParameter("no"));

        SubjectDao subjectDao = new SubjectDao();
        Subject subject = subjectDao.get(subjectCd, school);

        StudentDao studentDao = new StudentDao();
        List<Student> students = studentDao.filter(school, entYear, classNum, true);

        List<Test> testList = new ArrayList<>();
        boolean hasError = false;

        for (Student student : students) {
            String pointStr = req.getParameter("point_" + student.getStudentNo());

            // ブランクの場合は保存しない
            if (pointStr == null || pointStr.isEmpty()) {
                continue;
            }

            int point = Integer.parseInt(pointStr);

            if (point < 0 || point > 100) {
                hasError = true;
                break;
            }

            Test test = new Test();
            test.setStudent(student);
            test.setSubject(subject);
            test.setSchool(school);
            test.setClassNum(classNum);
            test.setNo(no);
            test.setPoint(point);
            testList.add(test);
        }

        if (hasError) {
            req.setAttribute("error", "0〜100の範囲で入力してください");
            req.setAttribute("students", students);
            req.setAttribute("subject", subject);
            req.setAttribute("ent_year", entYear);
            req.setAttribute("class_num", classNum);
            req.setAttribute("no", no);
            req.getRequestDispatcher("test_regist.jsp").forward(req, res);
            return;
        }

        if (!testList.isEmpty()) {
            TestDao testDao = new TestDao();
            testDao.save(testList);
        }

        req.setAttribute("tests", testList);
        req.setAttribute("subject", subject);
        req.setAttribute("ent_year", entYear);
        req.setAttribute("class_num", classNum);
        req.setAttribute("no", no);
        req.getRequestDispatcher("test_regist_complete.jsp").forward(req, res);
    }
}