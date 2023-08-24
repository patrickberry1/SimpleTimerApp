package com.example.simpletimerapp;

import java.io.Serializable;

public class Step implements Serializable {
    int minutes, seconds;
    String title;

    public Step(int minutes, int seconds, String title) {
        this.title = title;
        this.minutes = minutes;
        this.seconds = seconds;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }
}
