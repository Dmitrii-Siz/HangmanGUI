import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.sql.SQLException;

public class ChoiceScreen extends JFrame{

    //private variables
    private String word;

    private JFrame frame;

    public ChoiceScreen(String word){
        super("Hangman Game");
        this.word = word;

        //create the panel which would hold the label and the 2 buttons:
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setPreferredSize(new Dimension(750, 700)); // Set the size of the panel
        panel.setBackground(Color.decode("#eeeeee")); // Set the background color

        // Create a label to display the message to the user
        JLabel messageLabel = new JLabel("Are you a new user? ");
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 24)); // Set the font size
        messageLabel.setForeground(Color.RED); // Set the font color

        //add the 2 buttons:
        JButton yesButton  = new Custombutton("Yes").CustomButton();
        yesButton.setBackground(new Color(50, 240, 0));//background colour

        JButton noButton  = new Custombutton("No").CustomButton();

        //Button styling:
        yesButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                yesButton.setBackground(new Color(0, 230, 50)); // set hover color
            }

            @Override
            public void mouseExited(MouseEvent e) {
                yesButton.setBackground(new Color(0, 200, 0)); // set original color
            }
        });

        // Create a GridBagConstraints object to position the label:
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.gridy = 1;
        gbc.insets = new Insets(-50, 0, 0, 0); // Padding
        panel.add(messageLabel, gbc);

        //GBC to position the buttons:
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets = new Insets(30, 0, 0, 0); // Padding
        panel.add(yesButton, gbc);

        gbc.gridx = 2;
        gbc.insets = new Insets(30, 0, 0, 0); // Padding
        panel.add(noButton,gbc);

        //Action Listeners:
        yesButton.addActionListener(e -> {
            try {
                new RegisterScreen(word);
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            frame.setVisible(false);
        });
        noButton.addActionListener(e -> {
            new FindmeScreen(word);
            frame.setVisible(false);
        });

        // Create a frame and add the panel to it:
        frame = new JFrame("Hangman Game");
        frame.getContentPane().add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null); // center the frame on screen
        frame.setVisible(true);

    }


}
