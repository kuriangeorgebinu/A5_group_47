package model.commandutils.basic;
import model.AngryBird;
import model.Store;
import model.users.Customer;

import java.util.ArrayList;
import java.util.List;

public class AngryBirdsSpecificCommandUtils implements CommandUtils {
    
    public String addAngryBirdToLocation(String command, List<Store> storeList, List<Customer> customerList) {
       // String colour, String locationTypeId;
        String [] commandParams = command.split(",");
        String locationTypeId = commandParams[1];
        int numberOfBirds = Integer.parseInt(commandParams[2].strip());
        List<String> storeIdentifiers = storeList.stream().map(Store::getName).toList();
        List<String> customerIdentifiers = customerList.stream().map(Customer::getAccount).toList();
        boolean locationTypeIdContains = (storeIdentifiers.contains(locationTypeId)) || (customerIdentifiers.contains(locationTypeId));
        if (locationTypeIdContains) {
            if (storeIdentifiers.contains(locationTypeId)) {
                List<AngryBird> angryBirds = new ArrayList<>();
                for (int i = 0; i < numberOfBirds; i++) {
                    AngryBird angryBird = new AngryBird();
                    angryBirds.add(angryBird);
                    angryBird.setLocation(storeList.get(storeIdentifiers.indexOf(locationTypeId)).getLocationProperty());
                }
                storeList.get(storeIdentifiers.indexOf(locationTypeId)).getLocationProperty().setAngryBirds(angryBirds);
                System.out.println("OK:number of angry birds set to "+numberOfBirds);
                return "OK:number of angry birds set to "+numberOfBirds;
            }
         else if (customerIdentifiers.contains(locationTypeId)) {
            System.out.println(command); 
            List<AngryBird> angryBirds = new ArrayList<>();
            for (int i = 0; i < numberOfBirds; i++){
                AngryBird angryBird = new AngryBird();
                angryBirds.add(angryBird);
                angryBird.setLocation(customerList.get(customerIdentifiers.indexOf(locationTypeId)).getLocationProperty());
            }
            customerList.get(customerIdentifiers.indexOf(locationTypeId)).getLocationProperty().setAngryBirds(angryBirds);
            System.out.println("OK:number of angry birds set to "+numberOfBirds);
            return "OK:number of angry birds set to "+numberOfBirds;
        }
        }
        System.out.println("ERROR:The location identifier is not valid");
        return "ERROR:The location identifier is not valid";
    }

    public String setProbabilityOfCollisionToLocation(String command, List<Store> storeList, List<Customer> customerList) {
        //String locationTypeId, double probability,
        String [] commandParams = command.split(",");
        String locationTypeId = commandParams[1].strip();
        String probability = commandParams[2].strip();
        List<String> storeIdentifiers = storeList.stream().map(Store::getName).toList();
        List<String> customerIdentifiers = customerList.stream().map(Customer::getAccount).toList();
        boolean locationTypeIdContains = (storeIdentifiers.contains(locationTypeId)) || (customerIdentifiers.contains(locationTypeId));
        if (locationTypeIdContains) {
            if (storeIdentifiers.contains(locationTypeId)) {
                if(storeList.get(storeIdentifiers.indexOf(locationTypeId)).getLocationProperty().getAngryBirds().size() == 0) {
                    System.out.println("ERROR:There must be at least one angry bird in that location");
                    return "ERROR:There must be at least one angry bird in that location";
                }
                storeList.get(storeIdentifiers.indexOf(locationTypeId)).getLocationProperty().setProbCollision(Double.parseDouble(probability));
                System.out.println("OK:probabilty set to "+probability);
                return "OK:probabilty set to "+probability;
            }
            else if (customerIdentifiers.contains(locationTypeId)) {
                if(customerList.get(customerIdentifiers.indexOf(locationTypeId)).getLocationProperty().getAngryBirds().size() == 0) {
                    System.out.println("ERROR:There must be at least one angry bird in that location");
                    return "ERROR:There must be at least one angry bird in that location";
                }
                customerList.get(customerIdentifiers.indexOf(locationTypeId)).getLocationProperty().setProbCollision(Double.parseDouble(probability));
                System.out.println("OK:probabilty set to "+probability);
                return "OK:probabilty set to "+probability;
            }

        }
        System.out.println("ERROR:location_identifier_is_not_valid");
        return "ERROR:location_identifier_is_not_valid";
    }
}
