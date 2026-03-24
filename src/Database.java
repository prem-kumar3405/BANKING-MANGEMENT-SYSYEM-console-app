import java.sql.*;

public class Database
{
    private static final String URL = "jdbc:mysql://localhost:3306/bankdb";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Prem@3405";

    public static Connection getConnection()
    {
        Connection con = null;
        try {
            con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (Exception e)
          {
            System.out.println("Database connection failed: " + e.getMessage());
          }
        return con;
    }
}
