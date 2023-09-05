/*
===================================
Component Which Paints the Hangman:
===================================
 */

import javax.swing.*;
import java.awt.*;

public class PaintedHangman extends JPanel {

    //private variables:
    private int size;

    PaintedHangman(int size){
        this.size = size;
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Set the color to black
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(5)); // Set the line size to 5 pixels
        g2.setColor(Color.BLACK);

        //Variables:
        int y = 400;
        //System.out.println(size);

        switch (size) {
            case 1 -> {
                g2.drawLine(30, y,60,y-50);
                g2.drawLine(60, y,60,y-350);
                g2.drawLine(60, y-50,90,y);

                g2.drawLine(60,y-300,100,y-350);

                g2.drawLine(60, 50,200,50);
                g2.drawLine(200, 50,200,100);
            }
            case 2 -> {
                g2.drawLine(30, y,60,y-50);
                g2.drawLine(60, y,60,y-350);
                g2.drawLine(60, y-50,90,y);
                g2.drawLine(60,y-300,100,y-350);
                g2.drawLine(60, 50,200,50);
                g2.drawLine(200, 50,200,100);

                //head:
                g2.drawOval(175,100,50,50);
            }
            case 3 -> {
                g2.drawLine(30, y,60,y-50);
                g2.drawLine(60, y,60,y-350);
                g2.drawLine(60, y-50,90,y);
                g2.drawLine(60,y-300,100,y-350);
                g2.drawLine(60, 50,200,50);
                g2.drawLine(200, 50,200,100);

                //head:
                g2.drawOval(175,100,50,50);

                //body:
                g2.drawLine(200,150,200,150+75);
            }

            case 4 -> {
                g2.drawLine(30, y,60,y-50);
                g2.drawLine(60, y,60,y-350);
                g2.drawLine(60, y-50,90,y);
                g2.drawLine(60,y-300,100,y-350);
                g2.drawLine(60, 50,200,50);
                g2.drawLine(200, 50,200,100);

                //head:
                g2.drawOval(175,100,50,50);

                //body:
                g2.drawLine(200,150,200,150+75);

                //left arm:
                g2.drawLine(200,150+25,200-35,150-5);
            }

            case 5 -> {
                g2.drawLine(30, y,60,y-50);
                g2.drawLine(60, y,60,y-350);
                g2.drawLine(60, y-50,90,y);
                g2.drawLine(60,y-300,100,y-350);
                g2.drawLine(60, 50,200,50);
                g2.drawLine(200, 50,200,100);

                //head:
                g2.drawOval(175,100,50,50);

                //body:
                g2.drawLine(200,150,200,150+75);

                //left arm:
                g2.drawLine(200,150+25,200-35,150-5);

                //right arm:
                g2.drawLine(200,150+25,200+35,150-5);
            }

            case 6 -> {
                g2.drawLine(30, y,60,y-50);
                g2.drawLine(60, y,60,y-350);
                g2.drawLine(60, y-50,90,y);
                g2.drawLine(60,y-300,100,y-350);
                g2.drawLine(60, 50,200,50);
                g2.drawLine(200, 50,200,100);

                //head:
                g2.drawOval(175,100,50,50);

                //body:
                g2.drawLine(200,150,200,150+75);

                //left arm:
                g2.drawLine(200,150+25,200-35,150-5);

                //right arm:
                g2.drawLine(200,150+25,200+35,150-5);

                //left leg:
                g2.drawLine(200,225, 200-25, 270);
            }

            case 7 -> {
                g2.drawLine(30, y,60,y-50);
                g2.drawLine(60, y,60,y-350);
                g2.drawLine(60, y-50,90,y);
                g2.drawLine(60,y-300,100,y-350);
                g2.drawLine(60, 50,200,50);
                g2.drawLine(200, 50,200,100);

                //head:
                g2.drawOval(175,100,50,50);

                //body:
                g2.drawLine(200,150,200,150+75);

                //left arm:
                g2.drawLine(200,150+25,200-35,150-5);

                //right arm:
                g2.drawLine(200,150+25,200+35,150-5);

                //left leg:
                g2.drawLine(200,225, 200-25, 270);

                //right leg:
                g2.drawLine(200,225,200+25, 270);
            }
        }
    }
}
