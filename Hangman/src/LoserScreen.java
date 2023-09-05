import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class LoserScreen extends JFrame implements ActionListener {

    //private variables:
    private String word;

    private JFrame frame;

    private String username;

    public LoserScreen(String word, String username) {
        super("Hangman Game");

        //words passed to the LoserScreen class
        this.word = word;
        this.username = username;

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setPreferredSize(new Dimension(750, 700)); // Set the size of the panel

        // Create a label to display the message to the user
        JLabel messageLabel = new JLabel("You lost! The word was " + word);
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 24)); // Set the font size
        messageLabel.setForeground(Color.RED); // Set the font color

        // Create an image label to display the hangman image
        JLabel imageLabel = new JLabel();
        try {
            BufferedImage image = ImageIO.read(new File("hangman.png")); // Change "hangman6.png" to the appropriate file name
            ImageIcon icon = new ImageIcon(image.getScaledInstance(350, 250, Image.SCALE_SMOOTH));
            imageLabel.setIcon(icon);
        } catch (IOException ex) {
            System.out.println("Failed to load image: " + ex);
        }

        // Create a button to start a new game
        JButton newGameButton = new Custombutton("New Game").CustomButton();
        newGameButton.setUI(new CustomButtonUI());
        newGameButton.setPreferredSize(new Dimension(150, 50)); // Set the button size
        newGameButton.setFont(new Font("Arial", Font.PLAIN, 18)); // Set the font size
        newGameButton.addActionListener(this);

        // Create a GridBagConstraints object to position the label and button
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(50, 0, 0, 0); // Padding

        panel.add(imageLabel, gbc);

        gbc.gridy = 1;
        gbc.insets = new Insets(30, 0, 0, 0); // Padding
        panel.add(messageLabel, gbc);

        gbc.gridy = 2;
        gbc.insets = new Insets(30, 0, 0, 0); // Padding
        panel.add(newGameButton, gbc);

        // Create a frame and add the panel to it
        frame = new JFrame("Hangman Game");
        frame.getContentPane().add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(Color.RED);
        frame.pack();
        frame.setLocationRelativeTo(null); // Center the frame on screen
        frame.setVisible(true);
    }

    //action listener:
    public void actionPerformed(ActionEvent e) {
        //restarts the game:
        if (e.getActionCommand().equals("New Game")) {
            dispose(); // Close the LoserScreen
            frame.setVisible(false);
            new HangmanGui(new Main().getWord(),username); // Open a new StartScreen to start a new game
        }
    }
}
