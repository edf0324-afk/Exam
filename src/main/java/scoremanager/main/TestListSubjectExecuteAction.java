package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import bean.Subject;
import bean.Teacher;
import bean.TestListSubject;
import dao.ClassNumDao;
import dao.SubjectDao;
import dao.TestListSubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestListSubjectExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // 選択肢を再セット（エラー時に画面を再表示するため）
        int year = LocalDate.now().getYear();
        List<Integer> entYearSet = new ArrayList<>();
        for (int i = year; i >= year - 10; i--) {
            entYearSet.add(i);
        }
        ClassNumDao classNumDao = new ClassNumDao();
        List<String> classNumSet = classNumDao.filter(teacher.getSchool());
        SubjectDao subjectDao = new SubjectDao();
        List<Subject> subjectSet = subjectDao.filter(teacher.getSchool());

        req.setAttribute("ent_year_set", entYearSet);
        req.setAttribute("class_num_set", classNumSet);
        req.setAttribute("subject_set", subjectSet);

        // フォームから値を取得
        String entYearStr = req.getParameter("ent_year");
        String classNum = req.getParameter("class_num");
        String subjectCd = req.getParameter("subject_cd");

        // 未入力チェック
        if (entYearStr == null || entYearStr.isEmpty()
                || classNum == null || classNum.isEmpty()
                || subjectCd == null || subjectCd.isEmpty()) {
            req.setAttribute("error", "入学年度とクラスと科目を選択してください");
            req.getRequestDispatcher("test_list.jsp").forward(req, res);
            return;
        }

        int entYear = Integer.parseInt(entYearStr);
        Subject subject = subjectDao.get(subjectCd, teacher.getSchool());

        // DB検索
        TestListSubjectDao dao = new TestListSubjectDao();
        List<TestListSubject> testList = dao.filter(entYear, classNum, subject, teacher.getSchool());

        if (testList.isEmpty()) {
            req.setAttribute("error", "学生情報が存在しませんでした");
            req.getRequestDispatcher("test_list.jsp").forward(req, res);
            return;
        }

        // 回数の最大値を取得（表のヘッダー用）
        int maxNo = 0;
        for (TestListSubject t : testList) {
            for (int k : t.getPoints().keySet()) {
                if (k > maxNo) maxNo = k;
            }
        }
        if (maxNo == 0) maxNo = 10;

        req.setAttribute("test_list", testList);
        req.setAttribute("subject", subject);
        req.setAttribute("ent_year", entYear);
        req.setAttribute("class_num", classNum);
        req.setAttribute("max_no", maxNo);

        req.getRequestDispatcher("test_list_subject.jsp").forward(req, res);
    }
}