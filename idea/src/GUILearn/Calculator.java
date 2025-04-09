package GUILearn;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;

public class Calculator extends JFrame {

    private JPanel panel;
    private JTextField textField;

    private String[] name = {
            "C", "*", "/", "=",
            "+", "7", "8", "9",
            "-", "4", "5", "6",
            "0", "1", "2", "3"
    };
    private JButton[] buttons = new JButton[16];


    public Calculator() {
        textField = new JTextField(20);
        textField.setEditable(false);
        textField.setHorizontalAlignment(SwingConstants.RIGHT);

        textField.setFont(new Font("Consolas", Font.PLAIN, 24));
        this.add(textField, BorderLayout.NORTH);
        panel = new JPanel(new GridLayout(4, 4, 3, 3));
        this.add(panel);
        ButtonAction action = new ButtonAction();
        for (int i = 0; i < 16; i ++) {
            buttons[i] = new JButton(name[i]);
            buttons[i].addActionListener(action);
            buttons[i].setFont(new Font("Consolas", Font.PLAIN, 16));
            panel.add(buttons[i]);
        }
        this.setTitle("Calculator");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(300, 320);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private void clear() {
        textField.setText("");
    }

    private void calculate() throws FormatErrorException {
        String text = textField.getText().replaceAll("\\s+", "");
        if (text.isEmpty()) throw new FormatErrorException("空表达式");

        Stack<Integer> nums = new Stack<>();
        Stack<Character> ops = new Stack<>();
        int index = 0;

        try {
            while (index < text.length()) {
                char c = text.charAt(index);

                // 处理数字
                if (Character.isDigit(c)) {
                    int num = 0;
                    while (index < text.length() && Character.isDigit(text.charAt(index))) {
                        num = num * 10 + (text.charAt(index) - '0');
                        index++;
                    }
                    nums.push(num);
                    continue;
                }

                else if (c == '-' && (index == 0 || isOperator(text.charAt(index-1)))) {
                    nums.push(-1);
                    ops.push('*');
                }

                else if (isOperator(c)) {
                    while (!ops.isEmpty() && getPriority(ops.peek()) >= getPriority(c)) {
                        compute(nums, ops);
                    }
                    ops.push(c);
                } else {
                    throw new FormatErrorException("非法字符: " + c);
                }
                index++;
            }

            while (!ops.isEmpty()) compute(nums, ops);

            if (nums.size() != 1 || !ops.isEmpty()) {
                throw new FormatErrorException("表达式不完整");
            }

            textField.setText(nums.pop().toString());
        } catch (Exception e) {
            throw new FormatErrorException(e.getMessage());
        }
    }

    private static void compute(Stack<Integer> nums, Stack<Character> ops) throws FormatErrorException {
        if (nums.size() < 2) throw new FormatErrorException("操作数不足");
        int b = nums.pop();
        int a = nums.pop();
        char op = ops.pop();

        switch (op) {
            case '+': nums.push(a + b); break;
            case '-': nums.push(a - b); break;
            case '*': nums.push(a * b); break;
            case '/':
                if (b == 0) throw new FormatErrorException("除以零");
                nums.push(a / b);
                break;
            default: throw new FormatErrorException("非法运算符");
        }
    }

    private static int getPriority(char op) {
        return op == '+' || op == '-' ? 1 :
                op == '*' || op == '/' ? 2 : 0;
    }

    private static boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    private class ButtonAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String s = e.getActionCommand();
            if (s.equals("C")) {
                clear();
                return;
            }
            if (s.equals("=")) {
                try {
                    calculate();
                } catch (FormatErrorException ex) {
                    System.out.println(ex.getMessage());
                }
                return;
            }
            textField.setText(textField.getText() + s);
        }
    }

    private static class FormatErrorException extends Exception {
        public FormatErrorException(String message) {
            super(message);
        }
    }

    public static void main(String[] args) {
        new Calculator();
    }
}
