import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class StartScreen extends JFrame implements ActionListener {

    //private variables:
    private JButton playButton;

    private String word;

    private JLabel background;

    private BufferedImage image;

    private JFrame frame;

    public StartScreen(String word) {
        super("Hangman Game");

        //word passed to the HangmanGui class
        this.word = word;

        JPanel panel = new JPanel(new GridBagLayout());

        //import the image
        try {
            image = ImageIO.read(new File("welcome.jpg"));
            background = new JLabel(new ImageIcon(image));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //Scale the image to fit the panel
        Image scaledImage = image.getScaledInstance(750, 650, Image.SCALE_SMOOTH);
        background.setIcon(new ImageIcon(scaledImage));

        //GridBagConstraints object to position the button
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 0, 50, 0); // padding
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.NONE;

        //Play Button Styling:
        JPanel buttonPanel = new JPanel();
        //import the customised button
        playButton = (new Custombutton("Play")).CustomButton();
        playButton.addActionListener(this);//al
        // Set the custom button UI to the playButton
        playButton.setUI(new CustomButtonUI());

        //adds the button to the pane;
        buttonPanel.add(playButton);

        //adds the label (image) and the panel to the main panel:
        panel.add(background);
        panel.add(buttonPanel, gbc);

        // Create a frame and add the panel to it:
        frame = new JFrame("Hangman Game");
        frame.getContentPane().add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null); // center the frame on screen
        frame.setVisible(true);
    }

    //Action listener
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == playButton) {
            new ChoiceScreen(word);
            dispose(); // close the StartScreen instance
            frame.setVisible(false);
        }
    }

}
