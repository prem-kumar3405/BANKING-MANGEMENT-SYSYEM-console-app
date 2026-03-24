import java.sql.*;
import java.util.Scanner;

public class BankOperations {
    private Connection con;
    private Scanner sc = new Scanner(System.in);
    private final int KEY = 3;

    public BankOperations() {
        con = Database.getConnection();
    }

    // 1. Add New User
    public void addUser() {
    try {
        System.out.print("Enter Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Password: ");
        String password = sc.nextLine();

        // Encrypt password
        String encryptedPassword = CaesarCipher.encrypt(password, KEY);

        PreparedStatement ps = con.prepareStatement(
                "INSERT INTO users(name, password, balance) VALUES (?, ?, ?)"
        );
        ps.setString(1, name);
        ps.setString(2, encryptedPassword);
        ps.setDouble(3, 0.0);

        ps.executeUpdate();

        System.out.println("User added successfully.");

    } catch (Exception e) {
        System.out.println("Error adding user: " + e.getMessage());
    }
   }

    // 2. View All Users
    public void viewUsers()
    {
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM users");

            System.out.println("\n--- All Users ---");
            while (rs.next())
            {
                System.out.println(
                        "Acc No: " + rs.getInt("acc_no") +
                                " | Name: " + rs.getString("name") +
                                " | Balance: " + rs.getDouble("balance")
                );
            }
        } catch (Exception e) {
            System.out.println("Error fetching users: " + e.getMessage());
        }
    }

    // 3. Debit Money
    public void debitMoney() {
        try {
            System.out.print("Enter Account No: ");
            int acc = sc.nextInt();
            System.out.print("Enter Amount to Debit: ");
            double amount = sc.nextDouble();

            PreparedStatement ps = con.prepareStatement(
                    "UPDATE users SET balance = balance - ? WHERE acc_no = ?"
            );
            ps.setDouble(1, amount);
            ps.setInt(2, acc);
            int updated = ps.executeUpdate();

            if (updated > 0) {
                logTransaction(acc, "DEBIT", amount);
                System.out.println("Money debited successfully.");
            } else {
                System.out.println("Account not found.");
            }
        } catch (Exception e) {
            System.out.println("Error debiting money: " + e.getMessage());
        }
    }

    // 4. Credit Money
    public void creditMoney() {
        try {
            System.out.print("Enter Account No: ");
            int acc = sc.nextInt();
            System.out.print("Enter Amount to Credit: ");
            double amount = sc.nextDouble();

            PreparedStatement ps = con.prepareStatement(
                    "UPDATE users SET balance = balance + ? WHERE acc_no = ?"
            );
            ps.setDouble(1, amount);
            ps.setInt(2, acc);
            int updated = ps.executeUpdate();

            if (updated > 0) {
                logTransaction(acc, "CREDIT", amount);
                System.out.println("Money credited successfully.");
            } else {
                System.out.println("Account not found.");
            }
        } catch (Exception e) {
            System.out.println("Error crediting money: " + e.getMessage());
        }
    }

    // 5. Account Transfer
    public void transferMoney() {
        try {
            System.out.print("Enter Sender Account No: ");
            int sender = sc.nextInt();
            System.out.print("Enter Receiver Account No: ");
            int receiver = sc.nextInt();
            System.out.print("Enter Amount: ");
            double amount = sc.nextDouble();

            con.setAutoCommit(false);

            PreparedStatement debit = con.prepareStatement(
                    "UPDATE users SET balance = balance - ? WHERE acc_no = ?"
            );
            debit.setDouble(1, amount);
            debit.setInt(2, sender);

            PreparedStatement credit = con.prepareStatement(
                    "UPDATE users SET balance = balance + ? WHERE acc_no = ?"
            );
            credit.setDouble(1, amount);
            credit.setInt(2, receiver);

            int d = debit.executeUpdate();
            int c = credit.executeUpdate();

            if (d > 0 && c > 0) {
                logTransaction(sender, "TRANSFER_OUT", amount);
                logTransaction(receiver, "TRANSFER_IN", amount);
                con.commit();
                System.out.println("Transfer successful.");
            } else {
                con.rollback();
                System.out.println("Transfer failed.");
            }

            con.setAutoCommit(true);
        } catch (Exception e) {
            System.out.println("Error in transfer: " + e.getMessage());
        }
    }

    // 6. Change Password
    public void changePassword() {
    try {
        System.out.print("Enter Account No: ");
        int acc = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter New Password: ");
        String newPass = sc.nextLine();

        // Encrypt password
        String encryptedPass = CaesarCipher.encrypt(newPass, KEY);

        PreparedStatement ps = con.prepareStatement(
                "UPDATE users SET password = ? WHERE acc_no = ?"
        );
        ps.setString(1, encryptedPass);
        ps.setInt(2, acc);

        ps.executeUpdate();

        System.out.println("Password updated successfully.");

    } catch (Exception e) {
        System.out.println("Error changing password: " + e.getMessage());
    }
}

    // 7. Check Balance
    public void checkBalance() {
        try {
            System.out.print("Enter Account No: ");
            int acc = sc.nextInt();

            PreparedStatement ps = con.prepareStatement(
                    "SELECT balance FROM users WHERE acc_no = ?"
            );
            ps.setInt(1, acc);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                System.out.println("Current Balance: " + rs.getDouble("balance"));
            } else {
                System.out.println("Account not found.");
            }
        } catch (Exception e) {
            System.out.println("Error checking balance: " + e.getMessage());
        }
    }

    // 8. Transaction History
    public void transactionHistory() {
        try {
            System.out.print("Enter Account No: ");
            int acc = sc.nextInt();

            PreparedStatement ps = con.prepareStatement(
                    "SELECT * FROM transactions WHERE acc_no = ?"
            );
            ps.setInt(1, acc);
            ResultSet rs = ps.executeQuery();

            System.out.println("\n--- Transaction History ---");
            while (rs.next()) {
                System.out.println(
                        rs.getTimestamp("date") + " | " +
                                rs.getString("type") + " | " +
                                rs.getDouble("amount")
                );
            }
        } catch (Exception e) {
            System.out.println("Error fetching history: " + e.getMessage());
        }
    }

    // 9. Delete User
    public void deleteUser() {
        try {
            System.out.print("Enter Account No: ");
            int acc = sc.nextInt();

            PreparedStatement ps = con.prepareStatement(
                    "DELETE FROM users WHERE acc_no = ?"
            );
            ps.setInt(1, acc);
            ps.executeUpdate();

            System.out.println("User deleted successfully.");
        } catch (Exception e) {
            System.out.println("Error deleting user: " + e.getMessage());
        }
    }

    // Helper Method: Log Transaction
    private void logTransaction(int accNo, String type, double amount) throws SQLException {
        PreparedStatement ps = con.prepareStatement(
                "INSERT INTO transactions(acc_no, type, amount) VALUES (?, ?, ?)"
        );
        ps.setInt(1, accNo);
        ps.setString(2, type);
        ps.setDouble(3, amount);
        ps.executeUpdate();
    }
}
