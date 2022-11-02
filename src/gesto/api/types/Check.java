package gesto.api.types;

import java.sql.Timestamp;

public class Check {
    private int ID;
    private double Amount;
    private Timestamp PaymentDate;
    private Order Order;

    public Check(int ID, double Amount, Timestamp PaymentDate, Order Order) {
        this.ID = ID;
        this.Amount = Amount;
        this.PaymentDate = PaymentDate;
        this.Order = Order;
    }

    public void setID(int ID) { this.ID = ID; }
    public void setAmount(double Amount) { this.Amount = Amount; }
    public void setPaymentDate(Timestamp PaymentDate) { this.PaymentDate = PaymentDate; }
    public void setOrder(Order Order) { this.Order = Order; }

    public int getID() { return this.ID; }
    public double getAmount() { return this.Amount; }
    public Timestamp getPaymentDate() { return this.PaymentDate; }
    public Order getOrder() { return this.Order; }
}
