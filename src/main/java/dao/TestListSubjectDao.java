package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Subject;
import bean.TestListSubject;

public class TestListSubjectDao extends Dao {

    public List<TestListSubject> postFilter(ResultSet rs) throws Exception {
        List<TestListSubject> list = new ArrayList<>();
        String currentStudentNo = null;
        TestListSubject current = null;

        while (rs.next()) {
            String studentNo = rs.getString("student_no");

            if (!studentNo.equals(currentStudentNo)) {
                current = new TestListSubject();
                current.setStudentNo(studentNo);
                current.setStudentName(rs.getString("student_name"));
                current.setEntYear(rs.getInt("ent_year"));
                current.setClassNum(rs.getString("class_num"));
                list.add(current);
                currentStudentNo = studentNo;
            }

            int testNo = rs.getInt("test_no");
            if (testNo > 0) {
                current.putPoint(testNo, rs.getInt("point"));
            }
        }
        return list;
    }

    public List<TestListSubject> filter(int entYear, String classNum, Subject subject, School school) throws Exception {
        List<TestListSubject> list = new ArrayList<>();
        Connection connection = getConnection();
        PreparedStatement statement = null;

        try {
        	String sql =
        		    "select s.student_no, s.student_name, s.ent_year, s.class_num, " +
        		    "t.no as test_no, t.point " +
        		    "from student s " +
        		    "left join test t on s.student_no = t.student_no and s.school_cd = t.school_cd and t.subject_cd = ? " +
        		    "where s.ent_year = ? and s.class_num = ? and s.school_cd = ? and s.is_attend = true " +
        		    "order by student_no asc";

            statement = connection.prepareStatement(sql);
            statement.setString(1, subject.getCd());
            statement.setInt(2, entYear);
            statement.setString(3, classNum);
            statement.setString(4, school.getSchoolCd());
            ResultSet rs = statement.executeQuery();
            list = postFilter(rs);
        } catch (Exception e) {
            throw e;
        } finally {
            closeResources(statement, connection);
        }

        return list;
    }

    private void closeResources(PreparedStatement statement, Connection connection) throws SQLException {
        if (statement != null) {
            try { statement.close(); } catch (SQLException sqle) { throw sqle; }
        }
        if (connection != null) {
            try { connection.close(); } catch (SQLException sqle) { throw sqle; }
        }
    }
}