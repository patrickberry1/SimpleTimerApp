package com.example.simpletimerapp;

public class Step {
    int minutes, seconds, reps;

    public Step(int minutes, int seconds, int reps) {
        this.minutes = minutes;
        this.seconds = seconds;
        this.reps = reps;
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

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }
}
