package com.example.viewpagerfragment;

public class DataHolder {
    private static DataHolder instance = new DataHolder();
    private String sharedValue;

    private DataHolder() {}

    public static DataHolder getInstance() {
        return instance;
    }

    public String getSharedValue() {
        return sharedValue;
    }

    public void setSharedValue(String value) {
        sharedValue = value;
    }
}
