package controller;
import model.CommandTypes;
import model.commandutils.UtilTypes;
import model.commandutils.basic.*;

import java.util.Map;


public class CommandUtilsFactory {
    private CommandUtilsFactoryConfiguration commandUtilsFactoryConfiguration;
    private static final String gatechUser = "kbinu3";

    public String executeCommand(CommandTypes commandType) {
        Map<UtilTypes, CommandUtils> commandUtilsMap = commandUtilsFactoryConfiguration.getCommandUtils();
        PropertySpecificCommandUtils propertySpecificCommandUtils = commandUtilsMap.get(UtilTypes.PROPERTY) instanceof PropertySpecificCommandUtils
                ? (PropertySpecificCommandUtils) commandUtilsMap.get(UtilTypes.PROPERTY) : null;
        OrderSpecificCommandUtils orderSpecificCommandUtils = commandUtilsMap.get(UtilTypes.ORDER) instanceof OrderSpecificCommandUtils
                ? (OrderSpecificCommandUtils) commandUtilsMap.get(UtilTypes.ORDER): null;
        UserSpecificCommandUtils userSpecificCommandUtils = commandUtilsMap.get(UtilTypes.USER) instanceof UserSpecificCommandUtils
                ? (UserSpecificCommandUtils) commandUtilsMap.get(UtilTypes.USER) : null;
        AssetSpecificCommandUtils assetSpecificCommandUtils = commandUtilsMap.get(UtilTypes.ASSET) instanceof AssetSpecificCommandUtils
                ? (AssetSpecificCommandUtils) commandUtilsMap.get(UtilTypes.ASSET) : null;
        AngryBirdsSpecificCommandUtils angryBirdsSpecificCommandUtils = commandUtilsMap.get(UtilTypes.ANGRYBIRDS) instanceof AngryBirdsSpecificCommandUtils
                ? (AngryBirdsSpecificCommandUtils) commandUtilsMap.get(UtilTypes.ANGRYBIRDS) : null;
        if (commandType.equals(CommandTypes.MAKE_STORE) && propertySpecificCommandUtils!=null)
            return propertySpecificCommandUtils.makeStore(commandUtilsFactoryConfiguration.getCommand(), commandUtilsFactoryConfiguration.getStores());
        else if (commandType.equals(CommandTypes.DISPLAY_STORES) && propertySpecificCommandUtils!=null)
            return propertySpecificCommandUtils.displayStores(commandUtilsFactoryConfiguration.getCommand(), commandUtilsFactoryConfiguration.getStores());
        else if (commandType.equals(CommandTypes.SELL_ITEM) && assetSpecificCommandUtils!=null)
            return assetSpecificCommandUtils.makeItem(commandUtilsFactoryConfiguration.getCommand(), commandUtilsFactoryConfiguration.getStores());
        else if (commandType.equals(CommandTypes.DISPLAY_ITEMS) && assetSpecificCommandUtils!=null)
            return assetSpecificCommandUtils.displayItems(commandUtilsFactoryConfiguration.getCommand(), commandUtilsFactoryConfiguration.getStores());
        else if (commandType.equals(CommandTypes.MAKE_PILOT) && userSpecificCommandUtils!=null)
            return userSpecificCommandUtils.makePilot(commandUtilsFactoryConfiguration.getCommand(), commandUtilsFactoryConfiguration.getDronePilots());
        else if (commandType.equals(CommandTypes.DISPLAY_PILOTS) && userSpecificCommandUtils!=null)
            return userSpecificCommandUtils.displayPilot(commandUtilsFactoryConfiguration.getCommand(), commandUtilsFactoryConfiguration.getDronePilots());
        else if (commandType.equals(CommandTypes.MAKE_DRONE) && assetSpecificCommandUtils!=null)
            return assetSpecificCommandUtils.makeDrone(commandUtilsFactoryConfiguration.getCommand(), commandUtilsFactoryConfiguration.getStores());
        else if (commandType.equals(CommandTypes.DISPLAY_DRONES) && assetSpecificCommandUtils!=null)
            return assetSpecificCommandUtils.displayDrones(commandUtilsFactoryConfiguration.getCommand(), commandUtilsFactoryConfiguration.getStores());
        else if (commandType.equals(CommandTypes.FLY_DRONE) && assetSpecificCommandUtils!=null)
            return assetSpecificCommandUtils.flyDrone(commandUtilsFactoryConfiguration.getCommand(), commandUtilsFactoryConfiguration.getStores(), commandUtilsFactoryConfiguration.getDronePilots());
        else if (commandType.equals(CommandTypes.MAKE_CUSTOMER) && userSpecificCommandUtils!=null)
            return userSpecificCommandUtils.makeCustomer(commandUtilsFactoryConfiguration.getCommand(), commandUtilsFactoryConfiguration.getCustomers());
        else if (commandType.equals(CommandTypes.DISPLAY_CUSTOMERS) && userSpecificCommandUtils!=null)
            return userSpecificCommandUtils.displayCustomer(commandUtilsFactoryConfiguration.getCommand(), commandUtilsFactoryConfiguration.getCustomers());
        else if (commandType.equals(CommandTypes.START_ORDER) && orderSpecificCommandUtils!=null)
            return orderSpecificCommandUtils.makeOrder(commandUtilsFactoryConfiguration.getCommand(), commandUtilsFactoryConfiguration.getStores(), commandUtilsFactoryConfiguration.getCustomers());
        else if (commandType.equals(CommandTypes.DISPLAY_ORDERS) && orderSpecificCommandUtils!=null)
            return orderSpecificCommandUtils.displayOrders(commandUtilsFactoryConfiguration.getCommand(), commandUtilsFactoryConfiguration.getStores());
        else if (commandType.equals(CommandTypes.REQUEST_ITEM) && orderSpecificCommandUtils!=null)
            return orderSpecificCommandUtils.requestItemToOrder(commandUtilsFactoryConfiguration.getCommand(), commandUtilsFactoryConfiguration.getStores());
        else if (commandType.equals(CommandTypes.PURCHASE_ORDER) && orderSpecificCommandUtils!=null)
            return orderSpecificCommandUtils.executePurchaseOrder(commandUtilsFactoryConfiguration.getCommand(), commandUtilsFactoryConfiguration.getStores());
        else if (commandType.equals(CommandTypes.CANCEL_ORDER) && orderSpecificCommandUtils!=null)
            return orderSpecificCommandUtils.executeCancelOrder(commandUtilsFactoryConfiguration.getCommand(), commandUtilsFactoryConfiguration.getStores());
        else if (commandType.equals(CommandTypes.SET_PROBABILITY) && angryBirdsSpecificCommandUtils!=null)
            return angryBirdsSpecificCommandUtils.setProbabilityOfCollisionToLocation(commandUtilsFactoryConfiguration.getCommand(), commandUtilsFactoryConfiguration.getStores(), commandUtilsFactoryConfiguration.getCustomers());
        else if (commandType.equals(CommandTypes.MAKE_ANGRY_BIRD) && angryBirdsSpecificCommandUtils!=null)
            return angryBirdsSpecificCommandUtils.addAngryBirdToLocation(commandUtilsFactoryConfiguration.getCommand(), commandUtilsFactoryConfiguration.getStores(), commandUtilsFactoryConfiguration.getCustomers());
        else if(commandType.equals(CommandTypes.AUTHOR))
           return gatechUser;
       
       return "";
    }

    public void setCommandUtilsFactoryConfiguration(CommandUtilsFactoryConfiguration commandUtilsFactoryConfiguration) {
        this.commandUtilsFactoryConfiguration = commandUtilsFactoryConfiguration;
    }
}
