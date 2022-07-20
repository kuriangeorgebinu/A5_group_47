package edu.gatech.cs6310.model;


import edu.gatech.cs6310.model.users.DronePilots;

import java.util.ArrayList;
import java.util.List;

public class Drone {

    //Primary attributes
    private int droneId;

    private int numDelivered;

    private int numThreshold;

    private double liftingCapacity;

    //associations
    private List<Order> orderList;
    private DronePilots dronePilot;

    public Drone(int droneId, int numThreshold, int liftingCapacity) { //New drone
        this.droneId = droneId;
        this.numThreshold = numThreshold;
        this.liftingCapacity = liftingCapacity;
        this.numDelivered = 0;
        orderList = new ArrayList<>();
    }

    //Getter
    public int getNumThreshold() {
        return numThreshold;
    }

    public int getDroneId() {
        return droneId;
    }

    public int getNumDelivered() {
        return this.numDelivered;
    }

    public int getCurrentOrdersPending() {
        return getOrderList().size();
    }

    public int getTripsPendingBeforeFuel() {
        return getNumThreshold() - getNumDelivered();
    }

    public double getLiftingCapacity() {
        return liftingCapacity;
    }

    public List<Order> getOrderList() {
        return orderList;
    }

    public DronePilots getDronePilot() {
        return dronePilot;
    }


    //Setters
    public void setNumDelivered(int numDelivered) {
        this.numDelivered = numDelivered;
    }

    public void setNumThreshold(int numThreshold) {
        this.numThreshold = numThreshold;
    }

    public void setLiftingCapacity(int liftingCapacity) {
        this.liftingCapacity = liftingCapacity;
    }

    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
    }

    public void setDronePilot(DronePilots dronePilot) {
        this.dronePilot = dronePilot;
    }


    //Behaviours
    public double getRemainingCap() {
        if (orderList == null || orderList.isEmpty()) {
            return this.getLiftingCapacity();
        }
        return getLiftingCapacity() - getTotalPendingOrderWeights();
    }

    public double getTotalPendingOrderWeights() {
        return getOrderList().stream()
                .map(Order::getTotalWeight)
                .reduce(Double::sum).stream().mapToDouble(i -> i).sum();
    }

    public boolean addNewOrder(Order order) {
        if (getOrderList() == null) {
            setOrderList(new ArrayList<>());
        }
        List<String> orderNames = getOrderList().stream().map(Order::getOrderIdentifier).toList();
        if (!orderNames.contains(order.getOrderIdentifier())) {
            this.getOrderList().add(order);
            return true;
        }
        return false;
    }

    public void refuel() {
        System.out.println("refueling");
        this.numDelivered = 0;
    }


    public void executeOrder(Order order) {
        List<String> orderList = getOrderList().stream().map(Order::getOrderIdentifier).toList();

        if (getTripsPendingBeforeFuel() <= 0) System.out.println("drone_needs_fuel");
        else if (getDronePilot() == null) System.out.println("drone_needs_pilot");
        else {
            /* Generate Random numbers with probability of the prob of location assigned */
            if ((orderList.contains(order.getOrderIdentifier()))) {
                getDronePilot().setNumDeliveries(getDronePilot().getNumDeliveries()+1);
                setNumDelivered(getNumDelivered()+1);
                order.getStore().setRevenueEarnt(order.getStore().getRevenueEarnt()+order.getTotalCost());
                order.deductCustomerCredits(order.getTotalCost());
                order.getCustomer().deleteOrder(order);
                deleteOrder(order);
                order.getStore().deleteOrder(order);
                System.out.println("OK:change_completed");
            }
        }
    }

    public void deleteOrder(Order order) {
        if (!(getOrderList() == null) && !(getOrderList().isEmpty())) {
            List<String> orderNames = getOrderList().stream().map(Order::getOrderIdentifier).toList();
            if (orderNames.contains(order.getOrderIdentifier())) {
                getOrderList().remove(order);
            }
        }
    }

}
