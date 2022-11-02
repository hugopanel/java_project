package gesto.api.types;

public class OrderItems {
    private Order Order;
    private MenuItem MenuItem;

    public OrderItems(Order Order, MenuItem MenuItem) {
        this.Order = Order;
        this.MenuItem = MenuItem;
    }

    public void setOrder(Order Order) { this.Order = Order; }
    public void setMenuItem(MenuItem MenuItem) { this.MenuItem = MenuItem; }

    public Order getOrder() { return this.Order; }
    public MenuItem getMenuItem() { return this.MenuItem; }
}
