import java.sql.*;
import java.util.Scanner;

public class LoginService {
    private Connection con = Database.getConnection();
    private Scanner sc = new Scanner(System.in);
    private final int KEY = 3; // encryption key

    // -------------------- ADMIN LOGIN --------------------
    public boolean adminLogin() {
        try {
            System.out.print("Enter Admin Username: ");
            String username = sc.nextLine();

            System.out.print("Enter Admin Password: ");
            String password = sc.nextLine();

            // Encrypt input password
            String encryptedPassword = CaesarCipher.encrypt(password, KEY);

            PreparedStatement ps = con.prepareStatement(
                "SELECT * FROM admin WHERE username=? AND password=?"
            );
            ps.setString(1, username);
            ps.setString(2, encryptedPassword);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                System.out.println("Admin Login Successful.");
                return true;
            } else {
                System.out.println("Invalid Admin Credentials.");
                return false;
            }

        } catch (Exception e) {
            System.out.println("Error during admin login: " + e.getMessage());
            return false;
        }
    }

    // -------------------- USER LOGIN --------------------
    public int userLogin() {
        try {
            System.out.print("Enter Account Number: ");
            int acc = sc.nextInt();
            sc.nextLine();

            System.out.print("Enter Password: ");
            String pass = sc.nextLine();

            // Encrypt input password
            String encryptedPass = CaesarCipher.encrypt(pass, KEY);

            PreparedStatement ps = con.prepareStatement(
                "SELECT * FROM users WHERE acc_no=? AND password=?"
            );
            ps.setInt(1, acc);
            ps.setString(2, encryptedPass);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                System.out.println("User Login Successful.");
                return acc;
            } else {
                System.out.println("Invalid User Credentials.");
                return -1;
            }

        } catch (Exception e) {
            System.out.println("Error during user login: " + e.getMessage());
            return -1;
        }
    }
}