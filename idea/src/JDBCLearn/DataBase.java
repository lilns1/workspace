package JDBCLearn;

import java.sql.*;

public class DataBase {
    public static void main(String[] args) {  // 移除 throws 声明
        Connection conn = null;
        Statement stmt = null;
        String DB_URL = "jdbc:mysql://localhost:3306/javalearn?serverTimezone=UTC";
        String sql;
        String user = "root";
        String password = "123456";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, user, password);
            stmt = conn.createStatement();
            sql = "Select * from javalearn.student";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt("ID");
                String name = rs.getString("Name");
                String Sex = rs.getString("Sex");
                String Address = rs.getString("Address");
                System.out.println(String.format("%d, %s, %s, %s", id, name, Sex, Address));
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException | ClassNotFoundException e) {  // 合并异常捕获
            e.printStackTrace();
        }
    }
}