package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.Student;
import bean.Subject;
import bean.Teacher;
import bean.Test;
import dao.ClassNumDao;
import dao.StudentDao;
import dao.SubjectDao;
import dao.TestDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestRegistAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        int year = LocalDate.now().getYear();
        List<Integer> entYearSet = new ArrayList<>();
        for (int i = year; i >= year - 10; i--) {
            entYearSet.add(i);
        }

        ClassNumDao classNumDao = new ClassNumDao();
        List<String> classNumSet = classNumDao.filter(teacher.getSchool());

        SubjectDao subjectDao = new SubjectDao();
        List<Subject> subjectSet = subjectDao.filter(teacher.getSchool());

        List<Integer> noSet = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            noSet.add(i);
        }

        req.setAttribute("ent_year_set", entYearSet);
        req.setAttribute("class_num_set", classNumSet);
        req.setAttribute("subject_set", subjectSet);
        req.setAttribute("no_set", noSet);

        String entYearStr = req.getParameter("ent_year");
        String classNum = req.getParameter("class_num");
        String subjectCd = req.getParameter("subject_cd");
        String noStr = req.getParameter("no");

        if (entYearStr != null && !entYearStr.isEmpty()
                && classNum != null && !classNum.isEmpty()
                && subjectCd != null && !subjectCd.isEmpty()
                && noStr != null && !noStr.isEmpty()) {

            int entYear = Integer.parseInt(entYearStr);
            int no = Integer.parseInt(noStr);
            Subject subject = subjectDao.get(subjectCd, teacher.getSchool());

            StudentDao studentDao = new StudentDao();
            List<Student> students = studentDao.filter(teacher.getSchool(), entYear, classNum, true);

            // 既存の点数を取得してMapに格納
            TestDao testDao = new TestDao();
            Map<String, Integer> existingPoints = new HashMap<>();
            for (Student student : students) {
                Test existing = testDao.get(student, subject, teacher.getSchool(), no);
                if (existing != null) {
                    existingPoints.put(student.getStudentNo(), existing.getPoint());
                }
            }

            req.setAttribute("students", students);
            req.setAttribute("existing_points", existingPoints);
            req.setAttribute("subject", subject);
            req.setAttribute("ent_year", entYear);
            req.setAttribute("class_num", classNum);
            req.setAttribute("no", no);
        } else if (entYearStr != null) {
            req.setAttribute("error", "入学年度とクラスと科目と回数を選択してください");
        }

        req.getRequestDispatcher("test_regist.jsp").forward(req, res);
    }
}