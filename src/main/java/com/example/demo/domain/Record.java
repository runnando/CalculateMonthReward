package com.example.demo.domain;

import java.time.LocalDate;
import java.util.HashMap;

public class Record {
    private String userId;
    private String name;
    private HashMap<Integer,String> rewardPerMonth = new HashMap<>();
    private String totalamount;

    public Record(){

    }

    public Record(String userId, String name, HashMap<Integer, String> rewardPerMonth, String totalamount) {
        this.userId = userId;
        this.name = name;
        this.rewardPerMonth = rewardPerMonth;
        this.totalamount = totalamount;
    }

    public Record(String name, HashMap<Integer, String> rewardPerMonth, String totalamount) {
        this.name = name;
        this.rewardPerMonth = rewardPerMonth;
        this.totalamount = totalamount;
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public HashMap<Integer, String> getRewardPerMonth() {
        return rewardPerMonth;
    }

    public String getTotalamount() {
        return totalamount;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRewardPerMonth(HashMap<Integer, String> rewardPerMonth) {
        this.rewardPerMonth = rewardPerMonth;
    }

    public void setTotalamount(String totalamount) {
        this.totalamount = totalamount;
    }

    @Override
    public String toString() {
        return "Record{" +
                "userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                ", rewardPerMonth=" + rewardPerMonth +
                ", totalamount=" + totalamount +
                '}';
    }
}
