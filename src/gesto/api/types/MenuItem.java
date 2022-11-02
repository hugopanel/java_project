package gesto.api.types;

public class MenuItem  {
    private int ID;
    private String Name;
    private MenuItemCategory Category;
    private boolean Available;
    private double Price;

    public MenuItem(int ID, String Name, MenuItemCategory Category, boolean Available, double Price) {
        this.ID = ID;
        this.Name = Name;
        this.Category = Category;
        this.Available = Available;
        this.Price = Price;
    }

    public void setID(int ID) { this.ID = ID; }
    public void setName(String Name) { this.Name = Name; }
    public void setCategory(MenuItemCategory Category) { this.Category = Category; }
    public void setAvailable(boolean Available) { this.Available = Available; }
    public void setPrice(double Price) { this.Price = Price; }

    public int getID() { return this.ID; }
    public String getName() { return this.Name; }
    public MenuItemCategory getCategory() { return this.Category; }
    public boolean getAvailable() { return this.Available; }
    public double getPrice() { return this.Price; }
}
