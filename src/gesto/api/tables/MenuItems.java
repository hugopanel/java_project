package gesto.api.tables;

import gesto.api.Database;
import gesto.api.types.MenuItem;
import gesto.api.types.MenuItemCategory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MenuItems {
    private Database db;

    public MenuItems(Database db) { this.db = db; }

    public MenuItem getMenuItem(int ID) throws SQLException {
        ResultSet rset = db.executeQuery("SELECT * FROM Menu_Items WHERE ID=%d".formatted(ID));
        rset.next();
        return new MenuItem(ID, rset.getString("Name"), MenuItemCategory.values()[rset.getInt("Category")], rset.getBoolean("Available"), rset.getDouble("Price"));
    }

    public List<MenuItem> getMenuItems() throws SQLException {
        ResultSet rset = db.executeQuery("SELECT * FROM Menu_Items");
        List<MenuItem> menuItemList = new ArrayList<>();

        while (rset.next()) {
            menuItemList.add(
                    new MenuItem(
                            rset.getInt("ID"),
                            rset.getString("Name"),
                            MenuItemCategory.values()[rset.getInt("Category")],
                            rset.getBoolean("Available"),
                            rset.getDouble("Price")
                    )
            );
        }

        return menuItemList;
    }

    public void insertMenuItem(String Name, MenuItemCategory Category, boolean Available, double Price) throws SQLException {
        db.execute(
                "INSERT INTO Menu_Items (Name, Category, Available, Price) VALUES ('%s', %d, %b, %s)"
                        .formatted(Name, Category.getValue(), Available, Double.toString(Price))
        );
    }

    public void updateMenuItem(int oldID, MenuItem newMenuItem) throws SQLException {
        db.execute(
                "UPDATE Menu_Items SET Name='%s', Category=%d, Available=%b, Price=%s WHERE ID=%d"
                        .formatted(newMenuItem.getName(), newMenuItem.getCategory().getValue(), newMenuItem.getAvailable(), Double.toString(newMenuItem.getPrice()), oldID)
        );
    }

    public void deleteMenuItem(int ID) throws SQLException {
        db.execute(
                "DELETE FROM Menu_Items WHERE ID=%d"
                        .formatted(ID)
        );
    }
}
