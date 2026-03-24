import java.util.Scanner;

public class Main {
    public static void main(String[] args)
    {
        Scanner scan = new Scanner(System.in);
        LoginService loginService = new LoginService();
        System.out.println("---- Banking Management System ----");
        System.out.println("1. Admin Login");
        System.out.println("2. User Login");
        System.out.print("Choose Login Type: ");
        int loginChoice = scan.nextInt();
        scan.nextLine();

        if (loginChoice == 1) {
            if (loginService.adminLogin()) {
                adminMenu();
            }
        } else if (loginChoice == 2) {
            int acc = loginService.userLogin();
            if (acc != -1) {
                userMenu(acc);
            }
        } else {
            System.out.println("Invalid choice! Exiting...");
        }
    }

    // -------------------- ADMIN MENU --------------------
    private static void adminMenu() {
        Scanner sc = new Scanner(System.in);
        BankOperations bank = new BankOperations();

        while (true) {
            System.out.println("\n--- Admin Menu ---");
            System.out.println("1. Add User");
            System.out.println("2. View All Users");
            System.out.println("3. Delete User");
            System.out.println("4. View All Transactions");
            System.out.println("5. Logout");
            System.out.print("Enter Choice: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1 -> bank.addUser();
                case 2 -> bank.viewUsers();
                case 3 -> bank.deleteUser();
                case 4 -> viewAllTransactions();
                case 5 -> {
                    System.out.println("Logout Successful.");
                    return;
                }
                default -> System.out.println("Invalid choice! Please try again.");
            }
        }
    }

    // -------------------- USER MENU --------------------
    private static void userMenu(int accNo) {
        Scanner sc = new Scanner(System.in);
        BankOperations bank = new BankOperations();

        while (true) {
            System.out.println("\n--- User Menu ---");
            System.out.println("1. Debit Money");
            System.out.println("2. Credit Money");
            System.out.println("3. Transfer Money");
            System.out.println("4. Change Password");
            System.out.println("5. Check Balance");
            System.out.println("6. Transaction History");
            System.out.println("7. Logout");
            System.out.print("Enter Choice: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1 -> bank.debitMoney();
                case 2 -> bank.creditMoney();
                case 3 -> bank.transferMoney();
                case 4 -> bank.changePassword();
                case 5 -> bank.checkBalance();
                case 6 -> bank.transactionHistory();
                case 7 -> {
                    System.out.println("Logout Successful.");
                    return;
                }
                default -> System.out.println("Invalid choice! Please try again.");
            }
        }
    }

    // -------------------- VIEW ALL TRANSACTIONS (Admin only) --------------------
    private static void viewAllTransactions() {
        try {
            var con = Database.getConnection();
            var ps = con.prepareStatement("SELECT * FROM transactions ORDER BY date DESC");
            var rs = ps.executeQuery();

            System.out.println("\n--- All Transactions ---");
            while (rs.next()) {
                System.out.println(
                        "Account No: " + rs.getInt("acc_no") +
                                " | Type: " + rs.getString("type") +
                                " | Amount: " + rs.getDouble("amount") +
                                " | Date: " + rs.getTimestamp("date")
                );
            }
        } catch (Exception e) {
            System.out.println("Error fetching transactions: " + e.getMessage());
        }
    }
}
