import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class WinnerScreen extends JFrame implements ActionListener {
    private String word;

    private String username;

    JFrame frame;

    public WinnerScreen(String word, String username) {
        super("Hangman Game");

        // word passed to the WinnerScreen class
        this.word = word;
        this.username = username;

        //update the score:
        scoreUpdate();

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setPreferredSize(new Dimension(750, 700)); // Set the size of the panel
        panel.setBackground(Color.decode("#eeeeee")); // Set the background color

        // Create a label to display the message to the user
        JLabel congratulationsLabel = new JLabel("Congratulations!");
        congratulationsLabel.setFont(new Font("Arial", Font.PLAIN, 36)); // Set the font size

        // Create a label to display the guessed word
        JLabel wordLabel = new JLabel("You guessed the word '" + word + "' correctly!");
        wordLabel.setFont(new Font("Arial", Font.PLAIN, 24)); // Set the font size

        // Create a button to see the leaderboard
        JButton leaderboardButton = new Custombutton("View Leaderboard").CustomButton();
        leaderboardButton.setPreferredSize(new Dimension(200, 50)); // Set the button size
        leaderboardButton.setFont(new Font("Arial", Font.PLAIN, 18)); // Set the font size
        leaderboardButton.setUI(new CustomButtonUI());//hover
        leaderboardButton.addActionListener(this);

        // Create a button to start a new game
        JButton newGameButton = new Custombutton("Play Again").CustomButton();
        newGameButton.setUI(new CustomButtonUI());//hover
        newGameButton.setPreferredSize(new Dimension(150, 50)); // Set the button size
        newGameButton.setFont(new Font("Arial", Font.PLAIN, 18)); // Set the font size
        newGameButton.addActionListener(this);

        // Create a GridBagConstraints object to position the labels, text field and buttons
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(50, 0, 0, 0); // Padding

        panel.add(congratulationsLabel, gbc);

        gbc.gridy = 1;
        gbc.insets = new Insets(10, 0, 0, 0); // Padding
        panel.add(wordLabel, gbc);

        gbc.gridy = 4;
        gbc.insets = new Insets(30, 0, 0, 0); // Padding
        panel.add(leaderboardButton, gbc);

        gbc.gridy = 5;
        gbc.insets = new Insets(10, 0, 0, 0); // Padding
        panel.add(newGameButton, gbc);

        // Create a frame and add the panel to it
        frame = new JFrame("Hangman Game");
        frame.getContentPane().add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null); // Center the frame on screen
        frame.setVisible(true);
    }

    //updates the player's score:
    private void scoreUpdate() {
        Connection con = null;
        Statement st = null;
        ResultSet rs = null;

        int score = 1;
        try{
            con = DriverManager.getConnection("jdbc:sqlite:users.sqlite");
            st = con.createStatement();
            //Selects the previous score:
            rs = st.executeQuery("SELECT * FROM users WHERE username='"+username+"'");
            //if the result was not null (should not be null):
            while (rs.next()) {
                score += rs.getInt("score");
            }
            //updates the new score:
            st.execute("UPDATE users SET score=" +score+ " WHERE username='"+username+"'");

            //cleans up our mess:
            rs.close();
            st.close();
            con.close();
        } catch (SQLException ex){System.out.println(ex);}
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("View Leaderboard")) {
            //displays the leaderboard:
            new Leaderboard(username);
            frame.setVisible(false);
        }
        else if (e.getActionCommand().equals("Play Again")) {
            new HangmanGui(Main.getWord(), username);
            frame.setVisible(false);
        }
    }
}
