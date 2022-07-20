package edu.gatech.cs6310.model;

import edu.gatech.cs6310.model.commandutils.basic.LocationType;

import java.util.ArrayList;
import java.util.List;

public class Location {

    private double probCollision;
    private LocationType locationType;
    private List<AngryBird> angryBirds;

    public Location(double probCollision, LocationType locationType) {
        this.probCollision = probCollision;
        this.locationType = locationType;
    }

    public double getProbCollision() {
        return this.probCollision;
    }

    public LocationType getLocationType() {
        return this.locationType;
    }

    public List<AngryBird> getAngryBirds() {
        return this.angryBirds;
    }

    public void setProbCollision(double probCollision) {
        this.probCollision = probCollision;
    }

    public void setLocationType(LocationType locationType) {
        this.locationType = locationType;
    }

    public void setAngryBirds(List<AngryBird> angryBirds) {
        this.angryBirds = angryBirds;
    }

    public void addAngryBirdToLocation(AngryBird angryBird) {
        if (angryBirds == null) {
            angryBirds = new ArrayList<>();
        }
        angryBirds.add(angryBird);
    }

    public int getNumAngryBirds() {
        return this.angryBirds.size();
    }
}
