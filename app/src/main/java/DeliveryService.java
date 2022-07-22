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

    public void commandLoop() {
        Scanner commandLineInput = new Scanner(System.in);
        String wholeInputLine;
        String[] tokens;
        final String DELIMITER = ",";

        while (true) {
            try {
                // Determine the next command and echo it to the monitor for testing purposes
                wholeInputLine = commandLineInput.nextLine();
                tokens = wholeInputLine.split(DELIMITER);
                System.out.println("> " + wholeInputLine);
                List<String> commandTypes = Arrays.stream(CommandTypes.values()).map(Enum::name).toList();
                if (tokens[0].equals("author")) {
                    execute(wholeInputLine);
                } else if (wholeInputLine.contains("//")) {
                    //do nothing
                } else if (tokens[0].equals("stop")) {
                    System.out.println("stop acknowledged");
                    break;
                } else if (!commandTypes.contains(tokens[0].toUpperCase(Locale.ROOT))) {
                    System.out.println("command " + tokens[0] + " NOT acknowledged");
                }
                else {
                    execute(wholeInputLine);
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println();
            }
        }

        System.out.println("simulation terminated");
        commandLineInput.close();
    }




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
