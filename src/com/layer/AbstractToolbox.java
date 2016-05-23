package com.layer;

/**
 * Created by okubis on 5/23/16.
 */
public abstract class AbstractToolbox {
    /**
     * method that will add plane to the sim. and set its initial position, speed etc.
     * @return  true if successful
     */
    public abstract boolean initFlight(SocketConnectionParameters socketParameters);

    /**
     * method that will remove the plane from the sim.
     * @return  true if successful
     */
    public abstract boolean endFlight();

    /**
     * method for fitness computation
     * @param altitude - altitude at position we compute penalty for
     * @param longitude - longitude at position we compute penalty for
     * @param latitude - latitude at position we compute penalty for
     * @return penalty (a.k.a.: inverse fitness)
     */
    public abstract double getPenalty(double altitude, double longitude, double latitude);

    /**
     *  method that returns current altitude
     * @return current Altitude
     */
    public abstract double getAltitude();

    /**
     *  method that returns current Latitude
     * @return current Latitude
     */
    public abstract double getLatitude();

    /**
     *  method that returns current Longitude
     * @return current Longitude
     */
    public abstract double getLongitude();

    /**
     *  method that returns current Roll
     * @return current Roll
     */
    public abstract double getRoll();

    /**
     *  method that returns current Pitch
     * @return current Pitch
     */
    public abstract double getPitch();

    /**
     *  method that returns current Yaw
     * @return current Yaw
     */
    public abstract double getYaw();

    /**
     * Method that returns current status of Aileron
     *
     * @return current status of Aileron
     */
    public abstract double getAileronStatus();

    /**
     * Method that returns current status of Elevators
     *
     * @return current status of Elevators
     */
    public abstract double getElevatorsStatus();

    /**
     * Method that returns current status of Rudder
     *
     * @return current status of Rudder
     */
    public abstract double getRudderStatus();

    /**
     *  method that controls ROLL by setting value for ailerons
     */
    public abstract void setAileron(double aileronInput);

    /**
     *  method that controls PITCH by setting value for elevators
     */
    public abstract void setElevators(double elevatorsInput);

    /**
     *  method that controls YAW by setting value for rudder
     */
    public abstract void setRudder(double rudderInput);
}
