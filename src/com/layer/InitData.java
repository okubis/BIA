package com.layer;

/**
 *
 * Simple class for initial constant values
 *
 * Created by okubis on 5/17/16.
 */
public class InitData {
    //NOTE:
    private static final double ALTITUDE = 0.0;
    private static final double LONGITUDE = 0.0;
    private static final double LATITUDE = 0.0;
    private static final double ROLL = 0.0;
    private static final double PITCH = 0.0;
    private static final double HEADING = 0.0;
    private static final double SPEED = 0.0;
    private static final double YAW = 0.0;

    protected static double getInitialSPEED() {
        return SPEED;
    }

    protected static double getInitialYAW() {
        return YAW;
    }

    protected static double getInitialALTITUDE() {
        return ALTITUDE;
    }

    protected static double getInitialLONGITUDE() {
        return LONGITUDE;
    }

    protected static double getInitialLATITUDE() {
        return LATITUDE;
    }

    protected static double getInitialROLL() {
        return ROLL;
    }

    protected static double getInitialPITCH() {
        return PITCH;
    }

    protected static double getInitialHEADING() {
        return HEADING;
    }
}
