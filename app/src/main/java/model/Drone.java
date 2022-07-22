package model;
import java.util.ArrayList;
import java.util.List;
import model.users.DronePilots;

public class Drone {

    //Primary attributes
    private int droneId;

    private int numTripsExecuted;

    private int numThreshold;

    private double liftingCapacity;

    //associations
    private List<Order> orderList;
    private DronePilots dronePilot;
    private Store store;

    public Drone(int droneId, int numThreshold, int liftingCapacity) { //New drone
        this.droneId = droneId;
        this.numThreshold = numThreshold;
        this.liftingCapacity = liftingCapacity;
        this.numTripsExecuted = 0;
        orderList = new ArrayList<>();
    }

    //Getter
    public int getNumThreshold() {
        return numThreshold;
    }

    public int getDroneId() {
        return droneId;
    }

    public int getNumTripsExecuted() {
        return this.numTripsExecuted;
    }

    public int getCurrentOrdersPending() {
        return getOrderList().size();
    }

    public int getTripsPendingBeforeFuel() {
        return getNumThreshold() - getNumTripsExecuted();
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

    public Store getStore() {return store;}


    //Setters
    public void setNumTripsExecuted(int numDelivered) {
        this.numTripsExecuted = numDelivered;
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

    public void setStore(Store store) {this.store = store;}

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

    public String executeOrder(Order order) {
        List<String> orderList = getOrderList().stream().map(Order::getOrderIdentifier).toList();

        if (getTripsPendingBeforeFuel() <= 0)  {
            List<Drone> alternativeDrones = getStore().getDroneList().stream()
                    .filter(drone -> (this.droneId!=drone.getDroneId()) && drone.getTripsPendingBeforeFuel()>0 && order.getTotalWeight()<=getLiftingCapacity()).toList();
            if (alternativeDrones.size()<=0) {
                System.out.println("drone_needs_fuel! No available drones to switched");
                return "ERROR:drone_needs_fuel! No available drones to be switched to";
            } else {
                System.out.println("drone needs fuel! Switching to the next available drone");
                Drone drone = alternativeDrones.get(0);
                drone.getOrderList().add(order);
                order.setDrone(drone);
                getOrderList().remove(order);
                return "ERROR:drone needs fuel! Switching to the next available drone";
            }
        }
        else if (getDronePilot() == null) {
            System.out.println("drone_needs_pilot");
            return "ERROR:drone_needs_pilot";
        }
        else {
            /* Generate Random numbers with probability of the prob of location assigned */
            double probabilityStore = order.getStore().getLocationProperty().getProbCollision();
            double probabilityCustomer = order.getCustomer().getLocationProperty().getProbCollision();
            boolean trigger = (getRandomNumberFromProb(probabilityStore) == 0 || getRandomNumberFromProb(probabilityCustomer) == 0);
            if(trigger){
                System.out.println("Angry bird has hit the drone. Drone "+getDroneId()+" has to go for repair. Try order again!");
                setNumTripsExecuted(getNumTripsExecuted()+1);
                return "ERROR:Angry bird has hit the drone. Drone "+getDroneId()+" has to go for repair. Try order again!";
            } else {
                if ((orderList.contains(order.getOrderIdentifier()))) {
                    getDronePilot().setNumDeliveries(getDronePilot().getNumDeliveries()+1);
                    setNumTripsExecuted(getNumTripsExecuted()+1);
                    order.getStore().setRevenueEarnt(order.getStore().getRevenueEarnt()+order.getTotalCost());
                    order.deductCustomerCredits(order.getTotalCost());
                    order.getCustomer().deleteOrder(order);
                    deleteOrder(order);
                    order.getStore().deleteOrder(order);
                    System.out.println("OK:change_completed");
                }
            }
            return "OK:change_completed";
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

    //helper function
    private int getRandomNumberFromProb(double probability) {
        return probability == 0 ? 1 : (int)((1/probability)*Math.random());
    }

}
