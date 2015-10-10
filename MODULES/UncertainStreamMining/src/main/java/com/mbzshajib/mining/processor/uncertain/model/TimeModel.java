package com.mbzshajib.mining.processor.uncertain.model;

/**
 * *****************************************************************
 *
 * @author - Md. Badi-Uz-Zaman Shajib
 * @copyright 2015.
 * @email - mbzshajib@gmail.com
 * @gitHub - https://github.com/mbzshajib
 * @date: 10/10/2015
 * @time: 6:18 PM
 * ****************************************************************
 */

public class TimeModel {
    private long startTimeInMilis;
    private long endTimeInMilis;
    private long timeNeeded;

    public TimeModel(long timeNeeded) {
        this.timeNeeded = timeNeeded;
    }

    public TimeModel(long startTimeInMilis, long endTimeInMilis) {
        this.startTimeInMilis = startTimeInMilis;
        this.endTimeInMilis = endTimeInMilis;
        this.timeNeeded = this.endTimeInMilis - this.startTimeInMilis;
    }

    public long getStartTimeInMilis() {
        return startTimeInMilis;
    }

    public void setStartTimeInMilis(long startTimeInMilis) {
        this.startTimeInMilis = startTimeInMilis;
    }

    public long getEndTimeInMilis() {
        return endTimeInMilis;
    }

    public void setEndTimeInMilis(long endTimeInMilis) {
        this.endTimeInMilis = endTimeInMilis;
    }

    public long getTimeNeeded() {
        return timeNeeded;
    }

    public void setTimeNeeded(long timeNeeded) {
        this.timeNeeded = timeNeeded;
    }

    @Override
    public String toString() {
        return "TimeModel{" +
                "startTimeInMilis=" + startTimeInMilis +
                ", endTimeInMilis=" + endTimeInMilis +
                ", timeNeeded=" + timeNeeded +
                '}';
    }
}
