package scoremanager.main;

import java.util.List;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class SubjectListAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // ログインユーザーの学校に紐づく科目一覧を取得
        SubjectDao subjectDao = new SubjectDao();
        List<Subject> subjects = subjectDao.filter(teacher.getSchool());

        // リクエストにセット
        req.setAttribute("subjects", subjects);

        // JSPへフォワード
        req.getRequestDispatcher("subject_list.jsp").forward(req, res);
    }
}