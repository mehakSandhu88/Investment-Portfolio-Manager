/*
 * name: mehak sandhu
 * student #: 1261399
 *  login id: msandh13
 * compile: javac ePortfolio.java GUI.java
 * run: java GUI filename.txt
 *  
*/
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

/**
 * GUI class for managing an ePortfolio. 
 * graphical  interface for managing investments, 
 * including buying, selling, updating, etc
 */
public class GUI extends JFrame {
/*
 * List of investments
 */
    private ArrayList<Investment> investments; 

    // Create the main frame
    JFrame frame = new JFrame("ePortfolio Management");

    /**
     * Constructs the GUI with the provided investments.
     * 
     * @param investments the list of investments to manage
     */
    public GUI(ArrayList<Investment> investments) {
        this.investments = investments; // Initialize investments list

        // Set up the frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close operation
        frame.setSize(600, 400); // Set frame size

        // Create the menu bar
        JMenuBar menuBar = new JMenuBar(); // Top menu bar

        // Create the "Commands" menu
        JMenu commandsMenu = new JMenu("Commands"); // Menu for commands
        menuBar.add(commandsMenu); // Add menu to menu bar

        // Add menu items to the "Commands" menu
        JMenuItem buyItem = new JMenuItem("Buy"); // Buy option
        JMenuItem sellItem = new JMenuItem("Sell"); // Sell option
        JMenuItem updateItem = new JMenuItem("Update"); // Update option
        JMenuItem getGainItem = new JMenuItem("Get Gain"); // Get gain option
        JMenuItem searchItem = new JMenuItem("Search"); // Search option
        JMenuItem quitItem = new JMenuItem("Quit"); // Quit option

        // Add menu items to the menu
        commandsMenu.add(buyItem); // Add buy to menu
        commandsMenu.add(sellItem); // Add sell to menu
        commandsMenu.add(updateItem); // Add update to menu
        commandsMenu.add(getGainItem); // Add get gain to menu
        commandsMenu.add(searchItem); // Add search to menu
        commandsMenu.add(quitItem); // Add quit to menu

        // Set up the content pane with a welcome message
        JPanel contentPane = new JPanel(); // Main content panel
        contentPane.setLayout(new BorderLayout()); // Border layout

        JLabel welcomeLabel = new JLabel("Welcome to ePortfolio Management!", JLabel.CENTER); // Welcome message
        contentPane.add(welcomeLabel, BorderLayout.CENTER); // Center the label
        JLabel wLabel = new JLabel("Select an option from the 'Commands' menu to begin.", JLabel.CENTER); // Guide message
        contentPane.add(wLabel, BorderLayout.CENTER); // Add guide message

        // Set the menu bar and content pane
        frame.setJMenuBar(menuBar); // Attach menu bar to frame
        frame.setContentPane(contentPane); // Set content pane

        // Add action listeners to menu items
        buyItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Call the buy method from ePortfolio
                ePortfolio.buy(investments);
            }
        });

        sellItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Call the sell method from ePortfolio
                ePortfolio.sell(investments);
            }
        });

        updateItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Call the update method from ePortfolio
                ePortfolio.update(investments);
            }
        });

        getGainItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Call the getGain method from ePortfolio
                ePortfolio.getGain(investments);
            }
        });

        searchItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Call the search method from ePortfolio
                ePortfolio.search(investments);
            }
        });

        quitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Exit the application
                System.exit(0);
            }
        });

        // Make the frame visible
        frame.setVisible(true); // Display the frame
    }

    /**
     * Main method for launching the application
     * 
     * command-line arguments: first argument is the filename 
     * for loading existing investments
     */
    public static void main(String[] args) {
        // filename needed for loading investments
        if (args.length != 1) {
            System.out.println("Error: Please specify a filename."); // Error message
            return; // Exit if no filename
        }
        String filename = args[0]; // Get filename

        // List to store investments
        ArrayList<Investment> investments = new ArrayList<>(); // Initialize list

        // Load existing investments from the file
        ePortfolio.loadInvestments(filename, investments); // Load investments

        // Start the GUI
        new GUI(investments); // Launch GUI
    }
}
