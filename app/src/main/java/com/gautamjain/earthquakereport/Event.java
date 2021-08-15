package com.gautamjain.earthquakereport;

public class Event {

    private double mMagnitude;
    private String mLocation;
    private long  mTimeInMilliseconds;
    private String mUrl;

    public Event(double magnitude, String location, long timeInMilliseconds, String url) {
        mMagnitude = magnitude;
        mLocation = location;
        mTimeInMilliseconds = timeInMilliseconds;
        mUrl = url;
    }

    public double getMagnitude() { return mMagnitude; }

    public String getLocation() { return mLocation; }

    public long getTimeInMilliseconds() { return mTimeInMilliseconds; }

    public String getUrl() { return mUrl; }
}
