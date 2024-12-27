/*
 * name: mehak sandhu
 * student #: 1261399
 *  login id: msandh13
 * compile: javac ePortfolio.java GUI.java
 * run: java GUI filename.txt
 *  
*/

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * class is an ePortfolio for managing investments like stocks and mutual funds
 * has the functionality to buy, sell, update prices, get the gain, search
 * investments, and save/load from a file.
 */
public class ePortfolio {

    // Map to store the index of investment names to speed up search
    public static Map<String, List<Integer>> nameIndex = new HashMap<>();

    /**
     * The main method that starts the application
     * 
     * @param args Command line arguments (expects filename)
     */
    public static void main(String[] args) {

        // Ensure that a filename is provided as an argument
        if (args.length != 1) {
            System.out.println("Error: Please specify a filename.");
            return;
        }
        String filename = args[0];

        // List to store investments
        ArrayList<Investment> investments = new ArrayList<>();

        // Load existing investments from the file
        loadInvestments(filename, investments);

        // Scanner for user input
        Scanner scan = new Scanner(System.in);

        // Main loop for user interactions
        while (true) {
            try {
                // Display menu options
                System.out.println("1. Buy");
                System.out.println("2. Sell");
                System.out.println("3. Update");
                System.out.println("4. Get Gain");
                System.out.println("5. Search");
                System.out.println("6. Quit");
                System.out.print("Choose an option: ");

                String option = scan.nextLine().trim().toLowerCase();

                // Handle the user's choice
                if ("sell".startsWith(option)) {
                    sell(investments); // Call sell method
                } else if ("search".startsWith(option)) {
                    search(investments); // Call search method
                } else if ("buy".startsWith(option)) {
                    buy(investments); // Call buy method
                } else if ("update".startsWith(option)) {
                    update(investments); // Call update method
                } else if ("getgain".startsWith(option)) {
                    getGain(investments); // Call get gain method
                } else if ("quit".startsWith(option)) {
                    System.out.println("Goodbye");
                    // Save all investments to file before quitting
                    saveInvestments(filename, investments);
                    scan.close();
                    return;
                } else {
                    System.out.println("Invalid option. Try again.");
                }
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
        }

    }

    /**
     * Handles buying function for investments
     * 
     * @param investments list of investments to update
     */
    public static void buy(ArrayList<Investment> investments) {

        // Input investment type
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter kind of investment (stock/mutualfund): ");
        String type = scan.nextLine().trim().toLowerCase();

        // Input symbol
        System.out.print("Enter symbol: ");
        String symbol = scan.nextLine().trim();

        Investment newInvestment = null;

        // Check existing investments
        for (Investment inv : investments) {
            if (inv.getSymbol().equals(symbol)) {

                // Reject duplicate mutual fund
                if (type.equals("mutualfund") && inv instanceof MutualFund) {
                    System.out.println("Error: MutualFund exists.");
                    return;
                }

                // Handle stock or mutual fund
                if (inv instanceof Stock || inv instanceof MutualFund) {
                    System.out.print("Enter quantity to buy: ");
                    try {
                        int quantity = Integer.parseInt(scan.nextLine().trim());
                        if (quantity <= 0)
                            throw new NumberFormatException();

                        System.out.print("Enter price per unit: ");
                        double price = Double.parseDouble(scan.nextLine().trim());
                        if (price <= 0)
                            throw new NumberFormatException();

                        inv.buy(quantity, price);
                        System.out.println(type.equals("stock") ? "Stock bought." : "Mutual Fund bought.");
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Positive numbers required.");
                    }
                    return;
                }
            }
        }

        // Add new investment
        try {
            System.out.print("Enter name: ");
            String name = scan.nextLine().trim();
            System.out.print("Enter quantity: ");
            int quantity = Integer.parseInt(scan.nextLine().trim());
            System.out.print("Enter price per unit: ");
            double price = Double.parseDouble(scan.nextLine().trim());

            if (quantity <= 0 || price <= 0) {
                throw new IllegalArgumentException("Positive values required.");
            }

            // Create investment
            if (type.equals("stock")) {
                newInvestment = new Stock(symbol, name, quantity, price);
            } else if (type.equals("mutualfund")) {
                newInvestment = new MutualFund(symbol, name, quantity, price);
            } else {
                System.out.println("Invalid investment type.");
                return;
            }

            // Update investments
            investments.add(newInvestment);
            updateNameIndex(newInvestment, investments.size() - 1);
            System.out.println(type.equals("stock") ? "Stock added." : "Mutual Fund added.");

        } catch (NumberFormatException e) {
            System.out.println("Invalid number format.");
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid input: " + e.getMessage());
        }
    }

    /**
     * Handles selling functions of investments
     * 
     * @param investments list of investments to update
     */
    public static void sell(ArrayList<Investment> investments) {

        // Input symbol
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter symbol: ");
        String symbol = scan.nextLine().trim();

        // Input quantity
        System.out.print("Enter quantity to sell: ");
        int quantity = 0;
        try {
            quantity = Integer.parseInt(scan.nextLine().trim());
            if (quantity <= 0)
                throw new NumberFormatException("Quantity must be positive.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid input: Quantity must be positive.");
            return;
        }

        // Input sale price
        System.out.print("Enter sale price: ");
        double price = 0.0;
        try {
            price = Double.parseDouble(scan.nextLine().trim());
            if (price <= 0)
                throw new NumberFormatException("Price must be positive.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid input: Price must be positive.");
            return;
        }

        // Find investment and sell
        for (int i = 0; i < investments.size(); i++) {
            Investment inv = investments.get(i);
            if (inv.getSymbol().equals(symbol)) {
                double payment = inv.sell(quantity, price);
                System.out.println("Gain: $" + payment);

                // Remove investment if quantity is zero
                if (inv.getQuantity() == 0) {
                    investments.remove(i);
                    removeFromNameIndex(inv, i); // Update name index
                    System.out.println("Investment removed from portfolio.");
                }
                return;
            }
        }
        // Investment not found
        System.out.println("Investment with symbol " + symbol + " not found.");
    }

    /**
     * Updates the price for investments
     * 
     * @param investments list of investments to update
     */
    public static void update(ArrayList<Investment> investments) {

        // Input new price for each investment
        Scanner scan = new Scanner(System.in);
        for (Investment inv : investments) {
            System.out.println("Current price of " + inv.getSymbol() + ": $" + inv.getPrice());
            System.out.print("Enter new price: ");
            double newPrice = 0;
            try {
                newPrice = Double.parseDouble(scan.nextLine().trim());
                if (newPrice <= 0) {
                    throw new NumberFormatException("Price must be positive.");
                }
                inv.setPrice(newPrice); // Update price
            } catch (NumberFormatException e) {
                System.out.println("Invalid input: Price must be positive.");
            }
        }

        // Confirmation message
        System.out.println("Prices updated successfully.");
    }

    /**
     * Calculates & displays total gain
     * 
     * @param investments list of investments to calculate from
     * @return total gain for all investments
     */
    public static double getGain(ArrayList<Investment> investments) {

        // Calculate and display gain for each investment
        double totalGain = 0.0;
        for (Investment inv : investments) {
            double gain = inv.getGain(inv.getPrice());
            totalGain += gain;
            System.out.println("Gain for " + inv.getSymbol() + ": $" + gain);
        }

        // Display total gain
        System.out.println("Total Gain: $" + totalGain);
        return totalGain;
    }

    /**
     * Search investments by symbol or name
     * 
     * @param investments list of investments to search
     */
    public static void search(ArrayList<Investment> investments) {

        // Input search term
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter symbol or name to search: ");
        String searchTerm = scan.nextLine().trim().toLowerCase();

        // Search for matching investment
        boolean found = false;
        for (Investment inv : investments) {
            if (inv.getSymbol().toLowerCase().contains(searchTerm)
                    || inv.getName().toLowerCase().contains(searchTerm)) {
                System.out.println(inv); // Display matching investment
                found = true;
            }
        }

        // No matching investment found
        if (!found) {
            System.out.println("No investments found matching your search.");
        }
    }

    /**
     * Loads investments from a file
     * 
     * @param filename    The file to load investments from
     * @param investments List to store loaded investments
     */
    // Define method
    public static void loadInvestments(String filename, ArrayList<Investment> investments) {

        // Try loading investments from file
        try {
            Scanner fileScanner = new Scanner(new File(filename));

            // Process each line in the file
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine().trim();
                String[] parts = line.split(",");
                if (parts.length != 5) {
                    continue; // Skip invalid lines
                }

                // Extract investment details
                String type = parts[0];
                String symbol = parts[1];
                String name = parts[2];
                int quantity = Integer.parseInt(parts[3]);
                double price = Double.parseDouble(parts[4]);

                Investment investment = null;

                // Create investment based on type
                if (type.equals("stock")) {
                    investment = new Stock(symbol, name, quantity, price);
                } else if (type.equals("mutualfund")) {
                    investment = new MutualFund(symbol, name, quantity, price);
                }

                // Add valid investment to list
                if (investment != null) {
                    investments.add(investment);
                    updateNameIndex(investment, investments.size() - 1);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filename);
        }
    }

    /**
     * Updates the index map for quick name-based search
     * 
     * @param investment The investment object to index
     * @param index      The index of the investment in the investments list
     */
    // Define method
    public static void updateNameIndex(Investment investment, int index) {

        // Get or create a list for the investment's name
        List<Integer> indexes = nameIndex.getOrDefault(investment.getName(), new ArrayList<>());

        // Add the current index to the list
        indexes.add(index);

        // Update the name index map
        nameIndex.put(investment.getName(), indexes);
    }

    /**
     * Removes the investment from the name index
     * 
     * @param investment The investment to remove
     * @param index      The index of the investment to remove
     */
    public static void removeFromNameIndex(Investment investment, int index) {

        // Get the list of indexes for the investment's name
        List<Integer> indexes = nameIndex.get(investment.getName());
        if (indexes != null) {
            // Remove the specified index
            indexes.remove(Integer.valueOf(index));
        }
    }

    /**
     * Saves all investments to the file
     * 
     * @param filename    The name of the file to save to
     * @param investments List of investments to save
     */
    public static void saveInvestments(String filename, ArrayList<Investment> investments) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            // Write each investment to the file
            for (Investment inv : investments) {
                writer.println(inv);
            }
        } catch (IOException e) {
            System.out.println("Error saving to file: " + e.getMessage());
        }
    }

}

/**
 * Abstract super class representing an investment.
 * Provides common properties and behaviors for various investment types.
 */
abstract class Investment {
    protected String symbol; // Investment symbol (e.g., stock ticker or fund code)
    protected String name; // Name of the investment
    protected int quantity; // Number of units or shares held
    protected double price; // Price per unit or share
    protected double bookValue; // Total book value of the investment

    /**
     * Constructor to initialize an investment.
     *
     * @param symbol   The symbol of the investment.
     * @param name     The name of the investment.
     * @param quantity The quantity of units or shares.
     * @param price    The price per unit or share.
     */
    public Investment(String symbol, String name, int quantity, double price) {
        this.symbol = symbol;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.bookValue = quantity * price; // Initial book value
    }

    // Abstract methods that subclasses must implement

    /**
     * Computes the book value of the investment.
     * 
     * @return The book value as a double.
     */
    public abstract double getBookValue();

    /**
     * Calculates the gain based on the current price.
     *
     * @param currentPrice The current price of the investment.
     * @return The calculated gain as a double.
     */
    public abstract double getGain(double currentPrice);

    /**
     * Processes a sell transaction for the investment.
     *
     * @param quantity The quantity to sell.
     * @param price    The price per unit during the sale.
     * @return The payment received after the sale.
     */
    public abstract double sell(int quantity, double price);

    /**
     * Processes a buy transaction for the investment.
     *
     * @param quantity The quantity to buy.
     * @param price    The price per unit during the purchase.
     */
    public abstract void buy(int quantity, double price);

    // Getter methods

    public String getSymbol() {
        return symbol;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double newPrice) {
        this.price = newPrice;
    }

    @Override
    public String toString() {
        return "Symbol: " + symbol + ", Name: " + name + ", Quantity: " + quantity + ", Price: $" + price;
    }

    // Overriding equals and hashCode for object comparison

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Investment that = (Investment) obj;
        return symbol.equals(that.symbol) && name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return 31 * symbol.hashCode() + name.hashCode();
    }
}

/**
 * Concrete subclass representing a stock investment.
 */
class Stock extends Investment {
    private static final double COMMISSION_RATE = 9.99; // Commission fee for stock transactions

    /**
     * Constructs a Stock object with the specified details.
     *
     * @param symbol   The stock symbol.
     * @param name     The stock name.
     * @param quantity The number of shares held.
     * @param price    The price per share.
     */
    public Stock(String symbol, String name, int quantity, double price) {
        super(symbol, name, quantity, price);
    }

    @Override
    public double getBookValue() {
        return this.quantity * this.price;
    }

    @Override
    public double getGain(double currentPrice) {
        return (currentPrice - this.price) * this.quantity - COMMISSION_RATE;
    }

    @Override
    public double sell(int quantity, double price) {
        if (quantity > this.quantity) {
            System.out.println("Not enough stock to sell.");
            return 0;
        }
        double payment = (quantity * price) - COMMISSION_RATE; // Subtract commission fee
        this.quantity -= quantity;
        return payment;
    }

    @Override
    public void buy(int quantity, double price) {
        this.quantity += quantity;
        this.price = price; // Update the price per share
    }

    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj))
            return false;
        Stock stock = (Stock) obj;
        return quantity == stock.quantity && Double.compare(price, stock.price) == 0;
    }

    @Override
    public int hashCode() {
        return super.hashCode() * 31 + quantity + Double.hashCode(price);
    }
}

/**
 * Concrete subclass representing a mutual fund investment.
 */
class MutualFund extends Investment {
    private static final double REDEMPTION_FEE = 45.0; // Redemption fee for mutual fund sales

    /**
     * Constructs a MutualFund object with the specified details.
     *
     * @param symbol   The mutual fund symbol.
     * @param name     The mutual fund name.
     * @param quantity The number of units held.
     * @param price    The price per unit.
     */
    public MutualFund(String symbol, String name, int quantity, double price) {
        super(symbol, name, quantity, price);
    }

    @Override
    public double getBookValue() {
        return this.quantity * this.price;
    }

    @Override
    public double getGain(double currentPrice) {
        return (currentPrice - this.price) * this.quantity - REDEMPTION_FEE;
    }

    @Override
    public double sell(int quantity, double price) {
        if (quantity > this.quantity) {
            System.out.println("Not enough mutual funds to sell.");
            return 0;
        }
        double payment = (quantity * price) - REDEMPTION_FEE; // Subtract redemption fee
        this.quantity -= quantity;
        return payment;
    }

    @Override
    public void buy(int quantity, double price) {
        this.quantity += quantity;
        this.price = price; // Update the price per unit
    }

    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj))
            return false;
        MutualFund mutualFund = (MutualFund) obj;
        return quantity == mutualFund.quantity && Double.compare(price, mutualFund.price) == 0;
    }

    @Override
    public int hashCode() {
        return super.hashCode() * 31 + quantity + Double.hashCode(price);
    }
}
