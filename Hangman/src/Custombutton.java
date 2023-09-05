import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


//Creates a custom button which would be used though-out the program:
public class Custombutton extends JButton {

    //private variables:
    private String name;

    //Constructor:
    public Custombutton(String name) {
        this.name = name;
    }

    //method:
    public JButton CustomButton() {

        //sets the styling of the button:
        JButton playButton = new JButton(name);
        playButton.setPreferredSize(new Dimension(150, 75));//size
        playButton.setBackground(new Color(124, 0, 0));//background colour
        playButton.setForeground(Color.white);//font colour
        Font font = new Font("Arial", Font.BOLD, 24); // create a new font
        playButton.setFont(font); // set the new font for the button
        //border around the text:
        playButton.setFocusPainted(false);
        playButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        //Button Hover:
        playButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                playButton.setBackground(new Color(184, 0, 0)); // set hover color
            }

            @Override
            public void mouseExited(MouseEvent e) {
                playButton.setBackground(new Color(124, 0, 0)); // set original color
            }
        });
        return playButton;
    }
}
