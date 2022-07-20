package edu.gatech.cs6310.model.commandutils.basic;

import edu.gatech.cs6310.CommandTypes;
import edu.gatech.cs6310.model.Store;

import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class PropertySpecificCommandUtils implements CommandUtils{

    public void makeStore(String makeStoreCommand, List<Store> stores) {
        String [] commandSplit = makeStoreCommand.split(",");
        String storeName = commandSplit[1];
        String revenue = commandSplit[2];
        List<String> storeNames = stores.stream().map(Store::getName).toList();
        if (!storeNames.contains(storeName)) {
            stores.add(new Store(storeName, Double.parseDouble(revenue)));
            System.out.println("OK:change_completed");
        }
        else {
            System.out.println("ERROR:store_identifier_already_exists");
        }
    }

    public void displayStores(String displayStoreCommand, List<Store> stores) {
        String commandTypeString = displayStoreCommand.split(",")[0].toUpperCase(Locale.ROOT);
        CommandTypes commandType = CommandTypes.valueOf(commandTypeString);
        stores = stores.stream().sorted(Comparator.comparing(Store::getName)).collect(Collectors.toList());
        if (commandType.equals(CommandTypes.DISPLAY_STORES)) {
            for (Store store: stores) {
                System.out.println("name:"+store.getName()+","+"revenue:"+(int) store.getRevenueEarnt());
            }
            System.out.println("OK:display_completed");
        }
    }
}
