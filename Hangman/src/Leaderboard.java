import javax.swing.*;
import java.sql.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Comparator;
import java.util.TreeMap;

public class Leaderboard extends JFrame {

    // private variables
    private TreeMap<Integer,String> map;
    private JFrame frame;
    private JTable table;

    private String usernameToPass;

    Leaderboard(String usernameToPass){
        this.usernameToPass = usernameToPass;

        // created a new hashmap:
        map = new TreeMap<>(new DescendingKeyComparator());
        try{
            Connection con = DriverManager.getConnection("jdbc:sqlite:users.sqlite");
            Statement st = con.createStatement();
            // Retrieve the Data:
            ResultSet rs = st.executeQuery("SELECT * FROM users");
            // pass all the values that we retrieved to the hashmap:
            while(rs.next()){
                map.put(rs.getInt("score"),rs.getString("username"));
            }
            con.close();
        } catch (SQLException e) {
            System.out.println(e);
        }

        // frame:
        frame = new JFrame("Hangman Game");
        frame.setSize(750, 700); // size of the canvas
        frame.setLocationRelativeTo(null);
        frame.setBackground(Color.decode("#eeeeee"));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Panel to store the label and the button:
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc1 = new GridBagConstraints();
        gbc1.gridx = 0;
        gbc1.gridy = 0;
        gbc1.anchor = GridBagConstraints.CENTER;
        gbc1.insets = new Insets(20, 50, 0, 0); // padding

        // leaderboard label
        JLabel leaderboardLabel = new JLabel("LEADERBOARD", SwingConstants.CENTER);
        leaderboardLabel.setFont(new Font("Arial", Font.BOLD, 36));
        leaderboardLabel.setForeground(new Color(136, 8, 8));
        leaderboardLabel.setPreferredSize(new Dimension(750, 50));
        //ADD the label:
        panel.add(leaderboardLabel, gbc1);

        //alter the constraints:
        gbc1.gridy = 1;
        // play again button
        JButton playAgainButton = new Custombutton("Play Again").CustomButton();
        playAgainButton.setUI(new CustomButtonUI());
        playAgainButton.setPreferredSize(new Dimension(235, 75));
        playAgainButton.addActionListener(e -> {
            //restart the game:
            frame.setVisible(false);
            new HangmanGui(new Main().getWord(), usernameToPass);
        });
        //ADD the button:
        panel.add(playAgainButton, gbc1);

        //Add the panel to the frame:
        frame.add(panel, BorderLayout.NORTH);

        // leaderboard table (column names)
        String[] columnNames = {"Rank", "Username", "Score"};
        //declare a model:
        //in this case we made the table non-editable:
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

        };
        //draw out the table:
        //adds the rows
        int rank = 1;
        for (Integer score : map.keySet()) {
            String username = map.get(score);
            Object[] rowData = {rank++, username, score};
            model.addRow(rowData);
        }

        table = new JTable(model);
        //Don't allow the user to select a cell:
        table.setCellSelectionEnabled(false);
        //increases the width of the column:
        table.getColumnModel().getColumn(0).setPreferredWidth(150);
        table.getColumnModel().getColumn(1).setPreferredWidth(400);
        table.getColumnModel().getColumn(2).setPreferredWidth(200);

        //increases the height of the row:
        table.setRowHeight(25);
        // set the font size of the header
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 24));
        //change the colour:
        table.getTableHeader().setBackground(new Color(136, 8, 8));
        table.getTableHeader().setForeground(Color.WHITE);

        //set it as a scroll panel and add some padding on the sides:
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        //add it to the frame:
        frame.add(scrollPane, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    //sort the treemap in descending order:
    public class DescendingKeyComparator implements Comparator<Integer> {
        @Override
        public int compare(Integer o1, Integer o2) {
            return o2.compareTo(o1);
        }
    }
}
