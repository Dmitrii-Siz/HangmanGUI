import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main {
    private static ArrayList<String> targetWords = new ArrayList<>();

    public static void main(String[] args) throws FileNotFoundException {
        Scanner in = new Scanner(new File("wordlist.txt"));
        while(in.hasNext()){
            targetWords.add(in.next());
        }

        //create the table:
        try{
            Connection con = DriverManager.getConnection("jdbc:sqlite:users.sqlite");
            Statement st = con.createStatement();
            //CREATE the table
            st.execute("CREATE TABLE IF NOT EXISTS users(id INTEGER PRIMARY KEY AUTOINCREMENT, username VARCHAR(24) NOT NULL, score INTEGER(5))");
            con.close();
        } catch (SQLException e) {
            System.out.println(e);
        }

        //new runnable:
        SwingUtilities.invokeLater(() ->new StartScreen(getWord()));
    }

    //random word:
    public static String getWord(){
        Random r = new Random();
        String word = targetWords.get(r.nextInt(targetWords.size()));
        System.out.println("The target word is: " + word);
        return word;
    }
}
