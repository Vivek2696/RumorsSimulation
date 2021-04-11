package com.uofl.cse622;

import java.util.Random;

public class Student {
    private static int idCount = 0;
    private int id;
    private boolean hasHeardRumor;
    private boolean stopSpreadingRumors;
    private boolean isPaired;
    private int numberOfTimesRumorHeard;

    public Student(){
        this.id = generateId();
        this.stopSpreadingRumors = false;
        this.hasHeardRumor = false;
        this.isPaired = false;
        this.numberOfTimesRumorHeard = 0;
    }

    private int generateId(){
        return idCount++;
    }

    public void setStopSpreadingRumors(boolean stopSpreadingRumors) {
        this.stopSpreadingRumors = stopSpreadingRumors;
    }

    public boolean isStopSpreadingRumors() {
        return stopSpreadingRumors;
    }

    public void setHasHeardRumor(boolean hasHeardRumor) {
        this.hasHeardRumor = hasHeardRumor;
    }

    public boolean hasHeardRumor() {
        return hasHeardRumor;
    }

    public boolean isPaired() {
        return isPaired;
    }

    public void setPaired(boolean paired) {
        isPaired = paired;
    }

    public void setNumberOfTimesRumorHeard(int numberOfTimesRumorHeard) {
        this.numberOfTimesRumorHeard = numberOfTimesRumorHeard;
    }

    public void increaseNumberOfTimesRumorHeard(){
        this.numberOfTimesRumorHeard++;
        if(numberOfTimesRumorHeard >=2){
            stopSpreadingRumors = true;
        }
    }

    public int getNumberOfTimesRumorHeard() {
        return numberOfTimesRumorHeard;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isGoingToSpreadRumor(){
        Random random = new Random();
        if(stopSpreadingRumors){
            return false;
        }
        else{
            return random.nextBoolean();
        }
    }

}
