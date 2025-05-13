package GUILearn;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Cal {

    private JFrame frame;
    private JTextField Number1;
    private JTextField Number2;
    private JTextField Number3;
    float data1,data2;
    String flag;
    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Cal window = new Cal();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public Cal() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setTitle("\u8BA1\u7B97\u56681.0");
        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        frame.getContentPane().add(panel, BorderLayout.CENTER);
        panel.setLayout(null);

        JLabel lblNewLabel_1 = new JLabel("\u7B80\u6613\u8BA1\u7B97\u5668");
        lblNewLabel_1.setBounds(160, 10, 117, 24);
        lblNewLabel_1.setForeground(Color.BLUE);
        lblNewLabel_1.setFont(new Font("����", Font.BOLD, 22));
        panel.add(lblNewLabel_1);

        JPanel panel_1 = new JPanel();
        panel_1.setBounds(433, 12, 0, 0);
        panel.add(panel_1);
        panel_1.setLayout(new GridLayout(1, 0, 0, 0));

        JLabel lblNewLabel = new JLabel("\u64CD\u4F5C\u65701\uFF1A");
        lblNewLabel.setForeground(Color.MAGENTA);
        lblNewLabel.setFont(new Font("����", Font.PLAIN, 14));
        lblNewLabel.setBounds(89, 46, 72, 15);
        panel.add(lblNewLabel);

        Number1 = new JTextField();
        Number1.setBounds(160, 40, 151, 21);
        panel.add(Number1);
        Number1.setColumns(6);

        JLabel label = new JLabel("\u8FD0\u7B97\u7B26\uFF1A");
        label.setForeground(Color.MAGENTA);
        label.setFont(new Font("����", Font.PLAIN, 14));
        label.setBounds(89, 73, 72, 15);
        panel.add(label);

        JRadioButton div = new JRadioButton("/");
        div.setBounds(290, 69, 40, 23);
        panel.add(div);

        JRadioButton add = new JRadioButton("+");
        add.setSelected(true);
        add.setBounds(164, 69, 40, 23);
        panel.add(add);

        JRadioButton sub = new JRadioButton("-");
        sub.setBounds(206, 69, 40, 23);
        panel.add(sub);

        JRadioButton mul = new JRadioButton("*");
        mul.setBounds(248, 69, 40, 23);
        panel.add(mul);

        ButtonGroup  group=new  ButtonGroup();

        group.add(add);  group.add(sub);group.add(mul);group.add(div);





        JLabel lblNewLabel_2 = new JLabel("\u64CD\u4F5C\u65702\uFF1A");
        lblNewLabel_2.setForeground(Color.RED);
        lblNewLabel_2.setFont(new Font("����", Font.PLAIN, 14));
        lblNewLabel_2.setBounds(89, 107, 72, 15);
        panel.add(lblNewLabel_2);

        Number2 = new JTextField();
        Number2.setColumns(6);
        Number2.setBounds(160, 104, 151, 21);
        panel.add(Number2);

        JButton button = new JButton("\u786E\u5B9A");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                try{
                    data1=Float.parseFloat(Number1.getText());
                    data2=Float.parseFloat(Number2.getText());
                    float result=0;
                    if(add.isSelected()) {result=data1+data2;flag="+";}
                    if(sub.isSelected()) {result=data1-data2;flag="-";}
                    if(mul.isSelected()) {result=data1*data2;flag="*";}
                    if(div.isSelected()){result=data1/data2;flag="/";}
                    Number3.setText(Number1.getText()+flag+Number2.getText()+"="+result);
                }
                catch(NumberFormatException e){
                    //modDialog


                    //nonDialog
                    JDialog nondialog = new JDialog(frame, "错误!", false);
                    nondialog.setVisible(true);
                    nondialog.setBounds(250,200,200,100);
                    JPanel jp2 = new JPanel(new BorderLayout());

                    JLabel jl2 = new JLabel("数据格式错误");
                    jp2.add(jl2, BorderLayout.CENTER);
                    nondialog.add(jp2);
                    //JOptionPane.MessageDialog
                    JOptionPane.showMessageDialog(frame,"数据格式错误","错误",JOptionPane.ERROR_MESSAGE);
                    //
                    JOptionPane.showInputDialog(frame,"数据格式错误","错误",JOptionPane.ERROR_MESSAGE);
                    //
                    JOptionPane.showConfirmDialog(frame,"数据格式错误","错误",JOptionPane.DEFAULT_OPTION,JOptionPane.ERROR_MESSAGE);
                    //
                    JOptionPane.showOptionDialog(frame,"数据格式错误","错误",JOptionPane.DEFAULT_OPTION
                            ,JOptionPane.WARNING_MESSAGE,null,null,null);
                }
            }
        });
        button.setBounds(109, 168, 81, 35);
        panel.add(button);

        JButton button_1 = new JButton("\u53D6\u6D88");
        button_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                float data1,data2;
                Number1.setText("");
                Number2.setText("");
                Number3.setText("");
            }
        });
        button_1.setBounds(200, 168, 81, 35);
        panel.add(button_1);

        JLabel lblNewLabel_2_1 = new JLabel("\u7ED3\u679C\uFF1A");
        lblNewLabel_2_1.setForeground(Color.RED);
        lblNewLabel_2_1.setFont(new Font("����", Font.PLAIN, 14));
        lblNewLabel_2_1.setBounds(89, 143, 72, 15);
        panel.add(lblNewLabel_2_1);

        Number3 = new JTextField();
        Number3.setColumns(6);
        Number3.setBounds(160, 137, 151, 21);
        panel.add(Number3);

        JButton button_1_1 = new JButton("\u9000\u51FA");
        button_1_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        button_1_1.setBounds(290, 168, 81, 35);
        panel.add(button_1_1);
    }
}
