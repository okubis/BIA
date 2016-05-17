package com.layer;

/**
 * Created by okubis on 5/17/16.
 */
public class Toolbox {


    /**
     * method that will add plane to the sim. and set its initial position, speed etc.
     * @return  true if successful
     */
    public boolean initFlight(){
        // TODO: Implement
        return false;
    }

    /**
     * method that will remove the plane from the sim.
     * @return  true if successful
     */
    public boolean endFlight(){
        // TODO: Implement
        return false;
    }

    /**
     * method for fitness computation
     * @param altitude - altitude at position we compute penalty for
     * @param longitude - longitude at position we compute penalty for
     * @param latitude - latitude at position we compute penalty for
     * @return penalty (a.k.a.: inverse fitness)
     */
    public double getPenalty(double altitude, double longitude, double latitude){
        //TODO: implement , EDAs task :)
        return 0.0;
    }

    /**
     *  method that returns current altitude
     * @return current Altitude
     */
    public double getAltitude(){
        // TODO: Implement
        return 0.0;
    }

    /**
     *  method that returns current Latitude
     * @return current Latitude
     */
    public double getLatitude(){
        // TODO: Implement
        return 0.0;
    }

    /**
     *  method that returns current Longitude
     * @return current Longitude
     */
    public double getLongitude(){
        // TODO: Implement
        return 0.0;
    }

    /**
     *  method that returns current Roll
     * @return current Roll
     */
    public double getRoll(){
        // TODO: Implement
        return 0.0;
    }

    /**
     *  method that returns current Pitch
     * @return current Pitch
     */
    public double getPitch(){
        // TODO: Implement
        return 0.0;
    }

    /**
     *  method that returns current Yaw
     * @return current Yaw
     */
    public double getYaw(){
        // TODO: Implement
        return 0.0;
    }

    /**
     *  method that controls ROLL by setting value for ailerons
     */
    public void setAileron(double aileronInput){
        // TODO: Implement
    }

    /**
     *  method that controls PITCH by setting value for elevators
     */
    public void setElevators(double elevatorsInput){
        // TODO: Implement
    }

    /**
     *  method that controls YAW by setting value for rudder
     */
    public void setRudder(double rudderInput){
        // TODO: Implement
    }

}
