import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Objects;

public class FindmeScreen extends JFrame {

    //private variables:
    private String word;

    private JFrame frame;

    private String username = null;

    //Constructor:
    public FindmeScreen(String word) {
        this.word = word;

        //create the panel which would hold the label and the 2 buttons:
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setPreferredSize(new Dimension(750, 700)); // Set the size of the panel
        panel.setBackground(Color.decode("#eeeeee")); // Set the background color

        // Create the label
        JLabel label = new JLabel("Enter your username:");
        label.setFont(new Font("Arial", Font.PLAIN, 32)); // Set the font size
        label.setForeground(Color.RED); // Set the font color
        GridBagConstraints gbc1 = new GridBagConstraints();
        gbc1.gridx = 0;
        gbc1.gridy = 0;
        gbc1.anchor = GridBagConstraints.LINE_START;
        gbc1.insets = new Insets(20, 50, 0, 0); // padding
        panel.add(label, gbc1);

        // Create the text field
        JTextField textField = new JTextField(20);
        textField.setPreferredSize(new Dimension(200, 40)); // set preferred size
        textField.setFont(new Font("Arial", Font.PLAIN, 20)); // set font and size
        textField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.gray), // add border
                BorderFactory.createEmptyBorder(5, 10, 5, 10))); // add padding
        textField.setBackground(Color.white); // set background color
        textField.setForeground(Color.black); // set text color
        GridBagConstraints gbc2 = new GridBagConstraints();
        gbc2.gridx = 0;
        gbc2.gridy = 1;
        gbc2.anchor = GridBagConstraints.CENTER;
        gbc2.insets = new Insets(20, 20, 0, 20); // padding
        panel.add(textField, gbc2);

        // Create the submit button
        JButton submitButton = new Custombutton("Find username").CustomButton();
        submitButton.setUI(new CustomButtonUI());
        submitButton.setPreferredSize(new Dimension(230, 75));//size
        GridBagConstraints gbc3 = new GridBagConstraints();
        gbc3.gridx = 0;
        gbc3.gridy = 2;
        gbc3.anchor = GridBagConstraints.CENTER;
        gbc3.insets = new Insets(20, 20, 20, 20); // padding
        panel.add(submitButton, gbc3);

        //replace submitButton with the play button:
        JButton playButton = new Custombutton("Play").CustomButton();
        playButton.setUI(new CustomButtonUI());
        playButton.setVisible(false);
        playButton.addActionListener(e -> {
            new HangmanGui(word,username);
            frame.setVisible(false);
            dispose();
        });
        panel.add(playButton, gbc3);

        //add the 2 labels:
        JLabel label2 = new JLabel("");
        label2.setFont(new Font("Arial", Font.PLAIN, 24)); // Set the font size
        label2.setForeground(Color.RED); // Set the font color
        //GBCs:
        gbc3.gridy = 3;
        gbc3.insets = new Insets(20, 0, 20, 0); // padding
        //add the label2
        panel.add(label2, gbc3);


        //GBCs:
        gbc3.gridy = 4;
        gbc3.insets = new Insets(20, 0, 20, 0); // padding
        //adds the register button:
        JButton registerButton = new Custombutton("Register").CustomButton();
        registerButton.setUI(new CustomButtonUI());
        registerButton.setSize(new Dimension(200,75));
        registerButton.setVisible(false);
        registerButton.addActionListener(e -> {
            try {
                new RegisterScreen(word);
            } catch (FileNotFoundException | SQLException ex) {
                throw new RuntimeException(ex);
            }
            frame.setVisible(false);
            dispose();
        });
        panel.add(registerButton, gbc3);



        //action listener:
        submitButton.addActionListener(e ->{
            String userInput = textField.getText();
            Connection con = null;
            Statement st = null;
            ResultSet rs = null;
            try{
                con = DriverManager.getConnection("jdbc:sqlite:users.sqlite");
                st = con.createStatement();
                rs = st.executeQuery("SELECT * FROM users WHERE username='"+userInput+"'");
                //if the result was not null:
                while (rs.next()) {
                    //for testing purposes:
                    int id = rs.getInt("id");
                    String usernameRetrieved = rs.getString("username");
                    int score = rs.getInt("score");
                    this.username = usernameRetrieved;
                    System.out.println("ID: " + id + ", Username: " + username + ", Score: " + score);
                }
                //cleans up our mess:
                rs.close();
                st.close();
                con.close();
            } catch (SQLException ex){System.out.println(ex);}
            //if the username is not found:
            if(username == null){
                //make the label visible
                label2.setText("Username Not Found!");
                //make register button visible
                registerButton.setVisible(true);
            }
            //if the username was found
            else{
                //set the top label to this text:
                label.setText("Your username '"+username+"' Was Found!");
                playButton.setVisible(true);

                //make everything else disappear:
                textField.setVisible(false);
                submitButton.setVisible(false);
                registerButton.setVisible(false);
                label2.setVisible(false);
            }
        });

        //Frame to display the panel:
        frame = new JFrame("Hangman Game");
        frame.getContentPane().add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null); // center the frame on screen
        frame.setVisible(true);
    }
}
