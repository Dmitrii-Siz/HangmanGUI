import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import javax.swing.text.AbstractDocument;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.AttributeSet;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class HangmanGui implements ActionListener {

    //private variables:
    //Text field for the player to enter their guess
    private JTextField guess;

    //Global Font:
    private String font = "Comic Sans MS";

    private String username;

    //stores the incorrectly guessed letters:
    private ArrayList<String> incorrectLetters = new ArrayList<>();

    //global JPanel which would be updating the words:
    private JPanel letterPanel = new JPanel();

    //global JPanel which would be drawing the hangman:
    private JPanel leftSide = new JPanel();

    private JLabel tempLabel;

    private String word;

    private int count = 0;

    private JLabel errorLabel;

    private JFrame masterFrame;

    //constructor:
    HangmanGui(String word, String username){
        this.word = word;
        this.username = username;

        //Panel with the Dimensions of 0:
        JPanel stylePanel = new JPanel();
        stylePanel.setPreferredSize(new Dimension(0,0));

        ArrayList<String> listOfLetters = new ArrayList<>();
        //sets the array correct guessed letters to empty:
        for (int i = 0;i < word.length(); i++){
            String letter = String.valueOf(word.charAt(i));
            listOfLetters.add(letter);

        }

        //==========
        // Main Frame:
        // ============
        masterFrame = new JFrame("Hangman Game");
        masterFrame.setSize(750, 700);//size of the canvas
        masterFrame.getContentPane().setBackground(Color.RED);
        masterFrame.setLayout(new BorderLayout());
        masterFrame.setLocationRelativeTo(null);
        masterFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        //Side Colours:
        JPanel p3 = new JPanel();
        JPanel p4 = new JPanel();
        JPanel p5 = new JPanel();

        Color background = new Color(238, 238, 238);

        p3.setBackground(background);
        p4.setBackground(background);
        p5.setBackground(background);

        masterFrame.add(p3, BorderLayout.EAST);
        masterFrame.add(p4, BorderLayout.WEST);
        masterFrame.add(p5, BorderLayout.SOUTH);


        p3.setPreferredSize(new Dimension(10,20));
        p4.setPreferredSize(new Dimension(10,20));
        p5.setPreferredSize(new Dimension(10,20));

        /*
         * ===================================
         * Panel With Letters and Underscores:
         * ===================================
         * */
        //Adding The letter Panel:
        masterFrame.add(letterPanel, BorderLayout.NORTH);

        //Letter Panel Dimensions:
        letterPanel.setPreferredSize(new Dimension(750,150));

        //Test background Colour:
        letterPanel.setBackground(background);

        letterPanel.setLayout(new GridLayout(2,listOfLetters.size()+1,-30,-120));

        letterPanel.add(drawLetter(" "));

        for(String s: listOfLetters){
            letterPanel.add(drawLetter(s));
        }

        letterPanel.add(drawLetter(" "));

        for(int i = 0; i < word.length(); i++){
            letterPanel.add(getUnderscore());
        }


        /*
         * ====================
         * Panel With Contents:
         * ====================
         * */
        JPanel content = new JPanel();
        content.setLayout(new BorderLayout());

        //Adding The Content Panel:
        masterFrame.add(content, BorderLayout.CENTER);

        //Content Panel Dimensions:
        //content.setPreferredSize(new Dimension(750,200));

        //Test background Colour:
        content.setBackground(Color.green);


        /*
         * ====================
         * (LeftSide) Frame with the Hangman drawing and the incorrect letters guessed:
         * ====================
         * */
        leftSide.setLayout(new BorderLayout());
        leftSide.setPreferredSize(new Dimension(500,400));
        tempLabel = new JLabel("Incorrect Guesses:");
        tempLabel.setForeground(new Color(136, 8, 8));
        tempLabel.setFont(new Font("Helvetica", Font.BOLD, 16));
        tempLabel.setName("incorrectGuesses");
        leftSide.add(tempLabel, BorderLayout.SOUTH);


        /*
         * ====================
         * (RightSide)Frame with the guess input box and the submit button:
         * ====================
         * */
        JPanel rightSide = new JPanel();
        rightSide.setLayout(new BorderLayout());

        //adding the label
        JLabel l1 = new JLabel("Enter Your Guess");
        l1.setForeground(new Color(136, 8, 8));
        l1.setHorizontalAlignment(JLabel.CENTER);
        l1.setFont(new Font("Helvetica", Font.BOLD, 16));
        rightSide.add(l1, BorderLayout.NORTH);

        //Right hand Side TextField:
        guess = new JTextField(5);
        guess.setFont(new Font(font, Font.PLAIN, 30));
        guess.setHorizontalAlignment(JTextField.CENTER);//center the text

        //Filter:
        //filter imported:
        ((AbstractDocument) guess.getDocument()).setDocumentFilter(new DocumentFilter(){
            //when a String is inserted (this method is called)
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                if (string == null)return;
                //length of the text < 2: (only want 1 letter to be inserted)
                if ((fb.getDocument().getLength() + string.length()) <= 1)
                    //character at index 0 (the string is always going to of length 1 - no errors)
                    super.insertString(fb, offset, string.toUpperCase(), attr);
            }
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                if (text == null)
                    return;
                if ((fb.getDocument().getLength() + text.length() - length) <= 1){
                    char ch = text.charAt(0);
                    //filter that the inserted string is a letter:
                    if(ch >= 'a' && ch <= 'z' || ch >= 'A' && ch <= 'Z'){
                        //string is a letter -> insert:
                        //converts it to uppercase before inserting:
                        super.replace(fb, offset, length, text.toUpperCase(), attrs);
                    }
                }
            }

        });

        //Action Command:
        guess.setActionCommand("textField");
        guess.addActionListener(this);

        //Adding the Text-field:
        JPanel textFieldPanel = new JPanel();
        textFieldPanel.setPreferredSize(new Dimension(150,50));
        textFieldPanel.add(guess);

        //Right hand Side Button:
        JButton submitGuess = new Custombutton("Guess").CustomButton();
        submitGuess.setPreferredSize(new Dimension(119, 50));
        submitGuess.setFont(new Font("Arial", Font.PLAIN, 24)); // Set the font size
        // Set the custom button UI to the playButton
        submitGuess.setUI(new CustomButtonUI());

        textFieldPanel.add(submitGuess);

        rightSide.add(textFieldPanel, BorderLayout.CENTER);

        //action listeners:
        submitGuess.addActionListener(this);


        //Size settings for Content's Panel content:
        content.add(leftSide,BorderLayout.CENTER);
        content.add(rightSide,BorderLayout.EAST);

        masterFrame.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //add action listener:
        if(e.getActionCommand().equals("Guess") || e.getActionCommand().equals("textField")){
            String l = guess.getText();
            boolean exists = false;
            //case 1 - l is empty:
            if(!l.equals("")){
                //loop through the array of components and find the matching names:
                Component[] components = letterPanel.getComponents();
                for(Component c: components){
                    if(c.getName() != null && c.getName().equals(l.toLowerCase())){
                        count+=1;
                        c.setVisible(true);
                        exists = true;
                    }
                }
                //if the player won:
                if(count == word.length()){
                    new WinnerScreen(word, username);
                    masterFrame.setVisible(false);
                }
                //Incorrect Letter so we need to draw the hangman
                if(!exists){
                    boolean toAdd = toAdd(l);
                    //if it's the first time the user entered this letter then add it:
                    //and also draw the hangman
                    if(toAdd){
                        incorrectLetters.add(l);
                        tempLabel.setText(tempLabel.getText()+" "+l);

                        //If the player lost:
                        if(incorrectLetters.size() == 7){
                            new LoserScreen(word, username);
                            masterFrame.setVisible(false);
                        }
                    }
                }

            }
            //call the method responsible for drawing the hangman
            PaintedHangman paint = new PaintedHangman(incorrectLetters.size());
            leftSide.add(paint, BorderLayout.CENTER);
        }
    }


    //draws out the letters:
    public JLabel drawLetter(String s){
        JLabel letterLabel = new JLabel(s);
        letterLabel.setName(s);
        letterLabel.setVisible(false);
        letterLabel.setFont(new Font(font, Font.BOLD, 50));//make the font larger
        return letterLabel;
    }

    //returns a label (underscores)
    public JLabel getUnderscore(){
        JLabel underscore = new JLabel("_");
        underscore.setFont(new Font(font, Font.BOLD, 50));//make the font larger
        return underscore;
    }

    //checks whether to add the letter or not:
    public boolean toAdd(String l){
        for(String s: incorrectLetters){
            if(s.equals(l))return false;
        }
        return true;
    }
}
