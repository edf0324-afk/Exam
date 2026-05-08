package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.Student;
import bean.TestListStudent;

public class TestListStudentDao extends Dao {

    private String baseSql =
        "select t.no, t.point, s.subject_name, s.subject_cd " +
        "from test t " +
        "inner join subject s on t.subject_cd = s.subject_cd and t.school_cd = s.school_cd " +
        "where ";

    public List<TestListStudent> postFilter(ResultSet rs) throws Exception {
        List<TestListStudent> list = new ArrayList<>();
        while (rs.next()) {
            TestListStudent tls = new TestListStudent();
            tls.setSubjectName(rs.getString("subject_name"));
            tls.setSubjectCd(rs.getString("subject_cd"));
            tls.setNum(rs.getInt("no"));
            tls.setPoint(rs.getInt("point"));
            list.add(tls);
        }
        return list;
    }

    public List<TestListStudent> filter(Student student) throws Exception {
        List<TestListStudent> list = new ArrayList<>();
        Connection connection = getConnection();
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(
                baseSql + "t.student_no = ? and t.school_cd = ? order by s.subject_cd, t.no asc");
            statement.setString(1, student.getStudentNo());
            statement.setString(2, student.getSchool().getSchoolCd());
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