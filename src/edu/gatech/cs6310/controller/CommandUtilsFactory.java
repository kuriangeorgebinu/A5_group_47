package edu.gatech.cs6310.controller;

import edu.gatech.cs6310.CommandTypes;
import edu.gatech.cs6310.model.commandutils.UtilTypes;
import edu.gatech.cs6310.model.commandutils.basic.*;

import java.util.Map;


public class CommandUtilsFactory {

    private CommandUtilsFactoryConfiguration commandUtilsFactoryConfiguration;
    private static final String gatechUser = "kbinu3";

    public void executeCommand(CommandTypes commandType) {
        Map<UtilTypes, CommandUtils> commandUtilsMap = commandUtilsFactoryConfiguration.getCommandUtils();
        PropertySpecificCommandUtils propertySpecificCommandUtils = commandUtilsMap.get(UtilTypes.PROPERTY) instanceof PropertySpecificCommandUtils
                ? (PropertySpecificCommandUtils) commandUtilsMap.get(UtilTypes.PROPERTY) : null;
        OrderSpecificCommandUtils orderSpecificCommandUtils = commandUtilsMap.get(UtilTypes.ORDER) instanceof OrderSpecificCommandUtils
                ? (OrderSpecificCommandUtils) commandUtilsMap.get(UtilTypes.ORDER): null;
        UserSpecificCommandUtils userSpecificCommandUtils = commandUtilsMap.get(UtilTypes.USER) instanceof UserSpecificCommandUtils
                ? (UserSpecificCommandUtils) commandUtilsMap.get(UtilTypes.USER) : null;
        AssetSpecificCommandUtils assetSpecificCommandUtils = commandUtilsMap.get(UtilTypes.ASSET) instanceof AssetSpecificCommandUtils
                ? (AssetSpecificCommandUtils) commandUtilsMap.get(UtilTypes.ASSET) : null;
        if (commandType.equals(CommandTypes.MAKE_STORE) && propertySpecificCommandUtils!=null)
            propertySpecificCommandUtils.makeStore(commandUtilsFactoryConfiguration.getCommand(), commandUtilsFactoryConfiguration.getStores());
        else if (commandType.equals(CommandTypes.DISPLAY_STORES) && propertySpecificCommandUtils!=null)
            propertySpecificCommandUtils.displayStores(commandUtilsFactoryConfiguration.getCommand(), commandUtilsFactoryConfiguration.getStores());
        else if (commandType.equals(CommandTypes.SELL_ITEM) && assetSpecificCommandUtils!=null)
            assetSpecificCommandUtils.makeItem(commandUtilsFactoryConfiguration.getCommand(), commandUtilsFactoryConfiguration.getStores());
        else if (commandType.equals(CommandTypes.DISPLAY_ITEMS) && assetSpecificCommandUtils!=null)
            assetSpecificCommandUtils.displayItems(commandUtilsFactoryConfiguration.getCommand(), commandUtilsFactoryConfiguration.getStores());
        else if (commandType.equals(CommandTypes.MAKE_PILOT) && userSpecificCommandUtils!=null)
            userSpecificCommandUtils.makePilot(commandUtilsFactoryConfiguration.getCommand(), commandUtilsFactoryConfiguration.getDronePilots());
        else if (commandType.equals(CommandTypes.DISPLAY_PILOTS) && userSpecificCommandUtils!=null)
            userSpecificCommandUtils.displayPilot(commandUtilsFactoryConfiguration.getCommand(), commandUtilsFactoryConfiguration.getDronePilots());
        else if (commandType.equals(CommandTypes.MAKE_DRONE) && assetSpecificCommandUtils!=null)
            assetSpecificCommandUtils.makeDrone(commandUtilsFactoryConfiguration.getCommand(), commandUtilsFactoryConfiguration.getStores());
        else if (commandType.equals(CommandTypes.DISPLAY_DRONES) && assetSpecificCommandUtils!=null)
            assetSpecificCommandUtils.displayDrones(commandUtilsFactoryConfiguration.getCommand(), commandUtilsFactoryConfiguration.getStores());
        else if (commandType.equals(CommandTypes.FLY_DRONE) && assetSpecificCommandUtils!=null)
            assetSpecificCommandUtils.flyDrone(commandUtilsFactoryConfiguration.getCommand(), commandUtilsFactoryConfiguration.getStores(), commandUtilsFactoryConfiguration.getDronePilots());
        else if (commandType.equals(CommandTypes.MAKE_CUSTOMER) && userSpecificCommandUtils!=null)
            userSpecificCommandUtils.makeCustomer(commandUtilsFactoryConfiguration.getCommand(), commandUtilsFactoryConfiguration.getCustomers());
        else if (commandType.equals(CommandTypes.DISPLAY_CUSTOMERS) && userSpecificCommandUtils!=null)
            userSpecificCommandUtils.displayCustomer(commandUtilsFactoryConfiguration.getCommand(), commandUtilsFactoryConfiguration.getCustomers());
        else if (commandType.equals(CommandTypes.START_ORDER) && orderSpecificCommandUtils!=null)
            orderSpecificCommandUtils.makeOrder(commandUtilsFactoryConfiguration.getCommand(), commandUtilsFactoryConfiguration.getStores(), commandUtilsFactoryConfiguration.getCustomers());
        else if (commandType.equals(CommandTypes.DISPLAY_ORDERS) && orderSpecificCommandUtils!=null)
            orderSpecificCommandUtils.displayOrders(commandUtilsFactoryConfiguration.getCommand(), commandUtilsFactoryConfiguration.getStores());
        else if (commandType.equals(CommandTypes.REQUEST_ITEM) && orderSpecificCommandUtils!=null)
            orderSpecificCommandUtils.requestItemToOrder(commandUtilsFactoryConfiguration.getCommand(), commandUtilsFactoryConfiguration.getStores());
        else if (commandType.equals(CommandTypes.PURCHASE_ORDER) && orderSpecificCommandUtils!=null)
            orderSpecificCommandUtils.executePurchaseOrder(commandUtilsFactoryConfiguration.getCommand(), commandUtilsFactoryConfiguration.getStores());
        else if (commandType.equals(CommandTypes.CANCEL_ORDER) && orderSpecificCommandUtils!=null)
            orderSpecificCommandUtils.executeCancelOrder(commandUtilsFactoryConfiguration.getCommand(), commandUtilsFactoryConfiguration.getStores());
        else if(commandType.equals(CommandTypes.AUTHOR))
            System.out.println(gatechUser);
    }

    public void setCommandUtilsFactoryConfiguration(CommandUtilsFactoryConfiguration commandUtilsFactoryConfiguration) {
        this.commandUtilsFactoryConfiguration = commandUtilsFactoryConfiguration;
    }
}
