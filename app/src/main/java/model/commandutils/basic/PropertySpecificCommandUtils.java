package model.commandutils.basic;

import java.util.ArrayList;
import model.CommandTypes;
import model.Location;
import model.Store;

import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class PropertySpecificCommandUtils implements CommandUtils{

    public String makeStore(String makeStoreCommand, List<Store> stores) {
        String [] commandSplit = makeStoreCommand.split(",");
        String storeName = commandSplit[1];
        String revenue = commandSplit[2];
        List<String> storeNames = stores.stream().map(Store::getName).toList();
        if (!storeNames.contains(storeName)) {
            Store store = new Store(storeName, Double.parseDouble(revenue));
            store.setLocationProperty(new Location(LocationType.STORE));
            stores.add(store);
            System.out.println("OK:change_completed");
            return "OK:change_completed";
        }
        else {
            System.out.println("ERROR:store_identifier_already_exists");
            return "ERROR:store_identifier_already_exists"; 
        }
    }

    public String displayStores(String displayStoreCommand, List<Store> stores) {
        String commandTypeString = displayStoreCommand.split(",")[0].toUpperCase(Locale.ROOT);
        CommandTypes commandType = CommandTypes.valueOf(commandTypeString);
        List<String> outputLines = new ArrayList<>(); 
        stores = stores.stream().sorted(Comparator.comparing(Store::getName)).collect(Collectors.toList());
        if (commandType.equals(CommandTypes.DISPLAY_STORES)) {
            for (Store store: stores) {
                System.out.println("name:"+store.getName()+","+"revenue:"+(int) store.getRevenueEarnt());
                outputLines.add("name:"+store.getName()+","+"revenue:"+(int)store.getRevenueEarnt()); 
            }
            System.out.println("OK:display_completed");
            outputLines.add("OK:display_completed"); 
            return String.join("\n", outputLines); 
        }
        return "";
    }
}
