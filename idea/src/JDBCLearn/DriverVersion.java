package JDBCLearn;

import java.sql.*;

public class DriverVersion {
    public static void main(String[] args) throws SQLException {
        Driver driver = new com.mysql.cj.jdbc.Driver();
//        System.out.println("MySQL JDBC Driver Version: " + driver.getMajorVersion() + "." + driver.getMinorVersion());
        // 或直接打印完整版本信息
        System.out.println("Driver Class Package: " + Driver.class.getPackage().getImplementationVersion());
    }
}
