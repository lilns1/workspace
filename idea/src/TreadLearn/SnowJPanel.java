package TreadLearn;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import javax.swing.*;
public class SnowJPanel extends JPanel implements Runnable {
    int[] x = new int[300];
    int[] y = new int[300]; 
    
    public SnowJPanel(){
        for (int i = 0; i < 80; i++) {
            x[i] = new Random().nextInt(600) + 1;
            y[i] = new Random().nextInt(800) + 1;
        }
    }
    
    public void paint(Graphics g) {
        super.paint(g);        //���ñ���ɫ
        setBackground(Color.black);   //���û��ʵı���
        g.setColor(Color.white);
        //������Ʈ��
        for (int i = 0; i < 300; i++) {
            g.drawString("*",x[i],y[i]);
          }

        //������
        g.fillOval(530,60,50,50);
        g.setColor(Color.black);        
        g.fillOval(550,60,50,50);
    }
    
    
    
    public void run() {
        while(true){
            for (int i = 0; i < y.length; i++) {
                if(y[i]<=800){
                    y[i] ++;
                }else{
                   // a[i] ++;
                    y[i] = 0;
                }
            }
            repaint();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}

    
