package com.example.simpletimerapp;

import android.util.Xml;

import java.util.List;

public class Timer {
    String name;
    List<Step> stepList;

    public Timer(String name) {
        this.name = name;
        //TODO: Access db and get steps? Or do that when getting data in main and pass to timer;
    }

    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }

}
