package com.example.simpletimerapp;

import android.util.Xml;

import java.io.Serializable;
import java.util.List;

public class Timer implements Serializable {
    String name;
    List<Step> stepList;

    public Timer(String name, List<Step> steps) {
        this.name = name;
        stepList = steps;
    }

    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }

}
