package edu.gatech.cs6310.model.commandutils.basic;

import edu.gatech.cs6310.model.*;
import edu.gatech.cs6310.model.users.Customer;
import edu.gatech.cs6310.model.users.Users;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class OrderSpecificCommandUtils implements CommandUtils{

    public void makeOrder(String makeOrderCommand, List<Store> storeList, List<Customer> customerList) {
        String[] commands = makeOrderCommand.split(",");
        String storeName = commands[1];
        String orderIdentifier = commands[2];
        String droneId = commands[3];
        String customerAccount = commands[4];
        List<String> storeIdentifiers = storeList.stream().map(Store::getName).toList();
        ;
        if (!storeIdentifiers.contains(storeName))
            System.out.println("ERROR:store_identifier_does_not_exist");
        else {
            Store store = storeList.get(storeIdentifiers.indexOf(storeName));
            List<Drone> droneList = store.getDroneList();
            List<Integer> droneIdentifiers = droneList.stream().map(Drone::getDroneId).toList();
            if (!droneIdentifiers.contains(Integer.parseInt(droneId)))
                System.out.println("ERROR:drone_identifier_does_not_exist");
            else {
                List<String> customerIdentifiers = customerList.stream().map(Users::getAccount).toList();
                if (!customerIdentifiers.contains(customerAccount))
                    System.out.println("ERROR:customer_identifier_does_not_exist");
                else {
                    Order order = new Order(orderIdentifier);
                    Drone drone = droneList.get(droneIdentifiers.indexOf(Integer.parseInt(droneId)));
                    Customer customer = customerList.get(customerIdentifiers.indexOf(customerAccount));
                    boolean isUnique = store.addNewOrder(order);
                    if (!isUnique) System.out.println("ERROR:order_identifier_already_exists");
                    else {
                        order.setCustomer(customer);
                        order.setStore(store);
                        order.setDrone(drone);
                        customer.addNewOrder(order);
                        drone.addNewOrder(order);
                        System.out.println("OK:change_completed");
                    }
                }
            }
        }
    }

    public void displayOrders(String displayOrderCommand, List<Store> stores) {
        String[] command = displayOrderCommand.split(",");
        List<String> storeNames = stores.stream().map(Store::getName).toList();
        String storeName = command[1];
        if (!storeNames.contains(storeName)) System.out.println("ERROR:store_identifier_does_not_exist");
        else {
            Store store = stores.get(storeNames.indexOf(storeName));
            List<Order> orderList = store.getOrderList();
            orderList = orderList.stream().sorted(Comparator.comparing(Order::getOrderIdentifier)).collect(Collectors.toList());
            for (Order order : orderList) {
                System.out.println("orderID:" + order.getOrderIdentifier());
                if (!(order.getLineItems() == null)) {
                    List<LineItem> lineItems = order.getLineItems();
                    lineItems = lineItems.stream().sorted(Comparator.comparing(lineItem -> lineItem.getItem().getItemName())).collect(Collectors.toList());
                    for (LineItem lineItem : lineItems) {
                        if (!(lineItem.getItem() == null)) {
                            Item item = lineItem.getItem();
                            if (!(item == null)) {
                                System.out.println("item_name:" + item.getItemName() + ",total_quantity:" + lineItem.getQuantity() + ",total_cost:" + (int) totalCost(lineItem.getUnitPrice(), lineItem.getQuantity()) + ",total_weight:" + (int) totalWeight(item.getWeight(), lineItem.getQuantity()));
                            }
                        }
                    }
                }



            }
            System.out.println("OK:display_completed");
        }
    }


    public void executePurchaseOrder(String puchaseOrderCommand, List<Store> storeList) {
        String[] commands = puchaseOrderCommand.split(",");
        String storeName = commands[1];
        String orderIdentifier = commands[2];
        List<String> storeNames = storeList.stream().map(Store::getName).toList();
        if (!(storeNames.contains(storeName))) System.out.println("ERROR:store_identifier_does_not_exist");
        else {
            Store store = storeList.get(storeNames.indexOf(storeName));
            List<String> orderList = store.getOrderList().stream().map(Order::getOrderIdentifier).toList();
            if (!(orderList.contains(orderIdentifier))) System.out.println("ERROR:order_identifier_does_not_exist");
            else {

                List<Drone> droneList = store.getDroneList().stream().filter(drone -> drone.getOrderList().stream()
                        .map(Order::getOrderIdentifier).toList().contains(orderIdentifier)).toList();
                if (!droneList.isEmpty()) {
                    Drone drone = droneList.get(0);
                    Order order = store.getOrderList().get(orderList.indexOf(orderIdentifier));
                    drone.executeOrder(order);
                }
            }
        }
    }

    public void executeCancelOrder(String cancelOrderCommand, List<Store> storeList) {
        String [] command = cancelOrderCommand.split(",");
        String storeName = command[1];
        String orderIdentifier = command[2];
        List<String> storenames = storeList.stream().map(Store::getName).toList();
        if (!storenames.contains(storeName)) System.out.println("ERROR:store_identifier_does_not_exist");
        else {
            Store store = storeList.get(storenames.indexOf(storeName));
            List<String> orderIds = store.getOrderList().stream().map(Order::getOrderIdentifier).toList();
            if (!orderIds.contains(orderIdentifier)) System.out.println("ERROR:order_identifier_does_not_exist");
            else {
                Order order = store.getOrderList().stream().filter(eachOrder -> eachOrder.getOrderIdentifier().equals(orderIdentifier)).toList().get(0);
                order.getCustomer().deleteOrder(order);
                order.getDrone().deleteOrder(order);
                order.getStore().deleteOrder(order);
                order.setCustomer(null);
                order.setDrone(null);
                order.setStore(null);
                System.out.println("OK:change_completed");
            }
        }
    }

    public void requestItemToOrder(String requestOrderCommand, List<Store> listOfStores) {
        String [] command = requestOrderCommand.split(",");
        String storeName = command[1];
        String orderIdentifier = command[2];
        String itemName = command[3];
        String quantity = command[4];
        String unitPrice = command[5];
        List<String> storeNames = listOfStores.stream().map(Store::getName).toList();

        if (!storeNames.contains(storeName)) System.out.println("ERROR:store_identifier_does_not_exist");
        else {
            Store store = listOfStores.get(storeNames.indexOf(storeName));
            List<Order> orderList = store.getOrderList();
            List<String> orderIdentifiers = orderList.stream().map(Order::getOrderIdentifier).toList();
            if(!orderIdentifiers.contains(orderIdentifier)) System.out.println("ERROR:order_identifier_does_not_exist");
            else {
                Order order = orderList.get(orderIdentifiers.indexOf(orderIdentifier));
                List<Item> itemInventory = store.getItemList();
                List<String> itemNames = itemInventory.stream().map(Item::getItemName).toList();
                if (!itemNames.contains(itemName)) System.out.println("ERROR:item_identifier_does_not_exist");
                else {
                    Item item = itemInventory.get(itemNames.indexOf(itemName));
                    Boolean ret = order.addNewLineItem(new LineItem(item, Integer.parseInt(quantity), Double.parseDouble(unitPrice)));
                    if (ret) System.out.println("OK:change_completed");
                }
            }
        }
    }

    public static double totalCost(double unitPrice, int quantity) {
        return unitPrice * quantity;
    }

    public static double totalWeight(double unitWeight, int quantity) {
        return unitWeight * quantity;
    }

}
