package scoremanager.main;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class SubjectCreateExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // フォームから値を受け取る
        String cd = req.getParameter("cd");
        String name = req.getParameter("name");

        // 科目コードの文字数チェック（3文字固定）
        if (cd == null || cd.length() != 3) {
            req.setAttribute("cdError", "科目コードは3文字で入力してください");
            req.setAttribute("cd", cd);
            req.setAttribute("name", name);
            req.getRequestDispatcher("subject_create.jsp").forward(req, res);
            return;
        }

        // 科目コードの重複チェック
        SubjectDao subjectDao = new SubjectDao();
        Subject existing = subjectDao.get(cd, teacher.getSchool());
        if (existing != null) {
            req.setAttribute("cdError", "科目コードが重複しています");
            req.setAttribute("cd", cd);
            req.setAttribute("name", name);
            req.getRequestDispatcher("subject_create.jsp").forward(req, res);
            return;
        }

        // Subjectインスタンスを作成して値をセット
        Subject subject = new Subject();
        subject.setCd(cd);
        subject.setName(name);
        subject.setSchool(teacher.getSchool());

        // DBに保存
        subjectDao.save(subject);

        // 登録した科目情報を完了画面用にセット
        req.setAttribute("subject", subject);

        // 登録完了画面へフォワード
        req.getRequestDispatcher("subject_create_complete.jsp").forward(req, res);
    }
}