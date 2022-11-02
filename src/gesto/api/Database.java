package gesto.api;

import gesto.api.tables.*;

import java.sql.*;

public class Database {
    String databaseUrl;
    Statement stmt;
    Connection connection;

    public Tables Tables;
    public Orders Orders;
    public MenuItems MenuItems;
    public Checks Checks;

    public Database(String databaseUrl) {
        this.databaseUrl = databaseUrl;
    }

    public void open() throws Exception {
        // Load the driver
        Class.forName("net.ucanaccess.jdbc.UcanaccessDriver").newInstance();

        // Connect to the database
        connection = DriverManager.getConnection(databaseUrl);
        stmt = connection.createStatement();

        // Initialize the tables
        Tables = new Tables(this);
        Orders = new Orders(this);
        MenuItems = new MenuItems(this);
        Checks = new Checks(this);
    }

    public void close() {
        try {
            stmt.close();
            connection.close();
        } catch (Exception ex) {
            // Cannot close the statement and connection, they are probably not opened yet.
        }
    }

    public void execute(String query) throws SQLException {
        stmt.execute(query);
    }

    public ResultSet executeQuery(String query) throws SQLException {
        return stmt.executeQuery(query);
    }
}
