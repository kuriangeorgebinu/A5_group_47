package edu.gatech.cs6310.model.commandutils.basic;

import edu.gatech.cs6310.CommandTypes;
import edu.gatech.cs6310.model.users.Customer;
import edu.gatech.cs6310.model.users.DronePilots;
import edu.gatech.cs6310.model.users.Users;

import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class UserSpecificCommandUtils implements CommandUtils {

    public void makeCustomer(String makeCustomerCommand, List<Customer> customerList) {
        String [] commands = makeCustomerCommand.split(",");
        String account = commands[1];
        String firstName = commands[2];
        String lastName = commands[3];
        String phoneNumber = commands[4];
        String customerRating = commands[5];
        String credits = commands[6];
        List<String> customerAccounts = customerList.stream().map(Users::getAccount).toList();
        if (customerAccounts.contains(account)) {
            System.out.println("ERROR:customer_identifier_already_exists");
        }
        else {
            Customer customer = new Customer(account, firstName, lastName, phoneNumber, customerRating, Double.parseDouble(credits));
            customerList.add(customer);
            System.out.println("OK:change_completed");
        }
    }

    public void displayCustomer(String displayCustomerCommand, List<Customer> customerList) {
        String [] command = displayCustomerCommand.split(",");
        String commandTypeString = command[0];
        CommandTypes commandType = CommandTypes.valueOf(commandTypeString.toUpperCase(Locale.ROOT));
        customerList = customerList.stream().sorted(Comparator.comparing(Customer::getAccount)).collect(Collectors.toList());
        if(commandType.equals(CommandTypes.DISPLAY_CUSTOMERS)) {
            for (Customer customer: customerList) {
                System.out.println("name:"+customer.getFirstName()+"_"+customer.getLastName()+",phone:"+customer.getPhoneNumber()+",rating:"+customer.getRating()+",credit:"+(int) customer.getCredit());
            }
            System.out.println("OK:display_completed");
        }
    }

    public void makePilot(String makePilotCommand, List<DronePilots> dronePilots) {
        String [] command = makePilotCommand.split(",");
        String accountName = command[1];
        String firstName = command[2];
        String lastName = command[3];
        String phoneNumber = command[4];
        String taxId = command[5];
        String licenseId = command[6];
        String numDeliveries = command[7];
        List<String> dronePilotAccounts = dronePilots.stream().map(Users::getAccount).toList();
        List<String> dronePilotLicenseId = dronePilots.stream().map(DronePilots::getLicenseId).toList();
        if (dronePilotAccounts.contains(accountName)) {
            System.out.println("ERROR:pilot_identifier_already_exists");
        }
        else if (dronePilotLicenseId.contains(licenseId)) {
            System.out.println("ERROR:pilot_license_already_exists");
        }
        else {
            DronePilots dronePilot = new DronePilots(accountName, firstName, lastName, phoneNumber, taxId, licenseId, Integer.parseInt(numDeliveries));
            dronePilot.setAccount(accountName);
            dronePilots.add(dronePilot);
            System.out.println("OK:change_completed");
        }
    }

    public void displayPilot(String displayPilotsCommand, List<DronePilots> dronePilots) {
        String commandTypeString = displayPilotsCommand.strip().split(",")[0].toUpperCase(Locale.ROOT);
        CommandTypes commandTypes = CommandTypes.valueOf(commandTypeString);
        if (commandTypes.equals(CommandTypes.DISPLAY_PILOTS)) {
            dronePilots = dronePilots.stream().sorted(Comparator.comparing(DronePilots::getAccount)).collect(Collectors.toList());
            for (DronePilots dronePilot: dronePilots) {
                System.out.println("name:"+dronePilot.getFirstName()+"_"+dronePilot.getLastName()+",phone:"+dronePilot.getPhoneNumber()+",taxID:"+dronePilot.getSsnNumber()+",licenseID:"+dronePilot.getLicenseId()+",experience:"+dronePilot.getNumDeliveries());
            }
            System.out.println("OK:display_completed");
        }
    }
}
