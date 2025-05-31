/*
 * Created by JFormDesigner on Mon May 19 14:51:28 HKT 2025
 */

package GUILearn.NotePad;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.FileReader;

/**
 * @author 81291
 */
public class NotePad extends JFrame {

    public NotePad() {
        initComponents();
    }

    public static void main(String[] args) {
        NotePad notePad = new NotePad();
        notePad.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 设置默认关闭操作
        notePad.setSize(600, 400); // 设置初始窗口大小
        notePad.setVisible(true); // 显示窗口
    }

    private void exit(ActionEvent e) {
        // TODO add your code here
        System.exit(0);
    }

    private void help(ActionEvent e) {
        // TODO add your code here
        JOptionPane.showMessageDialog(this, "程序开发中", "帮助", JOptionPane.INFORMATION_MESSAGE);
    }

    private void open(ActionEvent ee) {
        // TODO add your code here
        JFileChooser fc = new JFileChooser();
// 显示文件打开对话框
        int rVal = fc.showOpenDialog(this);
// 如果点击确定(Yes/OK)
        if (rVal == JFileChooser.APPROVE_OPTION) {
// 获取文件对话框中用户选中的文件名
            String fileName = fc.getSelectedFile().getName();
// 获取文件对话框中用户选中的文件所在的路径
            String path = fc.getCurrentDirectory().toString();
            try {
// 创建一个文件输入流，用于读文件
                FileReader fread = new FileReader(path + "/" + fileName);
// 创建一个缓冲流
                BufferedReader bread = new BufferedReader(fread);
// 从文件中读一行信息
                String line = bread.readLine();
                while (line != null) {
                    mainTextArea.append(line + "\n");
                    line = bread.readLine();
                }
                bread.close();
                fread.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void copyText(ActionEvent e) {

        String selectedText = mainTextArea.getSelectedText();

        if (selectedText != null) {

// 获取系统剪贴板

            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

// 创建字符串选择对象

            StringSelection selection = new StringSelection(selectedText);

// 将文本放入剪贴板

            clipboard.setContents(selection, null);

            JOptionPane.showMessageDialog(this, "已复制到剪贴板");

        } else {

            JOptionPane.showMessageDialog(this, "请先选择文本", "提示", JOptionPane.WARNING_MESSAGE);

        }

    }


// 粘贴文本方法

    private void pasteText(ActionEvent ee) {

// 获取系统剪贴板

        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

// 获取剪贴板内容

        Transferable transferable = clipboard.getContents(null);



// 检查内容是否为字符串类型

        if (transferable != null && transferable.isDataFlavorSupported(java.awt.datatransfer.DataFlavor.stringFlavor)) {

            try {

// 获取剪贴板中的文本

                String text = (String) transferable.getTransferData(java.awt.datatransfer.DataFlavor.stringFlavor);

// 将文本插入到文本区域的当前光标位置

                mainTextArea.replaceSelection(text);

            } catch (Exception e) {

                JOptionPane.showMessageDialog(this, "粘贴失败: " + e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);

            }

        } else {

            JOptionPane.showMessageDialog(this, "剪贴板中没有文本内容", "提示", JOptionPane.INFORMATION_MESSAGE);

        }

    }


    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        mainMenuBar = new JMenuBar();
        menuFile = new JMenu();
        menuItemOpen = new JMenuItem();
        menuItemSave = new JMenuItem();
        menuItemCopy = new JMenuItem();
        menuItemPaste = new JMenuItem();
        menuItemExit = new JMenuItem();
        menuItemHelp = new JMenuItem();
        scrollPane1 = new JScrollPane();
        mainTextArea = new JTextArea();

        //======== this ========
        setResizable(false);
        var contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== mainMenuBar ========
        {

            //======== menuFile ========
            {
                menuFile.setText("\u6587\u4ef6");

                //---- menuItemOpen ----
                menuItemOpen.setText("\u6253\u5f00");
                menuItemOpen.addActionListener(e -> open(e));
                menuFile.add(menuItemOpen);

                //---- menuItemSave ----
                menuItemSave.setText("\u4fdd\u5b58");
                menuFile.add(menuItemSave);

                //---- menuItemCopy ----
                menuItemCopy.setText("\u590d\u5236");
                menuItemCopy.addActionListener(e -> copyText(e));
                menuFile.add(menuItemCopy);

                //---- menuItemPaste ----
                menuItemPaste.setText("\u7c98\u8d34");
                menuItemPaste.addActionListener(e -> pasteText(e));
                menuFile.add(menuItemPaste);
                menuFile.addSeparator();

                //---- menuItemExit ----
                menuItemExit.setText("\u9000\u51fa");
                menuItemExit.addActionListener(e -> exit(e));
                menuFile.add(menuItemExit);
            }
            mainMenuBar.add(menuFile);

            //---- menuItemHelp ----
            menuItemHelp.setText("\u5e2e\u52a9");
            menuItemHelp.addActionListener(e -> help(e));
            mainMenuBar.add(menuItemHelp);
        }
        setJMenuBar(mainMenuBar);

        //======== scrollPane1 ========
        {
            scrollPane1.setViewportView(mainTextArea);
        }
        contentPane.add(scrollPane1, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JMenuBar mainMenuBar;
    private JMenu menuFile;
    private JMenuItem menuItemOpen;
    private JMenuItem menuItemSave;
    private JMenuItem menuItemCopy;
    private JMenuItem menuItemPaste;
    private JMenuItem menuItemExit;
    private JMenuItem menuItemHelp;
    private JScrollPane scrollPane1;
    private JTextArea mainTextArea;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
