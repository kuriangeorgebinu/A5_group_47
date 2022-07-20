package edu.gatech.cs6310.model.commandutils.basic;

import edu.gatech.cs6310.CommandTypes;
import edu.gatech.cs6310.model.Drone;
import edu.gatech.cs6310.model.Item;
import edu.gatech.cs6310.model.Store;
import edu.gatech.cs6310.model.users.DronePilots;
import edu.gatech.cs6310.model.users.Users;

import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class AssetSpecificCommandUtils implements  CommandUtils{

    public void makeDrone(String makeDroneCommand, List<Store> stores) {
        String [] command = makeDroneCommand.strip().split(",");
        String storeName = command[1];
        int droneId = Integer.parseInt(command[2]);
        int liftingCapacity = Integer.parseInt(command[3]);
        int maxTrips = Integer.parseInt(command[4]);
        List<String> storeNames = stores.stream().map(Store::getName).toList();
        if (!storeNames.contains(storeName)) {
            System.out.println("ERROR:store_identifier_does_not_exist");
        } else {
            Drone drone = new Drone(droneId, maxTrips, liftingCapacity);
            int expectedIndex = storeNames.indexOf(storeName);
            boolean uniqueDrone = stores.get(expectedIndex).addNewDrone(drone);
            if (uniqueDrone) {
                System.out.println("OK:change_completed");
            }
            else {
                System.out.println("ERROR:drone_identifier_already_exists");
            }
        }
    }

    public void displayDrones(String displayDronesCommand, List<Store> stores) {
        String [] command = displayDronesCommand.split(",");
        String storeName = command[1];
        List<String> storeNames = stores.stream().map(Store::getName).toList();
        if (!storeNames.contains(storeName)) {
            System.out.println("ERROR:store_identifier_does_not_exist");
        }
        else {
            int storeIndex = storeNames.indexOf(storeName);
            List<Drone> droneList = stores.get(storeIndex).getDroneList();
            droneList = droneList.stream().sorted(Comparator.comparingInt(Drone::getDroneId)).collect(Collectors.toList());
            for (Drone drone: droneList) {
                if (drone.getDronePilot() == null) {
                    System.out.println("droneID:"+drone.getDroneId()+",total_cap:"+(int) drone.getLiftingCapacity()+",num_orders:"+drone.getCurrentOrdersPending()+",remaining_cap:"+(int) drone.getRemainingCap()+",trips_left:"+drone.getTripsPendingBeforeFuel());
                }
                else {
                    System.out.println("droneID:"+drone.getDroneId()+",total_cap:"+(int) drone.getLiftingCapacity()+",num_orders:"+drone.getCurrentOrdersPending()+",remaining_cap:"+(int) drone.getRemainingCap()
                            +",trips_left:"+drone.getTripsPendingBeforeFuel()+",flown_by:"+drone.getDronePilot().getFirstName()+"_"+drone.getDronePilot().getLastName());
                }
            }
            System.out.println("OK:display_completed");
        }
    }

    public void flyDrone(String flyDroneCommand, List<Store> stores, List<DronePilots> dronePilots) {
        String [] command = flyDroneCommand.split(",");
        String storeName = command[1];
        int droneIdentifier = Integer.parseInt(command[2]);
        String dronePilotAccount = command[3];
        List<String> storeNames = stores.stream().map(Store::getName).toList();
        List<String> dronePilotAccounts = dronePilots.stream().map(Users::getAccount).toList();;
        if (!dronePilotAccounts.contains(dronePilotAccount)) {
            System.out.println("ERROR:pilot_identifier_does_not_exist");
        } else {
            DronePilots dronePilot = dronePilots.get(dronePilotAccounts.indexOf(dronePilotAccount));
            if (!storeNames.contains(storeName)) {
                System.out.println("ERROR:store_identifier_does_not_exist");
            }
            else {
                Store store = stores.get(storeNames.indexOf(storeName));
                List<Drone> droneList = store.getDroneList();
                List<Integer> droneIdList = droneList.stream().map(Drone::getDroneId).toList();
                if(!droneIdList.contains(droneIdentifier)) {
                    System.out.println("ERROR:drone_identifier_does_not_exist");
                }
                else {
                    Drone drone = droneList.get(droneIdList.indexOf(droneIdentifier));
                    if (drone.getDronePilot() == null && dronePilot.getDrone() == null) {
                        drone.setDronePilot(dronePilot);
                        dronePilot.setDrone(drone);
                    }
                    else {
                        dronePilot.getDrone().setDronePilot(null);
                        dronePilot.setDrone(drone);
                        dronePilot.getDrone().setDronePilot(dronePilot);
                    }
                    droneList.set(droneIdList.indexOf(droneIdentifier), drone);
                    System.out.println("OK:change_completed");
                }
            }
        }
    }


    public void makeItem(String sampleCommand, List<Store> stores) {
        String [] commandSplit = sampleCommand.split(",");
        CommandTypes commandType = CommandTypes.valueOf(commandSplit[0].toUpperCase(Locale.ROOT));
        if(commandType.equals(CommandTypes.SELL_ITEM)) {
            String storeName = commandSplit[1];
            String itemName = commandSplit[2];
            String itemWeight = commandSplit[3];
            Item item = new Item(itemName, Double.parseDouble(itemWeight));
            List<String> storeNames = stores.stream().map(Store::getName).toList();
            if (storeNames.contains(storeName)) {
                int index = storeNames.indexOf(storeName);
                boolean addStatus = stores.get(index).addNewItem(item);
                if (addStatus) {
                    System.out.println("OK:change_completed");
                }
                else {
                    System.out.println("ERROR:item_identifier_already_exists");
                }
            }
            else {
                System.out.println("ERROR:store_identifier_does_not_exist");
            }
        }
    }

    public void displayItems(String displayItemsCommand, List<Store> stores) {
        String [] commandSplit = displayItemsCommand.split(",");
        CommandTypes commandType = CommandTypes.valueOf(commandSplit[0].toUpperCase(Locale.ROOT));
        if(commandType.equals(CommandTypes.DISPLAY_ITEMS)) {
            String storeName = commandSplit[1];
            List<Store> storeList = stores.stream().filter(store -> store.getName().equals(storeName)).toList();
            if (storeList.isEmpty()) {
                System.out.println("ERROR:store_identifier_does_not_exist");
            }
            else {
                List<Item> itemList = storeList.get(0).getItemList();
                itemList = itemList.stream().sorted(Comparator.comparing(Item::getItemName)).collect(Collectors.toList());
                for (Item item: itemList) {
                    System.out.println(item.getItemName()+","+(int) item.getWeight());
                }
                System.out.println("OK:display_completed");
            }
        }
    }
}
