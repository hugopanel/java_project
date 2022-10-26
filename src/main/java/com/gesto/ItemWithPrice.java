package com.gesto;

import gesto.api.types.MenuItem;

public class ItemWithPrice {
    private gesto.api.types.MenuItem MenuItem;
    private double ItemPrice;

    public ItemWithPrice(MenuItem MenuItem, double price) {
        this.MenuItem = MenuItem;
        ItemPrice = price;
    }

    public String getItemName() { return MenuItem.getName(); }
    public double getItemPrice() { return ItemPrice; }
    public MenuItem getMenuItem() { return MenuItem; }
}
