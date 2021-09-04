package com.pega.stockapp.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Stock implements Parcelable {
    private String companySymbol, companyName;
    private double currentStockPrice, dailyLowPrice, dailyHighPrice;

    public Stock() {

    }

    public Stock(String companySymbol, String companyName, double currentStockPrice, double dailyLowPrice, double dailyHighPrice) {
        this.companySymbol = companySymbol;
        this.companyName = companyName;
        this.currentStockPrice = currentStockPrice;
        this.dailyLowPrice = dailyLowPrice;
        this.dailyHighPrice = dailyHighPrice;
    }

    protected Stock(Parcel in) {
        companySymbol = in.readString();
        companyName = in.readString();
        currentStockPrice = in.readDouble();
        dailyLowPrice = in.readDouble();
        dailyHighPrice = in.readDouble();
    }

    public static final Creator<Stock> CREATOR = new Creator<Stock>() {
        @Override
        public Stock createFromParcel(Parcel in) {
            return new Stock(in);
        }

        @Override
        public Stock[] newArray(int size) {
            return new Stock[size];
        }
    };


    public String getCompanySymbol() {
        return companySymbol;
    }

    public void setCompanySymbol(String companySymbol) {
        this.companySymbol = companySymbol;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public double getCurrentStockPrice() {
        return currentStockPrice;
    }

    public void setCurrentStockPrice(double currentStockPrice) {
        this.currentStockPrice = currentStockPrice;
    }

    public double getDailyLowPrice() {
        return dailyLowPrice;
    }

    public void setDailyLowPrice(double dailyLowPrice) {
        this.dailyLowPrice = dailyLowPrice;
    }

    public double getDailyHighPrice() {
        return dailyHighPrice;
    }

    public void setDailyHighPrice(double dailyHighPrice) {
        this.dailyHighPrice = dailyHighPrice;
    }

    public static Creator<Stock> getCREATOR() {
        return CREATOR;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(companySymbol);
        parcel.writeString(companyName);
        parcel.writeDouble(currentStockPrice);
        parcel.writeDouble(getDailyLowPrice());
        parcel.writeDouble(getDailyHighPrice());
    }
}
