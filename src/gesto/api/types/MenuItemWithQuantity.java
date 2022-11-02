package gesto.api.types;

public class MenuItemWithQuantity {
    private MenuItem MenuItem;
    private int Quantity;

    public MenuItemWithQuantity(MenuItem MenuItem, int Quantity) {
        this.MenuItem = MenuItem;
        this.Quantity = Quantity;
    }

    public MenuItem getMenuItem() {
        return this.MenuItem;
    }

    public int getQuantity() {
        return this.Quantity;
    }

    public void setQuantity(int Quantity) {
        this.Quantity = Quantity;
    }
}
