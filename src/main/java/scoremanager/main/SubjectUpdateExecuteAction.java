package scoremanager.main;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class SubjectUpdateExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // フォームから値を受け取る
        String cd = req.getParameter("cd");
        String name = req.getParameter("name");

        // Subjectインスタンスを作成して値をセット
        Subject subject = new Subject();
        subject.setCd(cd);
        subject.setName(name);
        subject.setSchool(teacher.getSchool());

        // DBに保存
        SubjectDao subjectDao = new SubjectDao();
        subjectDao.save(subject);

        // 変更した科目情報を完了画面用にセット
        req.setAttribute("subject", subject);

        // 変更完了画面へフォワード
        req.getRequestDispatcher("subject_update_complete.jsp").forward(req, res);
    }
}