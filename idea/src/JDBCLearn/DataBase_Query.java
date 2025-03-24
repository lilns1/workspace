package JDBCLearn;
import java.sql.*;
import java.util.*;
public class DataBase_Query {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/java";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "123456";
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;
        while(true) {
            System.out.println("请选择操作：");
            System.out.println("1. 添加数据");
            System.out.println("2. 删除数据");
            System.out.println("3. 查询数据");
            System.out.println("4. 修改数据");
            System.out.println("5. 退出");
            choice = scanner.nextInt();
            System.out.println("");
            switch (choice) {
                case 1:
                    addData();
                    break;
                case 2:
                     deleteData();
                    break;
                case 3:
                    queryData();
                    break;
                case 4:
                     updateData();
                    break;
                case 5:
                    System.out.println("退出程序。");
                    System.exit(0);
                    break;
                default:
                    System.out.println("无效的选择，请重新输入。");
            }
        }

    }

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }


    private static void queryData() {
        String sql = "SELECT * FROM student";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                System.out.print("\t"+rs.getInt("ID"));
                System.out.print("\t"+rs.getString("Name"));
                System.out.print("\t"+rs.getString("Sex"));
                System.out.print("\t"+rs.getString("Address"));
                System.out.print("\t"+rs.getString("Dept"));String name = rs.getString("Name");
                System.out.print("\t"+rs.getInt("Age"));
                System.out.println("");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void addData() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please input ID, Name, Sex, Address, Dept, age");
        int id = sc.nextInt();
//        sc.nextLine();
        String name = sc.next(), sex = sc.next(), address = sc.next(), dept = sc.next();
        int age = sc.nextInt();
        String sql = String.format("INSERT INTO student VALUES(%d, '%s', '%s', '%s', '%s', %d);", id, name, sex, address, dept, age);
        System.out.println(sql);
        try (Connection conn  = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);) {
            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Operation Successful");
            } else System.out.println("Operation Failed");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void deleteData() {
        // 按学号删除
        Scanner sc = new Scanner(System.in);
        System.out.println("Please input delete ID");
        int id = sc.nextInt();
        String sql = String.format("DELETE FROM student WHERE ID = %d;", id);
        System.out.println(sql);
        try (Connection conn = getConnection();
            Statement stmt = conn.createStatement()) {
            int rows = stmt.executeUpdate(sql);
            if (rows > 0) {
                System.out.println("Operation Successful");
            } else System.out.println("Operation Failed");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void updateData() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please input update ID");
        int id = sc.nextInt();
        System.out.println("Please input new data");
//        int newid = sc.nextInt();
        String name = sc.next(), sex = sc.next(), address = sc.next(), dept = sc.next();
        int age = sc.nextInt();
        String sql = "UPDATE student SET Name = ?, Sex = ?, Address = ?, dept = ?, Age = ? WHERE ID = ?";
//        String sql = String.format("UPDATE  student VALUES(%d, '%s', '%s', '%s', '%s', %d);", id, name, sex, address, dept, age);
//        System.out.println(sql);
        try (Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setString(1, name);
            pstmt.setString(2, sex);
            pstmt.setString(3, address);
            pstmt.setString(4, dept);
            pstmt.setInt(5, age);
            pstmt.setInt(6, id);
            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Operation Successful");
            } else System.out.println("Operation Failed");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
