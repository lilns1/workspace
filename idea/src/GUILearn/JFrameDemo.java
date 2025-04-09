package GUILearn;

import javax.swing.*;
import java.awt.*;

public class JFrameDemo {
    public static void main(String[] args) {
        // JFrame
        JFrame jf = new JFrame();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int)(screenSize.width * 0.5);
        int height = (int)(screenSize.height * 0.5);
        jf.setSize(width, height);
        jf.setLocationRelativeTo(null);
        jf.setTitle("abc");
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel jp = new JPanel();

        JButton jbutton1 = new JButton("按钮1");
        JButton jbutton2 = new JButton("按钮2");
        FlowLayout flow = new FlowLayout(FlowLayout.LEFT, 10, 6);
//        jp.setLayout(flow);
//        jp.add(jbutton1);
//        jp.add(jbutton2);
//        jf.add(jp);

        JButton jb1 = new JButton("北");
        JButton jb2 = new JButton("南");
        JButton jb3 = new JButton("西");
        JButton jb4 = new JButton("东");
        JButton jb5 = new JButton("中间");

        jp.setLayout(new BorderLayout());
        jp.add(jb1, BorderLayout.NORTH);
        jp.add(jb2, BorderLayout.SOUTH);
        jp.add(jb3, BorderLayout.EAST);
        jp.add(jb4, BorderLayout.WEST);
        jp.add(jb5, BorderLayout.CENTER);
        jf.add(jp);
        jf.setVisible(true);
    }
}
