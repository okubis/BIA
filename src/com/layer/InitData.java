package com.layer;

/**
 *
 * Simple class for initial constant values
 *
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

    public static double getInitialSPEED() {
        return SPEED;
    }

    public static double getInitialYAW() {
        return YAW;
    }

    public static double getInitialALTITUDE() {
        return ALTITUDE;
    }

    public static double getInitialLONGITUDE() {
        return LONGITUDE;
    }

    public static double getInitialLATITUDE() {
        return LATITUDE;
    }

    public static double getInitialROLL() {
        return ROLL;
    }

    public static double getInitialPITCH() {
        return PITCH;
    }

    public static double getInitialHEADING() {
        return HEADING;
    }
}
