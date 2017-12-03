package com.project.pro112.hydrateam.thepolycoffee.models;

/**
 * Created by VI on 03/12/2017.
 */

public class DateAndTotal {
    private String dateNe;
    private String keyOrder;

    public String getDateNe() {
        return dateNe;
    }

    public void setDateNe(String dateNe) {
        this.dateNe = dateNe;
    }

    public String getKeyOrder() {
        return keyOrder;
    }

    public void setKeyOrder(String keyOrder) {
        this.keyOrder = keyOrder;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public DateAndTotal(String dateNe, String keyOrder, double total) {
        this.dateNe = dateNe;
        this.keyOrder = keyOrder;
        this.total = total;
    }

    public DateAndTotal() {
    }

    private double total;

}
