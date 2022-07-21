package model.commandutils.basic;

import model.AngryBird;
import model.Location;
import model.Store;
import model.users.Customer;
import model.users.Users;

import java.util.List;

public class LocationSpecificCommandUtils {

    public void createLocation(String locationTypeString, int probabilityOfCollision, String locationTypeIdentifier, List<Store> stores, List<Customer> customers) {
        LocationType locationType = LocationType.valueOf(locationTypeString);
        Location location = new Location(probabilityOfCollision, locationType);
        if (locationType.equals(LocationType.STORE)) {
            List<String> storeIdentifiers = stores.stream().map(Store::getName).toList();
            if (!storeIdentifiers.contains(locationTypeIdentifier)) {
                System.out.println("Store identifier does not exist");
            }
            else {
                Store store = stores.get(storeIdentifiers.indexOf(locationTypeIdentifier));
                store.setLocationProperty(location);
            }
        }
        else if (locationType.equals(LocationType.CUSTOMER)) {
            List<String> customerIdentifiers = customers.stream().map(Users::getAccount).toList();
            if (!customerIdentifiers.contains(locationTypeIdentifier)) {
                System.out.println("Customer identifier does not exits");
            }
            else {
                Customer customer = customers.get(customerIdentifiers.indexOf(locationTypeIdentifier));
                customer.setLocationProperty(location);
            }
        }
    }

    public void addAngryBirdToLocationIdentifier(String locationTypeIdentifier, AngryBird angryBird, List<Store> stores, List<Customer> customers) {
        List<String> storeIdentifiers = stores.stream().map(Store::getName).toList();
        List<String> customerIdentifiers = customers.stream().map(Customer::getAccount).toList();
        if (storeIdentifiers.contains(locationTypeIdentifier)) {
            Store store = stores.get(storeIdentifiers.indexOf(locationTypeIdentifier));
            if (store.getLocationProperty() == null) {
                System.out.println("No location Property exists for that location");
            }
            else {
                store.getLocationProperty().addAngryBirdToLocation(angryBird);
            }
        }
        else if (customerIdentifiers.contains(locationTypeIdentifier)) {
            Customer customer = customers.get(customerIdentifiers.indexOf(locationTypeIdentifier));
            if (customer.getLocationProperty() == null) {
                customer.getLocationProperty().addAngryBirdToLocation(angryBird);
            } else {
                customer.getLocationProperty().addAngryBirdToLocation(angryBird);
            }
        }
    }
}
