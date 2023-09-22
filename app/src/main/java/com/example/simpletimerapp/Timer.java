package com.example.simpletimerapp;

import android.util.Xml;

import java.io.Serializable;
import java.util.List;

public class Timer implements Serializable {
    int id;
    String name;
    List<Step> stepList;

    public Timer(String name, List<Step> steps, int id) {
        this.name = name;
        this.id = id;
        this.stepList = steps;
    }

    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }

}
