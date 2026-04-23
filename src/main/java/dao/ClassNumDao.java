package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.School;

public class ClassNumDao extends Dao {

    public List<String> filter(School school) throws Exception {
        List<String> list = new ArrayList<>();
        Connection connection = getConnection();
        PreparedStatement statement = null;

        try {
            // ★ここを CLASSNUM（アンダーバーなし）にする！
            statement = connection.prepareStatement("select CLASS_NUM from CLASSNUM where SCHOOL_CD=? order by CLASS_NUM asc");
            statement.setString(1, school.getSchoolCd());
            ResultSet rSet = statement.executeQuery();

            while (rSet.next()) {
                list.add(rSet.getString("CLASS_NUM"));
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (statement != null) { statement.close(); }
            if (connection != null) { connection.close(); }
        }
        return list;
    }

    // getメソッドやsaveメソッドを使っている場合も、すべて class_num -> classnum に修正してください
}