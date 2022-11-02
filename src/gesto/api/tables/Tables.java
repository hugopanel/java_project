package gesto.api.tables;

import gesto.api.Database;
import gesto.api.types.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Tables {
    private Database db;

    public Tables(Database db) {
        this.db = db;
    }

    /**
     * Select a single table from the Tables table.
     * @param Num The ID of the table.
     * @return The corresponding table
     * **/
    public Table getTable(String Num) throws SQLException {
        ResultSet rset = db.executeQuery("SELECT * FROM Tables WHERE Num='" + Num + "'");
        rset.next();
        return new Table(rset.getString("Num"), rset.getBoolean("Available"));
    }

    /**
     * Returns all the tables from the Tables table.
     * @return A list containing all the tables.
     * **/
    public List<Table> getTables() throws SQLException {
        ResultSet rset = db.executeQuery("SELECT * FROM Tables");
        List<Table> tableList = new ArrayList<>();

        while (rset.next()) {
            tableList.add(
                    new Table(
                            rset.getString("Num"),
                            rset.getBoolean("Available")
                    )
            );
        }

        return tableList;
    }

    /**
     * Inserts a table into the Tables table.
     * @param table The table to insert.
     * **/
    public void insertTable(Table table) throws SQLException {
        db.execute(
                "INSERT INTO Tables (Num, Available) VALUES ('%s', '%s')"
                        .formatted(table.getNum(), table.getAvailable())
        );
    }

    /**
     * Inserts a table into the Tables table.
     * @param Num The number (ID) of the table (String of 5 characters long).
     * @param Available Whether the table is available or not (boolean).
     * **/
    public void insertTable(String Num, boolean Available) throws SQLException {
        db.execute(
                "INSERT INTO Tables (Num, Available) VALUES ('%s', '%s')"
                        .formatted(Num, Available)
        );
    }

    /**
     * Update a table with new values.
     * @param oldTable The table to update.
     * @param newTable The table to replace the old one with.
     * **/
    public void updateTable(Table oldTable, Table newTable) throws SQLException {
        db.execute(
                "UPDATE Tables SET Num='%s', Available='%s' WHERE Num='%s' AND Available='%s'"
                        .formatted(newTable.getNum(), newTable.getAvailable(),
                                oldTable.getNum(), oldTable.getAvailable())
        );
    }

    /**
     * Update a table with new values.
     * @param oldNum The number of the table to update.
     * @param newTable The table to replace the old one with.
     * **/
    public void updateTable(String oldNum, Table newTable) throws SQLException {
        db.execute(
                "UPDATE Tables SET Num='%s', Available='%s' WHERE Num='%s'"
                        .formatted(newTable.getNum(), newTable.getAvailable(), oldNum)
        );
    }

    /**
     * Removes an entry from the Tables table.
     * @param Table The table to delete.
     * **/
    public void deleteTable(Table Table) throws SQLException {
        db.execute("DELETE FROM Tables WHERE Num='%s' AND Available='%s'"
                .formatted(Table.getNum(), Table.getAvailable()));
    }

    /**
     * Removes an entry from the Tables table.
     * @param Num The number of the table to delete.
     * **/
    public void deleteTable(String Num) throws SQLException {
        db.execute("DELETE FROM Tables WHERE Num='%s'"
                .formatted(Num)
        );
    }
}
