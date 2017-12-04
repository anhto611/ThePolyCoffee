package com.project.pro112.hydrateam.thepolycoffee.models;

/**
 * Created by Huyn_TQT on 12/3/2017.
 */

public class UserRank {
    private double totalMoney;
    private int numOfSrart;
    private String nameRank;

    public UserRank(double totalMoney, int numOfSrart, String nameRank) {
        this.totalMoney = totalMoney;
        this.numOfSrart = numOfSrart;
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

    public int getNumOfSrart() {
        return numOfSrart;
    }

    public void setNumOfSrart(int numOfSrart) {
        this.numOfSrart = numOfSrart;
    }

    public String getNameRank() {
        return nameRank;
    }

    public void setNameRank(String nameRank) {
        this.nameRank = nameRank;
    }
}
