package com.example.bglresidentprofiling;

public class BarDataChart {

    String address;
    int sales;

    public BarDataChart(String address, int sales){
        this.address = address;
        this.sales = sales;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getSales() {
        return sales;
    }

    public void setSales(int sales) {
        this.sales = sales;
    }
}
