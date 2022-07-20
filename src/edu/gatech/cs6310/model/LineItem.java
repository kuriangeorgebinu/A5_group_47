package edu.gatech.cs6310.model;

public class LineItem {

    private Item item;

    private int quantity;

    private double unitPrice;

    public LineItem() {
        quantity = 0;
        unitPrice = 0;
    }

    public LineItem(Item item, int quantity, double unitPrice) {
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.item =item;
    }

    //Getter
    public double getUnitPrice() {
        return unitPrice;
    }

    public Item getItem() {
        return item;
    }

    public int getQuantity() {
        return quantity;
    }
    //Setter
    public void setItem(Item item) {
        this.item = item;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

}
