package model.users;

import model.Store;

public class Employee extends Users {

    private String ssnNumber;

    private int numMonthsWorked;

    private Store store;

    public Employee(String account, String firstName, String lastName, String phoneNumber, String ssnNumber, int numMonthsWorked) {
        super(account, firstName, lastName, phoneNumber);
        this.ssnNumber = ssnNumber;
        this.numMonthsWorked = numMonthsWorked;
        this.store = store;
    }

    public Employee(String account, String firstName, String lastName, String phoneNumber, String ssnNumber) {
        super(account, firstName, lastName, phoneNumber);
        this.ssnNumber = ssnNumber;
        this.numMonthsWorked = 0;
    }

    public String getSsnNumber() {
        return ssnNumber;
    }

    public int getNumMonthsWorked() {
        return numMonthsWorked;
    }

    public Store getStore() {
        return store;
    }

    public void setSsnNumber(String ssnNumber) {
        this.ssnNumber = ssnNumber;
    }

    public void setNumMonthsWorked(int numMonthsWorked) {
        this.numMonthsWorked = numMonthsWorked;
    }

    public void setStore(Store store) {
        this.store = store;
    }
}
