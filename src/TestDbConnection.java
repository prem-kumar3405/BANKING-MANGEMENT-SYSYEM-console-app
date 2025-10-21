import java.sql.Connection;

public class TestDbConnection {
    public static void main(String[] args) {
        Connection con=Database.getConnection();
        if(con!=null)
        {
            System.out.println("Connection sucessful");
        }
        else {
            System.out.println("Connection failed");
        }
    }
}
