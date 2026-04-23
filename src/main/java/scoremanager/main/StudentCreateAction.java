package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import bean.Teacher;
import dao.ClassNumDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class StudentCreateAction extends Action {
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		HttpSession session = req.getSession();
		Teacher teacher = (Teacher) session.getAttribute("user");

		// 1. 入学年度の選択肢（現在の年から10年前まで）
		LocalDate now = LocalDate.now();
		int year = now.getYear();
		List<Integer> entYearSet = new ArrayList<>();
		for (int i = year; i >= year - 10; i--) {
			entYearSet.add(i);
		}

		// 2. クラス番号の選択肢をDBから取得
		// ここでエラーが出た場合も FrontController のエラー処理に飛ぶようになります
		ClassNumDao cDao = new ClassNumDao();
		List<String> classNumSet = cDao.filter(teacher.getSchool());

		// 3. リクエスト属性にセット
		req.setAttribute("ent_year_set", entYearSet);
		req.setAttribute("class_num_set", classNumSet);

		// 4. JSPへフォワード（画像の通りパスはあっています）
		req.getRequestDispatcher("student_create.jsp").forward(req, res);
	}
}