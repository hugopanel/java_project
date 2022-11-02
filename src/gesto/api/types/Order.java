package gesto.api.types;

import gesto.api.Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private int ID;
    private OrderStatus Status;
    private Table Table;

    public Order(int ID, OrderStatus Status, Table Table) {
        this.ID = ID;
        this.Status = Status;
        this.Table = Table;
    }

    public void setID(int ID) { this.ID = ID; }
    public void setStatus(OrderStatus Status) { this.Status = Status; }
    public void setTable(Table Table) { this.Table = Table; }

    public int getID() { return this.ID;}
    public OrderStatus getStatus() { return this.Status; }
    public Table getTable() { return this.Table; }

    public double getTotalPrice(Database db) throws SQLException {
        List<MenuItem> menuItems = getMenuItems(db);
        double totalPrice = 0;
        for (MenuItem menuItem: menuItems) {
            totalPrice += menuItem.getPrice();
        }

        return totalPrice;
    }

    public List<MenuItem> getMenuItems(Database db) throws SQLException {
        ResultSet rset = db.executeQuery("SELECT * FROM Order_Items WHERE OrderID=%d".formatted(this.ID));
        List<MenuItem> itemList = new ArrayList<>();

        while(rset.next()) {
            itemList.add(db.MenuItems.getMenuItem(rset.getInt("ItemID")));
        }

        return itemList;
    }

    public Check generateCheck(Database db) throws SQLException {
        double totalPrice = getTotalPrice(db);
        Timestamp timestamp = new java.sql.Timestamp(new java.util.Date().getTime());
        db.Checks.insertCheck(
                totalPrice,
                timestamp,
                this
        );

        return db.Checks.getCheck(totalPrice, timestamp, this.ID);
    }

    public Check getCheck(Database db) throws SQLException {
        ResultSet rset = db.executeQuery("SELECT * FROM Checks WHERE OrderID=%d".formatted(this.ID));
        rset.next();
        return new Check(
                rset.getInt(0),
                rset.getDouble(1),
                rset.getTimestamp(2),
                this
        );
    }
}
