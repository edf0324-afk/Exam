package scoremanager.main;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class SubjectDeleteExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // フォームから科目コードを受け取る
        String cd = req.getParameter("cd");

        // 完了画面用に削除前の情報を取得しておく
        SubjectDao subjectDao = new SubjectDao();
        Subject subject = subjectDao.get(cd, teacher.getSchool());

        // DBから削除
        subjectDao.delete(subject);

        // 削除した科目情報を完了画面用にセット
        req.setAttribute("subject", subject);

        // 削除完了画面へフォワード
        req.getRequestDispatcher("subject_delete_complete.jsp").forward(req, res);
    }
}