package db.connections.datasource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * This class manages the connection to the MariaDB database.
 */
public class MariaDbConnection {

    private static Connection conn = null;

    static {
        try {
            // Load MariaDB JDBC driver
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("MariaDB JDBC driver not found.");
            e.printStackTrace();
        }
    }

    /**
     * Returns the connection to the database. If the connection does not exist, it creates a new one.
     *
     * @return The connection to the database.
     */
    public static Connection getConnection() {
        if (conn == null) {
            // connect if necessary
            try {
                conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/market?user=appuser9&password=password");
            } catch (SQLException e) {
                System.out.println("Connection failed.");
                e.printStackTrace();
            }
        }
        return conn;
    }

    /**
     * Terminates the connection to the database if it exists.
     */
    public static void terminate() {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /**
     * Sets the connection to the database. This method is primarily intended for testing purposes.
     *
     * @param conn The connection to the database.
     */
    public static void setConnection(Connection conn) {
        MariaDbConnection.conn = conn;
    }
}
