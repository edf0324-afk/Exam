package scoremanager.main;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class SubjectUpdateAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // URLパラメーター ?cd=xxx から科目コードを取得
        String cd = req.getParameter("cd");

        // DBから科目情報を取得
        SubjectDao subjectDao = new SubjectDao();
        Subject subject = subjectDao.get(cd, teacher.getSchool());

        // リクエストにセット
        req.setAttribute("subject", subject);

        // JSPへフォワード
        req.getRequestDispatcher("subject_update.jsp").forward(req, res);
    }
}