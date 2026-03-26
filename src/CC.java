import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.util.*;
import java.sql.*;
import  java.io.*;
import java.util.regex.Pattern;

public class CC {
    public static void main(String[] args) throws Exception {
        String URL = "jdbc:mysql://localhost:3306/coincraft";
        String user = "root";
        String pass = "Yash@1234";
        Scanner sc = new Scanner(System.in);
        Connection con = null;
        String drivername = "com.mysql.cj.jdbc.Driver";
        try {
            Class.forName(drivername);
            con = DriverManager.getConnection(URL, user, pass);
        } catch (Exception e) {
            System.out.println("connection failed");
            System.out.println("reason=" + e);
            return;
        }
        boolean b = true;
        while (b) {
            System.out.println("===== Welcome to crptoCoin System =====");
            System.out.println("1.Login your account");
            System.out.println("2.Register your account");
            System.out.println("3.Exit");
            System.out.print("Enter your choice:");
            int choice = sc.nextInt();
            System.out.println();
            switch (choice) {
                case 1:
                    int userId = loginUser(con, sc);
                    if (userId != -1) {
                        userMenu(con, sc, userId); // enter full user menu
                    }
                    break;
                case 2:
                    registerUser(con, sc);
                    break;
                case 3:
                    System.out.println("Thank you...!");
                    System.out.println("Exiting!!");
                    b = false;
                    break;
                default:
                    System.out.println("Invalid choice!");
                    break;
            }
        }
    }


    //Login method with OTP verification
    static int loginUser(Connection con, Scanner sc) {
        try {

            while (true) {   // loop until correct login
                System.out.print("Enter your User ID: ");
                int id = sc.nextInt();
                sc.nextLine();

                System.out.print("Enter your password: ");
                int password = sc.nextInt();
                sc.nextLine();

                PreparedStatement ps = con.prepareStatement(
                        "SELECT * FROM login WHERE user_id = ? AND password = ?");
                ps.setInt(1, id);
                ps.setInt(2, password);

                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    System.out.println("Login successfully");
                    return id;   // correct → exit loop
                } else {
                    System.out.println("Invalid ID or Password. Try again!");
                }
            }

        } catch (Exception e) {
            System.out.println("Error login: " + e.getMessage());
            return -1;
        }
    }

    //register
    static void registerUser(Connection con, Scanner sc) {
        try {
            System.out.print("Enter a new User ID: ");
            int userId = sc.nextInt();
            sc.nextLine();

            System.out.println("Enter your name:");
            String name=sc.nextLine();

            System.out.print("Enter a new password: ");
            int password = sc.nextInt();

            System.out.print("Enter your email id: ");
            String email=sc.next();

            int otp = (int) (Math.random() * 9000) + 1000;
            String sub = "Register";
            String msg = "Your otp is " + otp + ". do not share otp with anyone.";
            sendEmail(email, sub, msg);

            boolean b = true;
            while (b) {
                System.out.print("Enter your otp which send you in your Email: ");
                int enteredOtp = sc.nextInt();
                if (enteredOtp == otp) {
                    System.out.println("Register susscessfully");
                    b = false;
                    break;
                } else {
                    System.out.println("OTP incorrect Register fail");
                }
            }

            PreparedStatement ps = con.prepareStatement("INSERT INTO login  VALUES (?,?,?,0,?)");
            ps.setInt(1, userId);
            ps.setString(2,name);
            ps.setInt(3, password);
            ps.setString(4,email);
            ps.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println(" User  ID already exists. Try a different one.");
        } catch (Exception e){
            System.out.println("Registration error: " + e.getMessage());
        }
    }

    //User Menu
    static void userMenu(Connection con,Scanner sc,int userId) throws SQLException {
        boolean loggedIn = true;

        while (loggedIn) {
            System.out.println("\n=== User Dashboard ===");
            System.out.println("1. View Wallet Balance");
            System.out.println("2. View Portfolio");
            System.out.println("3. List Available Coins");
            System.out.println("4. Buy Coin");
            System.out.println("5. Sell Coin");
            System.out.println("6. View All Coin Sorted By Price(Using AVL tree)");//AVL
            System.out.println("7. View top5 Coin(High price)(Using Stack) ");//STACK
            System.out.println("8. View Transaction History(Using Linked List)");//LIKEDLIST
            System.out.println("9. Coin Details");
            System.out.println("10. Show Letest News");
            System.out.println("11. Logout");
            System.out.print("Choose an option: ");
            int option = sc.nextInt();
            sc.nextLine(); // clear buffer

            switch (option) {
                case 1:
                    checkWalletBalance(con, userId);
                    break;

                case 2:
                    viewPortfolio(con, userId);
                    break;

                case 3:
                    listCoins(con);
                    break;

                case 4:
                    buyCoin(con, sc, userId);
                    break;

                case 5:
                    sellCoin(con, sc, userId);
                    break;

                case 6:
                    displayCoinsUsingAVL(con);
                    break;

                case 7:
                    top5CoinsByPrice(con);
                    break;

                case 8:
                    fetchTransactionHistoryFromLog(userId);
                    break;

                case 9:
                    String query = "SELECT coin_id, coin_name FROM coins";
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                try{
                    while (rs.next()) {
                        int coinId = rs.getInt("coin_id");
                        String coinName = rs.getString("coin_name");
                        System.out.println("ID: " + coinId + ", Name: " + coinName);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                    System.out.print("Enter coid id which you want to know details:");
                int n=sc.nextInt();
                printCoinInfo(n);
                    break;

                case 10:
                    showNews(con);
                    break;

                case 11:
                    System.out.println("Logging out...");
                    loggedIn = false;
                    break;

                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    //Check Balance
    static void checkWalletBalance(Connection con, int userId) {
        try {
            PreparedStatement ps = con.prepareStatement("SELECT Balance FROM login WHERE user_id = ?");
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                double wallet = rs.getDouble("Balance");
                System.out.println(" Your current wallet balance is: ₹" + wallet);
            } else {
                System.out.println("User not found.");
            }
        } catch (Exception e) {
            System.out.println("Error checking wallet balance: " + e.getMessage());
        }
        Scanner sc=new Scanner(System.in);
        boolean b=true;
        while (b)
        {
            System.out.println("1.Add money");
            System.out.println("2.Withdraw money");
            System.out.println("3.Exit");
            System.out.print("Choose option:");
            int n=sc.nextInt();
            switch (n)
            {
                case 1:
                    CC.addMoneyToWallet(con,new Scanner(System.in),userId);
                    break;
                case 2:
                    CC.withdrawMoneyFromWallet(con,new Scanner(System.in),userId);
                    break;
                case 3:
                    b=false;
                    break;
                default:
                    System.out.print("Enter valid choice.");
                    break;
            }
        }
    }

    // Add Money to Wallet
    static void addMoneyToWallet(Connection con, Scanner sc, int userId) {
        try {
            System.out.print("Enter amount to add: ₹");
            double amount = sc.nextDouble();

            if (amount <= 0) {
                System.out.println("Amount must be greater than zero.");
                return;
            }

            PreparedStatement ps = con.prepareStatement(
                    "UPDATE login SET Balance = Balance + ? WHERE user_id = ?"
            );
            ps.setDouble(1, amount);
            ps.setInt(2, userId);

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("₹" + amount + " has been added to your wallet successfully.");
            } else {
                System.out.println("Failed to add money. User not found.");
            }

        } catch (Exception e) {
            System.out.println("Error while adding money: " + e.getMessage());
        }
    }

    // Withdraw Money from Wallet
    static void withdrawMoneyFromWallet(Connection con, Scanner sc, int userId) {
        try {
            System.out.print("Enter amount to withdraw: ₹");
            double amount = sc.nextDouble();

            if (amount <= 0) {
                System.out.println("Amount must be greater than zero.");
                return;
            }

            // Get current balance
            PreparedStatement getBalance = con.prepareStatement(
                    "SELECT Balance FROM login WHERE user_id = ?"
            );
            getBalance.setInt(1, userId);
            ResultSet rs = getBalance.executeQuery();

            if (rs.next()) {
                double currentBalance = rs.getDouble("Balance");

                if (amount > currentBalance) {
                    System.out.println("Insufficient funds. Your current balance is ₹" + currentBalance);
                    return;
                }

                // Deduct from wallet
                PreparedStatement withdrawStmt = con.prepareStatement(
                        "UPDATE login SET Balance = Balance - ? WHERE user_id = ?"
                );
                withdrawStmt.setDouble(1, amount);
                withdrawStmt.setInt(2, userId);

                int rowsAffected = withdrawStmt.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("₹" + amount + " has been withdrawn successfully.");
                } else {
                    System.out.println("Failed to withdraw. User not found.");
                }

            } else {
                System.out.println("User not found.");
            }

        } catch (Exception e) {
            System.out.println("Error while withdrawing money: " + e.getMessage());
        }
    }


    //Buy Coins
    static void buyCoin(Connection con, Scanner sc, int userId) {
        try {
            // Show all available coins
            System.out.println("\n--- Available Coins ---");
            PreparedStatement coinStmt = con.prepareStatement("SELECT * FROM coins");
            ResultSet coinRs = coinStmt.executeQuery();
            while (coinRs.next()) {
                System.out.println("ID: " + coinRs.getInt("coin_id") +
                        ", Name: " + coinRs.getString("coin_name") +
                        ", Price: ₹" + coinRs.getDouble("price"));
            }
            System.out.print("Enter Coin ID to buy: ");
            int coinId = sc.nextInt();

            System.out.print("Enter quantity to buy: ");
            int quantity = sc.nextInt();

            // Get coin price
            PreparedStatement priceStmt = con.prepareStatement("SELECT coin_name, price FROM coins WHERE coin_id = ?");
            priceStmt.setInt(1, coinId);
            ResultSet priceRs = priceStmt.executeQuery();

            if (!priceRs.next()) {
                System.out.println("Invalid coin ID.");
                return;
            }

            String coin_name = priceRs.getString("coin_name");
            double price = priceRs.getDouble("price");
            double totalCost = price * quantity;

            // Get user wallet balance
            PreparedStatement walletStmt = con.prepareStatement("SELECT Balance FROM login WHERE user_id = ?");
            walletStmt.setInt(1, userId);
            ResultSet walletRs = walletStmt.executeQuery();
            walletRs.next();
            double wallet = walletRs.getDouble("Balance");

            // Check if user has enough money
            if (wallet < totalCost) {
                System.out.println(" Not enough balance. You need ₹" + totalCost);
                return;
            }

            // Deduct money from wallet
            PreparedStatement updateWallet = con.prepareStatement("UPDATE login SET Balance = Balance - ? WHERE user_id = ?");
            updateWallet.setDouble(1, totalCost);
            updateWallet.setInt(2, userId);
            updateWallet.executeUpdate();

            // Update or insert holdings
            PreparedStatement checkHoldings = con.prepareStatement("SELECT quantity FROM user_holdings WHERE user_id = ? AND coin_id = ?");
            checkHoldings.setInt(1, userId);
            checkHoldings.setInt(2, coinId);
            ResultSet holdingRs = checkHoldings.executeQuery();

            if (holdingRs.next()) {
                // Update existing holding
                PreparedStatement updateHolding = con.prepareStatement(
                        "UPDATE user_holdings SET quantity = quantity + ? WHERE user_id = ? AND coin_id = ?"
                );
                updateHolding.setInt(1, quantity);
                updateHolding.setInt(2, userId);
                updateHolding.setInt(3, coinId);
                updateHolding.executeUpdate();
            } else {
                // Insert new holding
                PreparedStatement insertHolding = con.prepareStatement(
                        "INSERT INTO user_holdings (user_id, coin_id, quantity) VALUES (?, ?, ?)"
                );
                insertHolding.setInt(1, userId);
                insertHolding.setInt(2, coinId);
                insertHolding.setInt(3, quantity);
                insertHolding.executeUpdate();
            }

            System.out.println("" +
                    " Successfully bought " + quantity + " coins!");
            CC.logTransactionToFile(userId,coin_name,"buy",coinId,quantity,totalCost);

        } catch (Exception e) {
            System.out.println("Error while buying coin: " + e.getMessage());
        }
    }
    // Log transaction to a text file
    static void logTransactionToFile(int userId,String coin_name,String type,int coinId, int quantity, double totalAmount) {
        try {
            FileWriter fw = new FileWriter("UserID_"+userId+"_transaction_history.txt", true); // 'true' for append mode
            BufferedWriter bw = new BufferedWriter(fw);

            String log = String.format("Coin Name: %s | %s | Quantity: %d | Amount: ₹%.2f | Time: %s \n",
                    coin_name,type, quantity, totalAmount, new java.util.Date());

            bw.write(log);
            bw.close();
        } catch (IOException e) {
            System.out.println("Error writing transaction to file: " + e.getMessage());
        }
    }

    //sell Coin
    static void sellCoin(Connection con, Scanner sc, int userId) {
        try {
            System.out.println("\n--- Your Holdings ---");
            PreparedStatement holdingsStmt = con.prepareStatement(
                    "SELECT c.coin_id, c.coin_name, c.price, uh.quantity FROM user_holdings uh JOIN coins c ON uh.coin_id = c.coin_id WHERE uh.user_id = ?");
            holdingsStmt.setInt(1, userId);
            ResultSet rs = holdingsStmt.executeQuery();
            boolean hasHoldings = false;
            while (rs.next()) {
                hasHoldings = true;
                System.out.println("Coin ID: " + rs.getInt("coin_id") +
                        ", Name: " + rs.getString("coin_name") +
                        ", Quantity: " + rs.getInt("quantity") +
                        ", Price: ₹" + rs.getDouble("price"));
            }

            if (!hasHoldings) {
                System.out.println(" You don’t own any coins to sell.");
                return;
            }

            System.out.print("Enter Coin ID to sell: ");
            int coinId = sc.nextInt();

            System.out.print("Enter quantity to sell: ");
            int quantityToSell = sc.nextInt();

            // Check if user has enough quantity
            PreparedStatement checkQty = con.prepareStatement(
                    "SELECT quantity FROM user_holdings WHERE user_id = ? AND coin_id = ?"
            );
            checkQty.setInt(1, userId);
            checkQty.setInt(2, coinId);
            ResultSet qtyRs = checkQty.executeQuery();

            if (!qtyRs.next()) {
                System.out.println(" You don’t own this coin.");
                return;
            }

            int ownedQty = qtyRs.getInt("quantity");
            if (quantityToSell > ownedQty) {
                System.out.println(" You only own " + ownedQty + " coins.");
                return;
            }

            // Get coin price
            PreparedStatement priceStmt = con.prepareStatement("SELECT price FROM coins WHERE coin_id = ?");
            priceStmt.setInt(1, coinId);
            ResultSet priceRs = priceStmt.executeQuery();
            priceRs.next();
            double price = priceRs.getDouble("price");
            double totalGain = price * quantityToSell;

            // Add money to wallet
            PreparedStatement updateWallet = con.prepareStatement(
                    "UPDATE login SET Balance = Balance + ? WHERE user_id = ?"
            );
            updateWallet.setDouble(1, totalGain);
            updateWallet.setInt(2, userId);
            updateWallet.executeUpdate();

            // Update or remove holdings
            if (quantityToSell == ownedQty) {
                // Delete holding
                PreparedStatement deleteHolding = con.prepareStatement(
                        "DELETE FROM user_holdings WHERE user_id = ? AND coin_id = ?"
                );
                deleteHolding.setInt(1, userId);
                deleteHolding.setInt(2, coinId);
                deleteHolding.executeUpdate();
            } else {
                // Reduce quantity
                PreparedStatement updateHolding = con.prepareStatement(
                        "UPDATE user_holdings SET quantity = quantity - ? WHERE user_id = ? AND coin_id = ?"
                );
                updateHolding.setInt(1, quantityToSell);
                updateHolding.setInt(2, userId);
                updateHolding.setInt(3, coinId);
                updateHolding.executeUpdate();
            }
            // Get coin name and price
            PreparedStatement coinStmt = con.prepareStatement(
                    "SELECT coin_name, price FROM coins WHERE coin_id = ?"
            );
            coinStmt.setInt(1, coinId);
            ResultSet coinRs = coinStmt.executeQuery();

            String coinName = "";

            if (coinRs.next()) {
                coinName = coinRs.getString("coin_name");
            }

            System.out.println(" Successfully sold " + quantityToSell + " coins for ₹" + totalGain);
            CC.logTransactionToFile(userId,coinName,"sell",coinId,quantityToSell,totalGain);

        } catch (Exception e) {
            System.out.println("Error while selling coin: " + e.getMessage());
        }
    }

    //list of coin
    static void listCoins(Connection con) {
        try {
            updateCoinPricesRandomly(con);
            System.out.println("\n--- Available Coins ---");
            String query = "SELECT * FROM coins";
            PreparedStatement stmt = con.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("coin_id");
                String name = rs.getString("coin_name");
                double price = rs.getDouble("price");

                System.out.println("ID: " + id + ", Name: " + name + ", Price: ₹" + price);
            }

        } catch (SQLException e) {
            System.out.println(" Error retrieving coin list: " + e.getMessage());
        }
    }
    static void viewPortfolio(Connection con, int userId)
    {
        try {
            System.out.println("\n=== Your Portfolio ===");
            String query = "SELECT c.coin_id, c.coin_name, c.price, uh.quantity " +
                    "FROM user_holdings uh " +
                    "JOIN coins c ON uh.coin_id = c.coin_id " +
                    "WHERE uh.user_id = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();

            boolean hasHoldings = false;
            double totalPortfolioValue = 0;

            while (rs.next()) {
                hasHoldings = true;
                int coinId = rs.getInt("coin_id");
                String coinName = rs.getString("coin_name");
                double price = rs.getDouble("price");
                int quantity = rs.getInt("quantity");
                double value = price * quantity;
                totalPortfolioValue += value;

                System.out.println("Coin ID: " + coinId +
                        ", Name: " + coinName +
                        ", Quantity: " + quantity +
                        ", Price: ₹" + price +
                        ", Value: ₹" + String.format("%.2f", value));
            }

            if (!hasHoldings) {
                System.out.println("You don't own any coins.");
            } else {
                System.out.println("\nTotal Portfolio Value: ₹" + String.format("%.2f", totalPortfolioValue));
            }

        } catch (Exception e) {
            System.out.println("Error viewing portfolio: " + e.getMessage());
        }
    }
    static void updateCoinPricesRandomly(Connection conn) {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM coins");

            while (rs.next()) {
                int coinId = rs.getInt("coin_id");
                double oldPrice = rs.getDouble("price");
                String symbol = rs.getString("sumbol");

                double percentageChange = (Math.random() * 10) - 5;
                double newPrice = oldPrice + (oldPrice * percentageChange / 100);
                newPrice = Math.round(newPrice * 100.0) / 100.0;

                PreparedStatement updateStmt = conn.prepareStatement(
                        "UPDATE coins SET price = ? WHERE coin_id = ?"
                );
                updateStmt.setDouble(1, newPrice);
                updateStmt.setInt(2, coinId);
                updateStmt.executeUpdate();
            }
        } catch (Exception e) {
            System.out.println("Error updating coin prices: " + e.getMessage());
        }
    }

    static void showNews(Connection conn) throws SQLException {
        // Query for a random row
        String sql = "SELECT * FROM coin_latest_news ORDER BY RAND() LIMIT 1";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        // Print random row
        if (rs.next()) {
            int id = rs.getInt("id");
            String coin = rs.getString("coin_name");
            String title = rs.getString("news_title");
            String url = rs.getString("news_url");
            Timestamp publishedAt = rs.getTimestamp("published_at");

            System.out.println("News:");
            System.out.printf("Coin: %s | Title: %s | URL: %s | Published At: %s%n",
                    id, coin, title, url, publishedAt.toString());
        } else {
            System.out.println("No data found.");
        }
    }


    static void printCoinInfo(int coinId) {
        File file = new File("details.txt");
        boolean found = false;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            StringBuilder coinBlock = new StringBuilder();
            Integer currentId = null;

            while ((line = br.readLine()) != null) {
                // Check for new coin block
                if (line.startsWith("ID:")) {
                    // If we already have a block, check ID and maybe print
                    if (currentId != null) {
                        if (currentId == coinId) {
                            System.out.println("Coin Information:\n" + coinBlock);
                            found = true;
                            break;
                        }
                    }
                    // Start new block
                    coinBlock.setLength(0);
                    coinBlock.append(line).append("\n");
                    try {
                        currentId = Integer.parseInt(line.split(":")[1].trim());
                    } catch (NumberFormatException e) {
                        currentId = null;
                    }
                } else {
                    // Collect coin info lines
                    coinBlock.append(line).append("\n");
                }
            }

            // Check last block
            if (!found && currentId != null && currentId == coinId) {
                System.out.println("Coin Information:\n" + coinBlock);
                found = true;
            }

            if (!found) {
                System.out.println("Coin not found with ID: " + coinId);
            }

        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }





    static class Coin {
        int id;
        String name;
        double price;

        public Coin(int id, String name, double price) {
            this.id = id;
            this.name = name;
            this.price = price;
        }
    }
    static class CoinStack {
        private Coin[] stack;
        private int top;

        public CoinStack(int capacity) {
            stack = new Coin[capacity];
            top = -1;
        }

        public void push(Coin coin) {
            if (top == stack.length - 1) {
                System.out.println("Stack Overflow");
                return;
            }
            stack[++top] = coin;
        }

        public Coin pop() {
            if (top == -1) {
                System.out.println("Stack Underflow");
                return null;
            }
            return stack[top--];
        }

        public boolean isEmpty() {
            return top == -1;
        }
    }
    static void top5CoinsByPrice(Connection con) {
        try {
            List<Coin> coins = new ArrayList<>();

            // Fetch all coins from DB
            PreparedStatement ps = con.prepareStatement("SELECT coin_id, coin_name, price FROM coins");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("coin_id");
                String name = rs.getString("coin_name");
                double price = rs.getDouble("price");

                coins.add(new Coin(id, name, price));
            }

            // Sort manually in descending order (Selection Sort)
            for (int i = 0; i < coins.size(); i++) {
                int maxIndex = i;
                for (int j = i + 1; j < coins.size(); j++) {
                    if (coins.get(j).price > coins.get(maxIndex).price) {
                        maxIndex = j;
                    }
                }
                // Swap
                Coin temp = coins.get(i);
                coins.set(i, coins.get(maxIndex));
                coins.set(maxIndex, temp);
            }

            // Push top 5 into stack
            CoinStack stack = new CoinStack(5);
            for (int i = 0; i < Math.min(5, coins.size()); i++) {
                stack.push(coins.get(i));
            }

            // Display Top 5 by popping from stack (will reverse order)
            System.out.println("\n=== Top 5 Coins by Price (Using Custom Stack) ===");
            int rank = 5;
            Coin[] result = new Coin[5];
            int i = 0;
            while (!stack.isEmpty()) {
                result[i++] = stack.pop(); // store in array to print in correct order
            }

            for (int j = result.length - 1; j >= 0; j--) {
                if (result[j] != null) {
                    System.out.println((5 - j) + ". " + result[j].name + " (ID: " + result[j].id + ") - ₹" + result[j].price);
                }
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }






    static class TransactionNode {
        String data;
        TransactionNode next;

        TransactionNode(String data) {
            this.data = data;
            this.next = null;
        }
    }

    static class TransactionLinkedList {
        TransactionNode head;

        public void addToHead(String data) {
            TransactionNode newNode = new TransactionNode(data);
            newNode.next = head;
            head = newNode;
        }

        public void printAll() {
            if (head == null) {
                System.out.println("No transaction history found.");
                return;
            }

            System.out.println("\n=== Transaction History (Latest First) ===");
            TransactionNode current = head;
            while (current != null) {
                System.out.println(current.data);
                current = current.next;
            }
        }
    }
    static void fetchTransactionHistoryFromLog(int userId) {
        String fileName = "UserID_" + userId + "_transaction_history.txt";
        File file = new File(fileName);

        if (!file.exists()) {
            System.out.println("No transaction log file found for user ID " + userId);
            return;
        }

        TransactionLinkedList historyList = new TransactionLinkedList();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = br.readLine()) != null) {
                historyList.addToHead(line);  // Insert latest at the head
            }

            historyList.printAll();

        } catch (IOException e) {
            System.out.println("Error reading transaction log: " + e.getMessage());
        }
    }





    static class avlCoin {
        int id;
        String name;
        double price;

        public avlCoin(int id, String name, double price) {
            this.id = id;
            this.name = name;
            this.price = price;
        }
    }

    static class AVLNode {
        avlCoin coin;
        AVLNode left, right;
        int height;

        AVLNode(avlCoin coin) {
            this.coin = coin;
            this.height = 1;
        }
    }

    static class AVLTree {
        AVLNode root;

        int height(AVLNode node) {
            return node == null ? 0 : node.height;
        }

        int getBalance(AVLNode node) {
            return node == null ? 0 : height(node.left) - height(node.right);
        }

        AVLNode rightRotate(AVLNode y) {
            AVLNode x = y.left;
            AVLNode T2 = x.right;

            x.right = y;
            y.left = T2;

            y.height = Math.max(height(y.left), height(y.right)) + 1;
            x.height = Math.max(height(x.left), height(x.right)) + 1;

            return x;
        }

        AVLNode leftRotate(AVLNode x) {
            AVLNode y = x.right;
            AVLNode T2 = y.left;

            y.left = x;
            x.right = T2;

            x.height = Math.max(height(x.left), height(x.right)) + 1;
            y.height = Math.max(height(y.left), height(y.right)) + 1;

            return y;
        }

        AVLNode insert(AVLNode node, avlCoin coin) {
            if (node == null)
                return new AVLNode(coin);

            if (coin.price < node.coin.price)
                node.left = insert(node.left, coin);
            else
                node.right = insert(node.right, coin);

            node.height = 1 + Math.max(height(node.left), height(node.right));
            int balance = getBalance(node);

            // Perform rotations
            if (balance > 1 && coin.price < node.left.coin.price)
                return rightRotate(node);

            if (balance < -1 && coin.price >= node.right.coin.price)
                return leftRotate(node);

            if (balance > 1 && coin.price >= node.left.coin.price) {
                node.left = leftRotate(node.left);
                return rightRotate(node);
            }

            if (balance < -1 && coin.price < node.right.coin.price) {
                node.right = rightRotate(node.right);
                return leftRotate(node);
            }

            return node;
        }

        void insert(avlCoin coin) {
            root = insert(root, coin);
        }

        void printDescending() {
            System.out.println("\n=== Coin List (High to Low Price using AVL Tree) ===");
            printDescending(root);
        }

        void printDescending(AVLNode node) {
            if (node == null) return;

            printDescending(node.right); // Visit higher price first
            System.out.println("ID: " + node.coin.id + ", Name: " + node.coin.name + ", Price: ₹" + node.coin.price);
            printDescending(node.left);
        }
    }
    static void displayCoinsUsingAVL(Connection con) {
        try {
            PreparedStatement ps = con.prepareStatement("SELECT coin_id, coin_name, price FROM coins");
            ResultSet rs = ps.executeQuery();

            AVLTree tree = new AVLTree();

            while (rs.next()) {
                int id = rs.getInt("coin_id");
                String name = rs.getString("coin_name");
                double price = rs.getDouble("price");

                avlCoin coin = new avlCoin(id, name, price);
                tree.insert(coin);
            }

            tree.printDescending();

        } catch (SQLException e) {
            System.out.println("Error fetching coins for AVL tree: " + e.getMessage());
        }
    }

    //Regex for email validation
    private static final String EMAIL_REGEX =
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

    private static boolean isValidEmail(String email) {
        return Pattern.matches(EMAIL_REGEX, email);
    }

    public static void sendEmail(String rmail,String sub,String msg) {
        Scanner sc = new Scanner(System.in);

        String fromUser ="gajerayash999@gmail.com";

        String fromUserPassword ="bxsh tnfc dawg jotg";

        String toEmail = rmail;

        String subject = sub;

        String body = msg;

        //Validate emails
        if (!isValidEmail(toEmail)) {
            System.out.println("Invalid receiver email: " + toEmail);
            return;
        }

        String host = "smtp.gmail.com";

        // Setup mail server properties
        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        // Create session with authentication
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromUser, fromUserPassword);
            }
        });

        try {
            // Create message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromUser));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject(subject);
            message.setText(body);

            // Send message
            Transport.send(message);

            System.out.println("Email sent to " + toEmail);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}