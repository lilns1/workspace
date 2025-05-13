package GUILearn;

import javax.swing.*;

public class Register extends JFrame {
    private JTextField IdField;
    private JTextField NameField;
    private JRadioButton MaleButton;
    private JRadioButton FemaleButton;
    private JTextArea textArea;
    private JComboBox comboBox1;
    private JComboBox comboBox2;
    private JList departmentList;
    private JCheckBox basketCheckBox;
    private JCheckBox danceCheckBox;
    private JCheckBox writeCheckBox;
    private JCheckBox gameCheckBox;
    private JButton registerButton;
    private JButton rewriteButton;
    private JPanel mainpanel;
    private JTextField addressField;


    public Register() {
        // 关键：将设计的 mainpanel 设为窗口内容
        setContentPane(mainpanel);

        // 初始化窗口属性（标题、关闭操作、自动调整大小）
        setTitle("注册窗口");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack(); // 自动计算窗口大小

        // 其他初始化代码（例如按钮监听器）
        registerButton.addActionListener(e -> {
            String id = IdField.getText();
            System.out.println("学号：" + id);

            String name = NameField.getText();
            System.out.println("姓名:" + name);

            String gender = "";
            if (MaleButton.isSelected()) gender = "Male";
            else if (FemaleButton.isSelected()) gender = "Female";
            System.out.println("性别:" + gender);

            String province = (String) comboBox1.getSelectedItem();
            String city = (String) comboBox2.getSelectedItem();
            String state = (String) addressField.getText();
            String address = province + "-" + city + "-" + state;
            System.out.println("地址: " + address);

            String department = (String) departmentList.getSelectedValue();
            System.out.println("系别: " + department);

            String interest = (basketCheckBox.isSelected() ? "篮球 " : "")
                    + (danceCheckBox.isSelected() ? "跳舞 " : "")
                    + (writeCheckBox.isSelected() ? "书法 " : "")
                    + (gameCheckBox.isSelected() ? "游戏 " : "");
            System.out.println("兴趣:" + interest);

            String other = textArea.getText();
            System.out.println("备注:" + other);
        });

        rewriteButton.addActionListener(e -> {
            IdField.setText("");
            NameField.setText("");
        });
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    static public void main(String[] args) {
        new Register();
    }


}
