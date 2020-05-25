package com.example.btlandroid_covid19.model;

public class DiaPhuongVN {
    private String locationName;
    private String caNhiem;
    private String tuVong;
    private String binhPhuc;

    public DiaPhuongVN(String locationName, String caNhiem, String tuVong, String binhPhuc) {
        this.locationName = locationName;
        this.caNhiem = caNhiem;
        this.tuVong = tuVong;
        this.binhPhuc = binhPhuc;
    }

    public DiaPhuongVN() {
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getCaNhiem() {
        return caNhiem;
    }

    public void setCaNhiem(String caNhiem) {
        this.caNhiem = caNhiem;
    }

    public String getTuVong() {
        return tuVong;
    }

    public void setTuVong(String tuVong) {
        this.tuVong = tuVong;
    }

    public String getBinhPhuc() {
        return binhPhuc;
    }

    public void setBinhPhuc(String binhPhuc) {
        this.binhPhuc = binhPhuc;
    }
}
