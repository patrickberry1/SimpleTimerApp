package com.example.simpletimerapp;

import java.io.Serializable;

public class Step implements Serializable {
    int minutes, seconds, reps, rest;
    String title;

    public Step(int minutes, int seconds, String title, int reps, int rest) {
        this.title = title;
        this.minutes = minutes;
        this.seconds = seconds;
        this.reps = reps;
        this.rest = rest;
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

    public int getReps() { return reps; }

    public int getRest() { return rest; }

    public void setReps(int reps) { this.reps = reps; }

    public void setRest(int rest) { this.rest = rest; }
}
