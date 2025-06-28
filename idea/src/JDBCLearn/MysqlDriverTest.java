package JDBCLearn;

public class MysqlDriverTest {
    public static void main(String[] args) {
        try {
            // 尝试加载MySQL驱动
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("MySQL驱动加载成功！");
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL驱动未找到：" + e.getMessage());
        }
    }
}