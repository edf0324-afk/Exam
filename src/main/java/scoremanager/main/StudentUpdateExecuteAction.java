package scoremanager.main;

import bean.Student;
import bean.Teacher;
import dao.StudentDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class StudentUpdateExecuteAction extends Action {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // フォームから値を受け取る
        String no = req.getParameter("no");
        String name = req.getParameter("name");
        int entYear = Integer.parseInt(req.getParameter("ent_year"));
        String classNum = req.getParameter("class_num");
        // チェックボックスは未チェックだとnullになるので null チェック
        boolean isAttend = req.getParameter("is_attend") != null;

        // Studentインスタンスを作成して値をセット
        Student student = new Student();
        student.setStudentNo(no);
        student.setStudentName(name);
        student.setEntYear(entYear);
        student.setClassNum(classNum);
        student.setAttend(isAttend);
        student.setSchool(teacher.getSchool());

        // DBに保存（save は既存レコードがあれば UPDATE）
        StudentDao sDao = new StudentDao();
        sDao.save(student);

        // 完了後、学生一覧画面へリダイレクト
        res.sendRedirect("StudentList.action");
    }
}