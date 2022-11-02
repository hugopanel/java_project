package gesto.api.tables;

import gesto.api.Database;
import gesto.api.types.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Orders {
    private Database db;

    public Orders(Database db) { this.db = db; }

    public Order getOrder(int ID) throws SQLException {
        ResultSet rset = db.executeQuery("SELECT * FROM Orders WHERE ID=%d".formatted(ID));
        rset.next();
        return new Order(rset.getInt("ID"), OrderStatus.values()[rset.getInt("Status")],
                db.Tables.getTable(rset.getString("Num")));
    }

    public List<Order> getOrders() throws SQLException {
        ResultSet rset = db.executeQuery("SELECT * FROM Orders");
        List<Order> orderList = new ArrayList<>();

        while (rset.next()) {
            orderList.add(
                    new Order(
                            rset.getInt(1),
                            OrderStatus.values()[rset.getInt(2)],
                            db.Tables.getTable(rset.getString(3))
                    )
            );
        }

        return orderList;
    }

    @Deprecated
    public void insertOrder(Order Order) throws SQLException {
        db.execute(
                "INSERT INTO Orders (ID, Status, Num) VALUES (%d, %d, '%s')"
                        .formatted(Order.getID(), Order.getStatus().getValue(), Order.getTable().getNum())
        );
    }

    public void insertOrder(OrderStatus Status, Table Table, List<MenuItem> MenuItems) throws SQLException {
        db.execute(
                "INSERT INTO Orders (Status, Num) VALUES (%d, '%s')"
                        .formatted(Status.getValue(), Table.getNum())
        );
        ResultSet rset = db.executeQuery("SELECT * FROM Orders ORDER BY ID DESC");
        if (rset.next())
        {
            List<MenuItemWithQuantity> menuItemWithQuantityList = new ArrayList<>();

            for (MenuItem item: MenuItems)
            {
                int frequency = Collections.frequency(MenuItems, item);

                if (!menuItemWithQuantityList.contains(new MenuItemWithQuantity(item, frequency)))
                    menuItemWithQuantityList.add(new MenuItemWithQuantity(item, frequency));
            }

            for (MenuItemWithQuantity item: menuItemWithQuantityList) {
                db.execute("INSERT INTO Order_Items (OrderID, ItemID, Quantity) VALUES (%d, %d, %d)"
                        .formatted(rset.getInt("ID"), item.getMenuItem().getID(), item.getQuantity()));
            }
        }
    }

    @Deprecated
    public void updateOrder(int oldID, Order newOrder) throws SQLException {
        db.execute(
                "UPDATE FROM Orders SET Status=%d, Num='%s' WHERE ID=%d"
                        .formatted(newOrder.getStatus().getValue(), newOrder.getTable().getNum(), oldID)
        );
    }

    @Deprecated
    public void deleteTable(int ID) throws SQLException {
        db.execute(
                "DELETE FROM Orders WHERE ID=%d".formatted(ID)
        );
    }
}
