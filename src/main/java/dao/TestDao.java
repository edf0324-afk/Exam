package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Test;

public class TestDao extends Dao {

	private String baseSql = "select * from test t inner join student s on t.student_no = s.student_no and t.school_cd = s.school_cd where ";

    public Test get(Student student, Subject subject, School school, int no) throws Exception {

        Test test = null;
        Connection connection = getConnection();
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(
                baseSql + "t.student_no = ? and t.subject_cd = ? and t.school_cd = ? and t.no = ?");
            statement.setString(1, student.getStudentNo());
            statement.setString(2, subject.getCd());
            statement.setString(3, school.getSchoolCd());
            statement.setInt(4, no);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                test = buildTest(rs, subject, school);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            closeResources(statement, connection);
        }

        return test;
    }

    public List<Test> postFilter(ResultSet resultSet, School school) throws Exception {
        List<Test> list = new ArrayList<>();
        SubjectDao subjectDao = new SubjectDao();

        while (resultSet.next()) {
            String subjectCd = resultSet.getString("subject_cd");
            Subject subject = subjectDao.get(subjectCd, school);
            Test test = buildTest(resultSet, subject, school);
            list.add(test);
        }
        return list;
    }

    public List<Test> filter(int entYear, String classNum, Subject subject, int no, School school) throws Exception {

        List<Test> list = new ArrayList<>();
        Connection connection = getConnection();
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(
                baseSql + "s.ent_year = ? and s.class_num = ? and t.subject_cd = ? and t.no = ? and t.school_cd = ? order by s.no asc");
            statement.setInt(1, entYear);
            statement.setString(2, classNum);
            statement.setString(3, subject.getCd());
            statement.setInt(4, no);
            statement.setString(5, school.getSchoolCd());
            ResultSet rs = statement.executeQuery();
            list = postFilter(rs, school);
        } catch (Exception e) {
            throw e;
        } finally {
            closeResources(statement, connection);
        }

        return list;
    }

    public boolean save(List<Test> list) throws Exception {

        Connection connection = getConnection();
        boolean result = true;

        try {
            connection.setAutoCommit(false);
            for (Test test : list) {
                result = save(test, connection);
                if (!result) break;
            }
            if (result) {
                connection.commit();
            } else {
                connection.rollback();
            }
        } catch (Exception e) {
            connection.rollback();
            throw e;
        } finally {
            if (connection != null) {
                try { connection.setAutoCommit(true); connection.close(); } catch (SQLException sqle) { throw sqle; }
            }
        }

        return result;
    }

    public boolean save(Test test, Connection connection) throws Exception {

        PreparedStatement statement = null;
        int count = 0;

        try {
            Connection tmpConn = getConnection();
            Test old = null;
            try {
                PreparedStatement checkStmt = tmpConn.prepareStatement(
                    "select * from test where student_no = ? and subject_cd = ? and school_cd = ? and no = ?");
                checkStmt.setString(1, test.getStudent().getStudentNo());
                checkStmt.setString(2, test.getSubject().getCd());
                checkStmt.setString(3, test.getSchool().getSchoolCd());
                checkStmt.setInt(4, test.getNo());
                ResultSet rs = checkStmt.executeQuery();
                if (rs.next()) old = new Test();
                checkStmt.close();
            } finally {
                tmpConn.close();
            }

            if (old == null) {
                statement = connection.prepareStatement(
                    "insert into test(student_no, subject_cd, school_cd, no, point) values(?, ?, ?, ?, ?)");
                statement.setString(1, test.getStudent().getStudentNo());
                statement.setString(2, test.getSubject().getCd());
                statement.setString(3, test.getSchool().getSchoolCd());
                statement.setInt(4, test.getNo());
                statement.setInt(5, test.getPoint());
            } else {
                statement = connection.prepareStatement(
                    "update test set point = ? where student_no = ? and subject_cd = ? and school_cd = ? and no = ?");
                statement.setInt(1, test.getPoint());
                statement.setString(2, test.getStudent().getStudentNo());
                statement.setString(3, test.getSubject().getCd());
                statement.setString(4, test.getSchool().getSchoolCd());
                statement.setInt(5, test.getNo());
            }
            count = statement.executeUpdate();
        } catch (Exception e) {
            throw e;
        } finally {
            if (statement != null) {
                try { statement.close(); } catch (SQLException sqle) { throw sqle; }
            }
        }

        return count > 0;
    }

    private Test buildTest(ResultSet rs, Subject subject, School school) throws Exception {
        Test test = new Test();

        Student student = new Student();
        student.setStudentNo(rs.getString("student_no"));
        student.setStudentName(rs.getString("student_name"));
        student.setEntYear(rs.getInt("ent_year"));
        student.setClassNum(rs.getString("class_num"));
        student.setSchool(school);

        test.setStudent(student);
        test.setSubject(subject);
        test.setSchool(school);
        test.setClassNum(rs.getString("class_num"));
        test.setNo(rs.getInt("no"));
        test.setPoint(rs.getInt("point"));

        return test;
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