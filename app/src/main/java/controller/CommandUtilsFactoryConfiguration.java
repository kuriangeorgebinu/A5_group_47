package controller;

import model.Store;
import model.commandutils.UtilTypes;
import model.commandutils.basic.*;
import model.users.Customer;
import model.users.DronePilots;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandUtilsFactoryConfiguration {

    private String command;

    private List<Store> stores;

    private List<DronePilots> dronePilots;

    private List<Customer> customers;

    private Map<UtilTypes, CommandUtils> commandUtils;

    public CommandUtilsFactoryConfiguration() {
        stores = new ArrayList<>();
        dronePilots = new ArrayList<>();
        customers = new ArrayList<>();
        commandUtils = new HashMap<>();
        commandUtils.put(UtilTypes.USER, new UserSpecificCommandUtils());
        commandUtils.put(UtilTypes.PROPERTY, new PropertySpecificCommandUtils());
        commandUtils.put(UtilTypes.ASSET, new AssetSpecificCommandUtils());
        commandUtils.put(UtilTypes.ORDER,  new OrderSpecificCommandUtils());
        commandUtils.put(UtilTypes.ANGRYBIRDS, new AngryBirdsSpecificCommandUtils());
    }

    public List<DronePilots> getDronePilots() {
        return dronePilots;
    }

    public List<Store> getStores() {
        return stores;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public String getCommand() {
        return this.command;
    }

    public Map<UtilTypes, CommandUtils> getCommandUtils() {
        return commandUtils;
    }

    public void setStores(List<Store> stores) {
        this.stores = stores;
    }

    public void setDronePilots(List<DronePilots> dronePilots) {
        this.dronePilots = dronePilots;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

}
