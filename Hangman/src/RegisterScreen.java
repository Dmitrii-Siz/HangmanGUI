import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class RegisterScreen extends JFrame {

    //private variables:
    private String word;

    private String username;

    private JFrame frame;

    //Constructor:
    public RegisterScreen(String word) throws FileNotFoundException, SQLException {
        this.word = word;

        //create the panel which would hold the label and the 2 buttons:
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setPreferredSize(new Dimension(750, 700)); // Set the size of the panel
        panel.setBackground(Color.decode("#eeeeee")); // Set the background color

        // Create a label to display the message to the user
        JLabel messageLabel = new JLabel("Here is your username:");
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 32)); // Set the font size
        messageLabel.setForeground(Color.RED); // Set the font color

        // Create a GridBagConstraints object to position the label:
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.gridy = 1;
        gbc.insets = new Insets(-50, 0, 0, 0); // Padding
        panel.add(messageLabel, gbc);

        username = generateUsername();
        JLabel usernameLabel = new JLabel(username);
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 24)); // Set the font size
        usernameLabel.setForeground(Color.RED); // Set the font color
        gbc.gridy = 2;
        gbc.insets = new Insets(50, 0, 0, 0); // Padding
        panel.add(usernameLabel, gbc);

        //Play Button Styling with an action listener:
        JButton playButton = (new Custombutton("Play")).CustomButton();
        // Set the custom button UI to the playButton
        playButton.setUI(new CustomButtonUI());
        playButton.addActionListener(e ->{
            new HangmanGui(word,username);
            frame.setVisible(false);
        });

        //GBC for the play button:
        gbc.gridy = 3;
        gbc.insets = new Insets(50, 0, 0, 0); // Padding
        panel.add(playButton, gbc);


        //Frame to display the panel:
        frame = new JFrame("Hangman Game");
        frame.getContentPane().add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null); // center the frame on screen
        frame.setVisible(true);
    }


    //generate a random username from the text file:
    public String generateUsername() throws SQLException, FileNotFoundException {
        ArrayList<String> targetWords = new ArrayList<>();
        //adds everything to the arraylist:
        Scanner in = new Scanner(new File("usernames.txt"));
        String username;
        while(in.hasNext()){
            targetWords.add(in.next());
        }
        Random r = new Random();
        while(true){
            //random username:
            username = targetWords.get(r.nextInt(targetWords.size()));
            Connection con = null;
            Statement st = null;
            ResultSet rs = null;
            //queries to check if the username exists and to insert the new username:
            try{
                con = DriverManager.getConnection("jdbc:sqlite:users.sqlite");
                st = con.createStatement();
                rs = st.executeQuery("SELECT * FROM users WHERE username='"+username+"'");
                if(!rs.next()){
                    // username is unique, so insert it into the database and return it
                    st.executeUpdate("INSERT INTO users (username,score) values('"+username+"',0)");
                    return username;
                }
            }
            //clean up:
            finally {
                if(rs != null) rs.close();
                if(st != null) st.close();
                if(con != null) con.close();
            }
        }
    }
}
