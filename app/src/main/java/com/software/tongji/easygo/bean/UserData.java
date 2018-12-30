package com.software.tongji.easygo.bean;

public class UserData {
    private String user;
    private String token;
    private int journalCount;
    private int tourCount;

    public int getJournalCount() {
        return journalCount;
    }

    public void setJournalCount(int journalCount) {
        this.journalCount = journalCount;
    }

    public int getTourCount() {
        return tourCount;
    }

    public void setTourCount(int tourCount) {
        this.tourCount = tourCount;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
