package edu.gatech.cs6310.model.users;


import edu.gatech.cs6310.model.Drone;

public class DronePilots extends Employee {

    //primary attr
    private String licenseId;

    private int numDeliveries;

    //association
    private Drone drone;

    public DronePilots(String account, String firstName, String lastName, String phoneNumber, String ssnNumber, int numMonthsWorked, String licenseId, int numDeliveries) {
        super(account, firstName, lastName, phoneNumber, ssnNumber, numMonthsWorked);
        this.licenseId = licenseId;
        this.numDeliveries = numDeliveries;
    }

    public DronePilots(String account, String firstName, String lastName, String phoneNumber, String ssnNumber, String licenseId, int numDeliveries) {
        super(account, firstName, lastName, phoneNumber, ssnNumber);
        this.licenseId = licenseId;
        this.numDeliveries = numDeliveries;
    }

    //Getters
    public int getNumDeliveries() {
        return numDeliveries;
    }

    public String getLicenseId() {
        return licenseId;
    }

    public Drone getDrone(){
        return this.drone;
    }

    //Setters
    public void setNumDeliveries(int numDeliveries) {
        this.numDeliveries = numDeliveries;
    }

    public void setLicenseId(String licenseId) {
        this.licenseId = licenseId;
    }

    public void setDrone(Drone drone) {
        this.drone = drone;
    }
}
