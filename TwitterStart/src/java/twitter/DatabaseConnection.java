
package twitter;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;

public class DatabaseConnection {
    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        //force java to load driver
            Class.forName("com.mysql.jdbc.Driver");
            String dbURL = "jdbc:mysql://localhost:3306/twitter";
            // driver: // url:port / database
            String username = "twitter-summer2024";
            String password = "test";
            Connection connection = DriverManager.getConnection(dbURL, username, password);
            
            return connection;
    }
}
