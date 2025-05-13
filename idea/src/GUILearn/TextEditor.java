package GUILearn;

import javax.swing.*;

public class TextEditor{
    private JPanel panel1;
    private JTextPane mainTextPane;
    private JMenuBar menubar;

    static public void main(String[] args) {
        JFrame frame = new JFrame("TextEditor");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        TextEditor textEditor = new TextEditor();
        frame.setContentPane(textEditor.panel1);
        frame.pack();
        frame.setVisible(true);

    }
}
