package com.example.btlandroid_covid19.model;

public class AllStatusByCountry {
    private String Country;
    private long Confirmed;
    private long Deaths;
    private long Recovered;
    private long Active;
    private String Date;

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public long getConfirmed() {
        return Confirmed;
    }

    public void setConfirmed(long confirmed) {
        Confirmed = confirmed;
    }

    public long getDeaths() {
        return Deaths;
    }

    public void setDeaths(long deaths) {
        Deaths = deaths;
    }

    public long getRecovered() {
        return Recovered;
    }

    public void setRecovered(long recovered) {
        Recovered = recovered;
    }

    public long getActive() {
        return Active;
    }

    public void setActive(long active) {
        Active = active;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }
}
