package db.connections.datasource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MariaDbConnection {

    private static Connection conn = null;

    static {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("MariaDB JDBC driver not found.");
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        if (conn == null) {
            // connect if necessary
            try {
                conn = DriverManager.getConnection(
                        "jdbc:mariadb://localhost:3306/market?user=appuser9&password=password");
            } catch (SQLException e) {
                System.out.println("Connection failed.");
                e.printStackTrace();
            }
        }
        return conn;
    }

    public static void terminate() {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
