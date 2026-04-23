package scoremanager.main;

import bean.Student;
import bean.Teacher;
import dao.StudentDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class StudentCreateExecuteAction extends Action {
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		HttpSession session = req.getSession();
		Teacher teacher = (Teacher) session.getAttribute("user");

		// JSPのフォーム（name="属性名"）から値を受け取る
		String no = req.getParameter("no");
		String name = req.getParameter("name");
		int entYear = Integer.parseInt(req.getParameter("ent_year"));
		String classNum = req.getParameter("class_num");

		// Studentインスタンスを作成し、値をセットする
		// DAOの定義に合わせて setStudentNo, setStudentName を使用
		Student student = new Student();
		student.setStudentNo(no);
		student.setStudentName(name);
		student.setEntYear(entYear);
		student.setClassNum(classNum);
		student.setAttend(true); // 新規登録時は「在学中」
		student.setSchool(teacher.getSchool());

		// 提供された StudentDao を使って保存を実行
		StudentDao sDao = new StudentDao();
		// saveメソッドが自動で INSERT か UPDATE かを判断してくれる
		sDao.save(student);

		// 登録完了後、学生一覧画面へ戻る
		res.sendRedirect("StudentList.action");
	}
}