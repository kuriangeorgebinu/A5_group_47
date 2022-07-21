package model.users;


import model.Location;
import model.Order;

import java.util.ArrayList;
import java.util.List;

public class Customer extends Users {

    private String rating;
    private double credit;

    //associations
    private List<Order> orderList;
    private Location locationProperty;

    public Customer(String account, String firstName, String lastName, String phoneNumber, String rating, double credit) {
        super(account, firstName, lastName, phoneNumber);
        this.rating = rating;
        this.credit = credit;

    }

    //Getter
    public String getRating() {
        return rating;
    }

    public double getCredit() {
        return credit;
    }

    public List<Order> getOrderList() {
        return orderList;
    }

    //Setter
    public void setRating(String rating) {
        this.rating = rating;
    }

    public void setCredit(double credit) {
        this.credit = credit;
    }

    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
    }

    public boolean addNewOrder(Order order) {
        if (this.getOrderList() == null) {
            this.setOrderList(new ArrayList<>());
        }
        List<String> orderNames = getOrderList().stream().map(Order::getOrderIdentifier).toList();
        if (orderNames.contains(order.getOrderIdentifier())) {
            this.getOrderList().add(order);
            return true;
        }
        return false;
    }


    public void deleteOrder(Order order) {
        if (!(this.getOrderList() == null) && !this.getOrderList().isEmpty()) {
            List<String> orderNames = getOrderList().stream().map(Order::getOrderIdentifier).toList();
            if (orderNames.contains(order.getOrderIdentifier())) {
                getOrderList().remove(order);
            }
        }
    }

    public void setLocationProperty(Location locationProperty) {
        this.locationProperty = locationProperty;
    }

    public Location getLocationProperty() {
        return this.locationProperty;
    }


}
