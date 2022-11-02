package gesto.api.tables;

import gesto.api.Database;
import gesto.api.types.Check;
import gesto.api.types.Order;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class Checks {
    private Database db;

    public Checks(Database db) {
        this.db = db;
    }

    public Check getCheck(int ID) throws SQLException {
        ResultSet rset = db.executeQuery("SELECT * FROM Checks WHERE ID=%d".formatted(ID));
        rset.next();
        return new Check(rset.getInt(0), rset.getDouble(1), rset.getTimestamp(2),
                db.Orders.getOrder(rset.getInt(3)));
    }

    public Check getCheck(double Amount, Timestamp PaymentDate, int OrderID) throws SQLException {
        ResultSet rset = db.executeQuery("SELECT * FROM Checks WHERE Amount=%s AND PaymentDate=%s AND OrderID=%d"
                .formatted(Double.toString(Amount), PaymentDate, OrderID));
        rset.next();
        return new Check(
                rset.getInt(0),
                rset.getDouble(1),
                rset.getTimestamp(2),
                db.Orders.getOrder(rset.getInt(3))
        );
    }

    public List<Check> getChecks() throws SQLException {
        ResultSet rset = db.executeQuery("SELECT * FROM Checks");
        List<Check> checkList = new ArrayList<>();

        while (rset.next()) {
            checkList.add(
                    new Check(
                            rset.getInt(1),
                            rset.getDouble(2),
                            rset.getTimestamp(3),
                            db.Orders.getOrder(rset.getInt(4))
                    )
            );
        }

        return checkList;
    }

    public void insertCheck(double Amount, Timestamp PaymentDate, Order Order) throws SQLException {
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        db.execute("INSERT INTO Checks (Amount, PaymentDate, OrderID) VALUES (%s, '%s', %d)"
                .formatted(Double.toString(Amount), PaymentDate, Order.getID()));
    }

    public void updateCheck(int oldID, Check newCheck) throws SQLException {
        db.execute("UPDATE Checks SET Amount=%s, PaymentDate=%s, OrderID=%d WHERE ID=%d"
                .formatted(Double.toString(newCheck.getAmount()), newCheck.getPaymentDate(), newCheck.getOrder().getID(), oldID));
    }

    public void deleteCheck(int ID) throws SQLException {
        db.execute("DELETE FROM Checks WHERE ID=%d".formatted(ID));
    }
}
