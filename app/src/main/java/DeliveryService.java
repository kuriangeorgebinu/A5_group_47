import controller.CommandUtilsFactory;
import controller.CommandUtilsFactoryConfiguration;
import model.CommandTypes;
import model.Store;
import model.users.Customer;
import model.users.DronePilots;

import java.util.*;

public class DeliveryService {

    private static final List<Store> stores = new ArrayList();
    private static final List<DronePilots> dronePilots = new ArrayList();
    private static final List<Customer> customers = new ArrayList();
    private static final CommandUtilsFactory commandUtilsFactory = new CommandUtilsFactory();

//    
    public static String execute(String sampleCommand) {
        sampleCommand = sampleCommand.strip(); // remove unnecessary space
        CommandTypes commandType = commandTypes(sampleCommand);
        CommandUtilsFactoryConfiguration commandUtilsFactoryConfiguration = commandUtilsFactoryConfiguration(commandType, sampleCommand);
        commandUtilsFactory.setCommandUtilsFactoryConfiguration(commandUtilsFactoryConfiguration);
        return commandUtilsFactory.executeCommand(commandType);
    }

    private static CommandTypes commandTypes(String command) {
        return CommandTypes.valueOf(command.strip().split(",")[0].toUpperCase(Locale.ROOT));
    }

    private static CommandUtilsFactoryConfiguration commandUtilsFactoryConfiguration(CommandTypes commandTypes, String sampleCommand) {
        CommandUtilsFactoryConfiguration commandUtilsFactoryConfiguration = new CommandUtilsFactoryConfiguration();
        if (commandTypes.equals(CommandTypes.MAKE_PILOT) || commandTypes.equals(CommandTypes.DISPLAY_PILOTS)){
            commandUtilsFactoryConfiguration.setCommand(sampleCommand);
            commandUtilsFactoryConfiguration.setDronePilots(dronePilots);
        }
        else if (commandTypes.equals(CommandTypes.FLY_DRONE)) {
            commandUtilsFactoryConfiguration.setCommand(sampleCommand);
            commandUtilsFactoryConfiguration.setDronePilots(dronePilots);
            commandUtilsFactoryConfiguration.setStores(stores);
        }
        else if (commandTypes.equals(CommandTypes.MAKE_CUSTOMER) || commandTypes.equals(CommandTypes.DISPLAY_CUSTOMERS)) {
            commandUtilsFactoryConfiguration.setCommand(sampleCommand);
            commandUtilsFactoryConfiguration.setCustomers(customers);
        }
        else if (commandTypes.equals(CommandTypes.START_ORDER) || commandTypes.equals(CommandTypes.SET_PROBABILITY) || commandTypes.equals(CommandTypes.MAKE_ANGRY_BIRD)) {
            commandUtilsFactoryConfiguration.setCommand(sampleCommand);
            commandUtilsFactoryConfiguration.setCustomers(customers);
            commandUtilsFactoryConfiguration.setStores(stores);
        }
        else {
            commandUtilsFactoryConfiguration.setCommand(sampleCommand);
            commandUtilsFactoryConfiguration.setStores(stores);
        }
        return commandUtilsFactoryConfiguration;
    }
}
