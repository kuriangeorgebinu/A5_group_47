package edu.gatech.cs6310.model;

public class Item {

    private String itemName;

    private double weight;

    public Item(String itemName, double weight){
        this.itemName = itemName;
        this.weight = weight;
    }

    //Getter
    public String getItemName() {
        return itemName;
    }

    public double getWeight() {
        return weight;
    }

    //Setter
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}
