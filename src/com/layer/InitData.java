package com.layer;

/**
 * Created by okubis on 5/17/16.
 */
public class InitData {
    // TODO: determine these values
    private static final double ALTITUDE = 0.0;
    private static final double LONGITUDE = 0.0;
    private static final double LATITUDE = 0.0;
    private static final double ROLL = 0.0;
    private static final double PITCH = 0.0;
    private static final double HEADING = 0.0;
    private static final double SPEED = 0.0;
    private static final double YAW = 0.0;

    public static double getSPEED() {
        return SPEED;
    }

    public static double getYAW() {
        return YAW;
    }

    public static synchronized double getALTITUDE() {
        return ALTITUDE;
    }

    public static synchronized double getLONGITUDE() {
        return LONGITUDE;
    }

    public static synchronized double getLATITUDE() {
        return LATITUDE;
    }

    public static synchronized double getROLL() {
        return ROLL;
    }

    public static synchronized double getPITCH() {
        return PITCH;
    }

    public static synchronized double getHEADING() {
        return HEADING;
    }
}
