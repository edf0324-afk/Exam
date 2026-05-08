package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import bean.Subject;
import bean.Teacher;
import dao.ClassNumDao;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestListAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // 入学年度の選択肢
        int year = LocalDate.now().getYear();
        List<Integer> entYearSet = new ArrayList<>();
        for (int i = year; i >= year - 10; i--) {
            entYearSet.add(i);
        }

        // クラス番号の選択肢
        ClassNumDao classNumDao = new ClassNumDao();
        List<String> classNumSet = classNumDao.filter(teacher.getSchool());

        // 科目の選択肢
        SubjectDao subjectDao = new SubjectDao();
        List<Subject> subjectSet = subjectDao.filter(teacher.getSchool());

        req.setAttribute("ent_year_set", entYearSet);
        req.setAttribute("class_num_set", classNumSet);
        req.setAttribute("subject_set", subjectSet);

        req.getRequestDispatcher("test_list.jsp").forward(req, res);
    }
}