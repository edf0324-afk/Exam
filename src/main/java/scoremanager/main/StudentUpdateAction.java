package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import bean.Student;
import bean.Teacher;
import dao.ClassNumDao;
import dao.StudentDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class StudentUpdateAction extends Action {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // URLパラメーター ?no=xxx から学生番号を取得
        String no = req.getParameter("no");

        // DBから学生情報を取得
        StudentDao sDao = new StudentDao();
        Student student = sDao.get(no);

        // 入学年度の選択肢（現在の年から10年前まで）
        LocalDate now = LocalDate.now();
        int year = now.getYear();
        List<Integer> entYearSet = new ArrayList<>();
        for (int i = year; i >= year - 10; i--) {
            entYearSet.add(i);
        }

        // クラス番号の選択肢をDBから取得
        ClassNumDao cDao = new ClassNumDao();
        List<String> classNumSet = cDao.filter(teacher.getSchool());

        // リクエスト属性にセット
        req.setAttribute("student", student);
        req.setAttribute("ent_year_set", entYearSet);
        req.setAttribute("class_num_set", classNumSet);

        // JSPへフォワード
        req.getRequestDispatcher("student_update.jsp").forward(req, res);
    }
}