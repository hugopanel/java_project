package gesto.api.types;

public enum MenuItemCategory {
    APPETIZERS(0),
    MAINS(1),
    DESSERTS(2),
    DRINKS(3),
    OTHER(4);

    private final int value;
    MenuItemCategory(int value) { this.value = value; }
    public int getValue() { return this.value; }
}
