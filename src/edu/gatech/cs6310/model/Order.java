package edu.gatech.cs6310.model;

import edu.gatech.cs6310.model.users.Customer;

import java.util.ArrayList;
import java.util.List;

public class Order {

    private String orderIdentifier;

    private double totalWeight;

    private double totalCost;

    //associations
    private Drone drone;
    private Store store;
    private Customer customer;
    private List<LineItem> lineItems;


    public Order(String orderIdentifier) {
        this.orderIdentifier = orderIdentifier;
        this.totalCost = 0;
        this.totalWeight = 0;
        lineItems = new ArrayList<>();
    }


    //Getter
    public double getTotalCost() {
        return totalCost;
    }

    public double getTotalWeight() {
        return totalWeight;
    }

    public Store getStore() {
        return store;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Drone getDrone() {
        return drone;
    }

    public String getOrderIdentifier() {
        return orderIdentifier;
    }

    public List<LineItem> getLineItems() {
        return lineItems;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public void setTotalWeight(double totalWeight) {
        this.totalWeight = totalWeight;
    }

    public void setDrone(Drone drone) {
        this.drone = drone;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setOrderIdentifier(String orderIdentifier) {
        this.orderIdentifier = orderIdentifier;
    }

    public void setLineItem(List<LineItem> lineItems) {
        this.lineItems = lineItems;
    }

    //behaviour
    public boolean addNewLineItem(LineItem lineItem) {
        if (getLineItems() == null) setLineItem(new ArrayList<>());
        List<String> itemsPresent = getLineItems().stream().map(item -> item.getItem().getItemName()).toList();
        double currentWeight = drone.getTotalPendingOrderWeights() + lineItem.getItem().getWeight()*lineItem.getQuantity();
        double currentCost = getTotalCost() + lineItem.getUnitPrice()*lineItem.getQuantity();
        if (!itemsPresent.contains(lineItem.getItem().getItemName())) {
            if (currentWeight >= drone.getLiftingCapacity()) {
                System.out.println("ERROR:drone_cant_carry_new_item");
                return false;
            } else if (currentCost > customer.getCredit()) {
                System.out.println("ERROR:customer_cant_afford_new_item");
                return false;
            }
            this.getLineItems().add(lineItem);
            calculateTotalCost();
            calculateTotalWeight();
            return true;
        }
        System.out.println("ERROR:item_already_ordered");
        return false;
    }

    public void deductCustomerCredits(double orderCost) {
        getCustomer().setCredit(getCustomer().getCredit() - orderCost);
    }

    private void calculateTotalWeight() {
        double totalWeight = getLineItems().stream()
                .map(lineItem -> lineItem.getItem().getWeight() * lineItem.getQuantity()).reduce(Double::sum)
                .stream().mapToDouble(i -> i).sum();
        setTotalWeight(totalWeight);
    }

    private void calculateTotalCost() {
        double totalCost = getLineItems().stream()
                .map(lineItem -> lineItem.getQuantity()*lineItem.getUnitPrice())
                .reduce(Double::sum).stream().mapToDouble(i -> i).sum();
        setTotalCost(totalCost);
    }
}
