package com.project.pro112.hydrateam.thepolycoffee.models;

/**
 * Created by Huyn_TQT on 12/3/2017.
 */

public class UserRank {
    private double totalMoney;
    private int numOfStart;
    private String nameRank;

    public UserRank(double totalMoney, int numOfStart, String nameRank) {
        this.totalMoney = totalMoney;
        this.numOfStart = numOfStart;
        this.nameRank = nameRank;
    }

    public UserRank() {
    }

    public double getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(double totalMoney) {
        this.totalMoney = totalMoney;
    }

    public int getNumOfStart() {
        return numOfStart;
    }

    public void setNumOfStart(int numOfStart) {
        this.numOfStart = numOfStart;
    }

    public String getNameRank() {
        return nameRank;
    }

    public void setNameRank(String nameRank) {
        this.nameRank = nameRank;
    }
}
