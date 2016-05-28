package com.layer;

/**
 *
 * Simple class for initial constant values
 *
 * Created by okubis on 5/17/16.
 */
public class InitData {
    private static final double ALTITUDE = 2000.0;
    private static final double LATITUDE = 37.59850793;
    private static final double LONGITUDE = -122.3214854;
    private static final double SPEED = 80.0;

    private static final double ROLL = 0.0;
    private static final double PITCH = 0.0; // we are not changing flight level
    private static final double YAW = 270.0; // we are heading west

    private static final double AILERON = 0.0;
    private static final double ELEVATOR = 0.0;
    private static final double RUDDER = 0.0;
    private static final double THROTTLE=0.7;
    private static final int ENGINE_RPMS=3000;

    // since we are heading west, the initial vector is (alt,lon,lat) = (0.0,-1.0,0.0)
    private static final double ALTITUDE_VECTOR = 0.0;
    private static final double LONGITUDE_VECTOR = -1.0;
    private static final double LATITUDE_VECTOR = 0.0;
    private static final double DISTANCE_OFFSET = 5;

    // CONNECTION
    private static final int Hz = 100;


    protected static double getInitialALTITUDE() {
        return ALTITUDE;
    }
    protected static double getInitialLATITUDE() {
        return LATITUDE;
    }
    protected static double getInitialLONGITUDE() {
        return LONGITUDE;
    }
    protected static double getInitialSPEED() {
        return SPEED;
    }

    protected static double getInitialROLL() {
        return ROLL;
    }
    protected static double getInitialPITCH() {
        return PITCH;
    }
    protected static double getInitialYAW() {
        return YAW;
    }

    protected static double getInitialAILERON() { return AILERON; }
    protected static double getInitialELEVATOR() { return ELEVATOR; }
    protected static double getInitialRUDDER() { return RUDDER; }

    protected static double getAltitudeVector() { return ALTITUDE_VECTOR; }
    protected static double getLongitudeVector() { return LONGITUDE_VECTOR; }
    protected static double getLatitudeVector() { return LATITUDE_VECTOR; }

    protected static synchronized String getFGFSArguments(SocketConnectionParameters scp){
        StringBuilder sb = new StringBuilder();
        sb.append(" --timeofday=noon");
        sb.append(" --altitude="+ALTITUDE);
        sb.append(" --lat="+LATITUDE);
        sb.append(" --lon="+LONGITUDE);
        sb.append(" --prop:/controls/engines/engine/throttle="+THROTTLE);
        sb.append(" --prop:/engines/engine[0]/rpm="+ENGINE_RPMS);
        sb.append(" --vc="+SPEED);
        sb.append(" --offset-distance="+DISTANCE_OFFSET);
        sb.append(" --offset-azimuth="+YAW);
        sb.append(" --disable-sound");
        // --telnet=socket,bi,100,127.0.0.1,6789,tcp
        sb.append(" --telnet=socket,bi,"+Hz+","+scp.getHost()+","+scp.getPort()+",tcp");
        return sb.toString();
    }
}
