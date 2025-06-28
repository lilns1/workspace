package Java8Learn;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NoDataBaseLogin {

    static final String id = "lilns";
    static final String password = "123abc";

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
        jb.addActionListener(e -> {
            String inputID = IDinput.getText();
            String inputpd = String.valueOf(pdinput.getPassword());
            if (inputpd.equals(password) && inputID.equals(id)) {
                JOptionPane.showMessageDialog(jf, "登录成功！", "成功", JOptionPane.INFORMATION_MESSAGE);
            }
            else {
                JOptionPane.showMessageDialog(jf, "密码或id错误","错误",JOptionPane.ERROR_MESSAGE);
            }
        });

        jp.add(IDlabel);jp.add(IDinput);
        jp.add(passwordLabel);jp.add(pdinput);
        jp.add(jb);

        jf.add(jp);
        jf.setVisible(true);


    }
}
