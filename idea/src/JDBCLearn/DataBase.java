package JDBCLearn;

import java.sql.*;

public class DataBase {
    public static void main(String[] args) {  // 移除 throws 声明
        Connection conn = null;
        Statement stmt = null;
        String url = "jdbc:mysql://localhost:3306/student";
        String user  = "root";
        String pass = "123456";
        System.out.println(Driver.class.getPackage().getImplementationVersion());
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, user, pass);
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM student.student;");
//            while (rs.next()) {
//                System.out.println(rs.getInt(1) + " ,"  + rs.getString("NAME"));
//            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}