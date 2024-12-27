# Investment Portfolio Manager

## 1. General Problem
Stocks and mutual funds are popular investment options that investors commonly keep in their portfolios. Managing this portfolio requires regular activities such as buying, selling, updating prices, and calculating gains. The goal of this project is to develop an ePortfolio system that helps investors efficiently manage their investments in stocks and mutual funds.

## 2. Assumptions and Limitations
- **Investment Types Supported**: The program supports only **Stocks** and **Mutual Funds**.
- **Commission and Fees**: 
  - A commission of **$9.99** is charged for buying and selling stocks.
  - A redemption fee of **$45.00** is applied when selling mutual funds.
- **Input Handling**: The program assumes that users will enter valid input types and doesn't include extensive error handling for invalid inputs.
- **Data Persistence**: Data is not saved between sessions; everything is reset when the program is closed.
- **GUI**: The program now includes a **Graphical User Interface (GUI)** for better user interaction.

## 3. User Guide

### Requirements
1. Ensure **Java** is installed on your system.
2. Place the file `ePortfolio.java` in your working directory.

### Running the Program
1. Compile the program:
   ```bash
   javac ePortfolio.java
   ```
2. Run the program:
   ```bash
   java ePortfolio
   ```

### GUI Interface
Once the program is running, you will be presented with a **GUI** where you can select from the following options:

1. **Buy**: Purchase an investment by providing the type, symbol, quantity, and price.
2. **Sell**: Sell an investment by specifying the type, symbol, quantity, and sale price.
3. **Update**: Update the price of an existing investment.
4. **Get Gain**: Calculate the total gain for all investments in the portfolio.
5. **Search**: Search for investments by symbol, name, or price range.
6. **Quit**: Exit the program.

## 4. Test Plan

### 1. Testing the **Buy** Functionality
- **Buying a New Stock**: Test if the program allows purchasing a new stock and adds it to the portfolio.
- **Buying an Existing Stock**: Verify that the program correctly updates the quantity and book value of an existing stock.
- **Buying a New Mutual Fund**: Ensure that the program correctly adds a mutual fund to the portfolio.
- **Buying an Existing Mutual Fund**: Test if the program properly updates the quantity and book value of an existing mutual fund.
- **Invalid Input**: Ensure the program handles invalid inputs, such as negative quantities or prices.

### 2. Testing the **Sell** Functionality
- **Selling a Stock**: Verify that the program correctly reduces the quantity and updates the book value of a stock when sold.
- **Selling All Stock**: Check that a stock is removed from the portfolio when all shares are sold.
- **Selling a Mutual Fund**: Ensure that the redemption fee is correctly applied when selling mutual funds.
- **Selling More Than Available**: Test if the program handles the case where the user tries to sell more units than available.
- **Invalid Input**: Check that the program handles incorrect inputs appropriately.

### 3. Testing the **Update** Functionality
- **Updating Stock Prices**: Test if the program allows updating the price of a stock and correctly applies it to all relevant stocks.
- **Updating Mutual Fund Prices**: Ensure that mutual fund prices can be updated and applied correctly.

### 4. Testing the **Search** Functionality
- **Search by Symbol**: Ensure that searching by the exact symbol returns the correct investment details.
- **Search by Keywords**: Verify that searching by a keyword in the name returns the correct investments, including partial matches.
- **Search by Price Range**: Test searching investments within a specified price range.
- **Invalid Price Range**: Ensure that the program handles invalid price range inputs.
- **Combination of Criteria**: Test searches using combinations of symbol, keyword, and price range filters.

### 5. Testing **Gain Calculation**
- **Gain from Stocks**: Verify that the program correctly calculates the gain for stocks based on current prices.
- **Gain from Mutual Funds**: Ensure that the program correctly calculates the gain from mutual funds, including redemption fees.
- **Combined Gain**: Test that the program calculates the total gain for a portfolio containing both stocks and mutual funds.

### 6. Testing Exit Condition
- **Quit Program**: Verify that the program exits properly when the "Quit" option is selected.

### 7. Edge Cases
- **Empty Portfolio**: Ensure the program handles an empty portfolio correctly when performing buy, sell, search, update, and gain calculations.
- **Invalid Inputs**: Test how the program handles invalid inputs, such as negative numbers or improperly formatted price ranges.
- **Partial Commands**: Ensure the program can handle partial commands (e.g., “b” for buy or “se” for search).

## 5. Example Test Cases

| **Test Case**                         | **Input**                                         | **Output**                                              |
|---------------------------------------|--------------------------------------------------|---------------------------------------------------------|
| **Buy a New Stock**                   | Stock, Symbol: AAPL, Name: Apple, Quantity: 10, Price: 150 | Stock has been added                                     |
| **Buy an Existing Stock**             | Stock, Symbol: AAPL, Quantity: 5, Price: 155     | Stock has been bought                                    |
| **Sell a Stock Partially**            | Stock, Symbol: AAPL, Quantity: 5, Price: 160     | Gain: $[calculated value]                               |
| **Sell More Than Available**          | Stock, Symbol: AAPL, Quantity: 20, Price: 160    | Sale not possible: Quantity too large                   |
| **Sell All Stock**                    | Stock, Symbol: AAPL, Quantity: 5, Price: 160     | Gain: $[calculated value] and stock is removed          |
| **Search for Stock by Symbol**        | Symbol: AAPL                                     | Stock details: {AAPL details}                           |
| **Search by Keyword**                 | Name: Apple                                      | Stock details: {AAPL details}                           |
| **Search by Price Range**             | Price range: 100-200                              | Stock details: {AAPL details}                           |
| **Search with Invalid Price Range**   | Price range: abc                                  | Invalid price range. Try again.                         |
| **Update Stock Price**                | Symbol: AAPL, New price: 170                      | Updated price: $170                                      |
| **Calculate Gain for Stock**          | N/A                                              | Total gain: $[calculated value]                         |
| **Calculate Gain for Mutual Funds**   | N/A                                              | Total gain: $[calculated value]                         |
| **Quit Program**                      | Quit                                             | Goodbye and program exits                               |

## 6. Future Improvements
- Add more investment types (e.g., Bonds, ETFs).
- Implement advanced error handling and validation for input formats (e.g., proper price range formatting).
- Improve the **GUI** for a more user-friendly experience, including features like drag-and-drop or graphical charts for gains.
- Add the option for **data persistence**, allowing users to save and load portfolios.

-
