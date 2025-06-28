package Java8Learn;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DataBaseLogin {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/student";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "lzr20040113";

    public static void main(String[] args) {
        JFrame jf = new JFrame();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        jf.setSize((int)(screenSize.width * 0.3), (int)(screenSize.height * 0.3));
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel jp = new JPanel(new GridLayout(3,2));
        JLabel IDlabel = new JLabel("用户名");
        JLabel passwordLabel = new JLabel("密码");
        JTextField IDinput = new JTextField();
        JPasswordField pdinput = new JPasswordField();

        JButton jb = new JButton("登陆");
        jb.addActionListener(event -> {
            String inputID = IDinput.getText();
            String inputPassword = String.valueOf(pdinput.getPassword());

            String sql = "SELECT * FROM account where ID = ? AND Password = ?";
            try(Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, inputID);
                pstmt.setString(2, inputPassword);
                boolean rs = pstmt.execute();
                if (rs) {
                    JOptionPane.showMessageDialog(jf, "登陆成功","success", JOptionPane.INFORMATION_MESSAGE);
                }else JOptionPane.showMessageDialog(jf, "登陆失败","fail",JOptionPane.ERROR_MESSAGE);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        });


        jp.add(IDlabel);jp.add(IDinput);
        jp.add(passwordLabel);jp.add(pdinput);
        jp.add(jb);

        jf.add(jp);
        jf.setVisible(true);
    }
}
