package model;

import model.users.Employee;

import java.util.ArrayList;
import java.util.List;

public class Store {

    //primary fields
    private String name;

    private double revenueEarnt;

    //associations
    private List<Employee> employeeList;
    private List<Item> itemList;
    private List<Drone> droneList;
    private List<Order> orderList;
    private Location locationProperty;

    public Store(String name, double revenueEarnt) {
        this.name = name;
        this.revenueEarnt = revenueEarnt;
    }

    //Getter
    public double getRevenueEarnt() {
        return revenueEarnt;
    }

    public String getName() {
        return name;
    }

    public List<Employee> getEmployeeList() {
        return employeeList;
    }

    public List<Item> getItemList() {
        if (itemList == null) {
            setItemList(new ArrayList<>());
        }
        return itemList;
    }

    public List<Drone> getDroneList() {
        if (droneList == null) {
            setDroneList(new ArrayList<>());
        }
        return droneList;
    }

    public List<Order> getOrderList() {
        return orderList;
    }

    //Setters
    public void setRevenueEarnt(double revenueEarnt) {
        this.revenueEarnt = revenueEarnt;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmployeeList(List<Employee> employeeList) {
        this.employeeList = employeeList;
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }

    public void setDroneList(List<Drone> droneList) {
        this.droneList = droneList;
    }

    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
    }

    //Behaviour
    public boolean addNewItem(Item item) { //To update inventory
        List<String> itemNames = getItemList().stream().map(Item::getItemName).toList();
        if (!itemNames.contains(item.getItemName())) {
            this.getItemList().add(item);
            return true;
        }
        return false;
    }

    public boolean addNewDrone(Drone drone) {
        List<Integer> droneIds = getDroneList().stream().map(Drone::getDroneId).toList();
        if (!droneIds.contains(drone.getDroneId())) {
            this.getDroneList().add(drone);
            return true;
        }
        return false;
    }

    public boolean addNewOrder(Order order) {
        if (getOrderList() == null) {
            setOrderList(new ArrayList<>());
        }
        List<String> orderIds = getOrderList().stream().map(Order::getOrderIdentifier).toList();
        if (!orderIds.contains(order.getOrderIdentifier())) {
            this.getOrderList().add(order);
            return true;
        }
        return false;
    }

    public void deleteOrder(Order order) {
        if (!(getOrderList() == null) && !(getOrderList().isEmpty())) {
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
