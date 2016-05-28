package com.layer;

import com.properties.*;
import org.flightgear.fgfsclient.FGFSConnection;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by okubis on 5/17/16.
 */
public class Toolbox extends AbstractToolbox {
    private FGFSConnection fgfs;
    private HashMap<Property, AbstractPropertyManager> properties;
    private Process sim;

    public Toolbox(){
    }

    /**
     * method that will add plane to the sim. and set its initial position, speed etc.
     * @return  true if successful
     */
    @Override
    public boolean initFlight(SocketConnectionParameters socketParameters){
        try {
            //sim = new ProcessBuilder("fgfs",InitData.getFGFSArguments(socketParameters)).start();
            sim =  Runtime.getRuntime().exec("fgfs"+ InitData.getFGFSArguments(socketParameters));
            Thread.sleep(TimeConstants.FGFS_INIT_TIME_CONST);
        } catch (Exception e) {
            exceptionHandler(e);
        }

        // init connection
        boolean notYetInitialized = true;
        long t = System.currentTimeMillis();
        while (notYetInitialized) {
            try {
                fgfs = new FGFSConnection(socketParameters.getHost(), socketParameters.getPort());
                notYetInitialized = false;
            } catch (IOException ioe) {
                // SWALLOW EXCEPTION
                //exceptionHandler(ioe);
                //return false;
            }
        }
        initProperties();

        for(Property p : Property.values()){
            try {
                properties.get(p).retrieveValue(fgfs);
            } catch (IOException e) {
                exceptionHandler(e);
            }
        }
        return true;
    }

    private void initProperties() {
        this.properties = new HashMap();

        // add control managers
        addDoubleManager("/controls/flight/aileron",Property.AILERON);
        addDoubleManager("/controls/flight/elevator",Property.ELEVATOR);
        addDoubleManager("/controls/flight/rudder",Property.RUDDER);

        // add position data managers
        addDoubleManager("/position/latitude-deg",Property.LATITUDE);
        addDoubleManager("/position/longitude-deg",Property.LONGITUDE);
        addDoubleManager("/position/altitude-ft",Property.ALTITUDE);
        addDoubleManager("/velocities/airspeed-kt",Property.SPEED);

        // add orientations managers
        addDoubleManager("/orientation/roll-deg",Property.ROLL);
        addDoubleManager("/orientation/pitch-deg",Property.PITCH);
        addDoubleManager("/orientation/heading-deg",Property.YAW);
/*
        try {
            System.out.println("Altitude: " + properties.get(Property.ALTITUDE).retrieveValue(fgfs));

            System.out.println("Latitude: " + properties.get(Property.LATITUDE).retrieveValue(fgfs));
            System.out.println("Longitude: " + properties.get(Property.LONGITUDE).retrieveValue(fgfs));
            System.out.println("Speed: " + properties.get(Property.SPEED).retrieveValue(fgfs));

            System.out.println("Roll: " + properties.get(Property.ROLL).retrieveValue(fgfs));
            System.out.println("Pitch: " + properties.get(Property.PITCH).retrieveValue(fgfs));
            System.out.println("Yaw: " + properties.get(Property.YAW).retrieveValue(fgfs));

            System.out.println("controls: " + properties.get(Property.AILERON).retrieveValue(fgfs));
            System.out.println("controls: " + properties.get(Property.ELEVATOR).retrieveValue(fgfs));
            System.out.println("controls: " + properties.get(Property.RUDDER).retrieveValue(fgfs));

        } catch (IOException e) {
            e.printStackTrace();
        }
*/
        setAltitude(InitData.getInitialALTITUDE());
        setLatitude(InitData.getInitialLATITUDE());
        setLongitude(InitData.getInitialLONGITUDE());
     //   setSpeed(InitData.getInitialSPEED());

        setRoll(InitData.getInitialROLL());
        setPitch(InitData.getInitialPITCH());
        setYaw(InitData.getInitialYAW());

        setAileron(InitData.getInitialAILERON());
        setElevator(InitData.getInitialELEVATOR());
        setRudder(InitData.getInitialRUDDER());
    }

    private void addDoubleManager(String name, Property caption){
        DoublePropertyManager dpm = new DoublePropertyManager(name, caption);
        this.properties.put(caption,dpm);
    }
    private void addStringManager(String name, Property caption){
        StringPropertyManager dpm = new StringPropertyManager(name, caption);
        this.properties.put(caption,dpm);
    }
    private void addBooleanManager(String name, Property caption){
        BooleanPropertyManager dpm = new BooleanPropertyManager(name, caption);
        this.properties.put(caption,dpm);
    }

    /**
     * method that will remove the plane from the sim.
     * @return  true if successful
     */
    @Override
    public boolean endFlight(){
        // end the connection
        try {
            fgfs.close();
        } catch (IOException ioe) {
            exceptionHandler(ioe);
            return false;
        }

        sim.destroy();
        return true;
    }

    /**
     * method for fitness computation
     * @param altitude - altitude at position we compute penalty for
     * @param longitude - longitude at position we compute penalty for
     * @param latitude - latitude at position we compute penalty for
     * @return penalty (a.k.a.: inverse fitness)
     */
    @Override
    public double getPenalty(double altitude, double longitude, double latitude){
        // let us consider a line given by a point (initialAltitude, initialLongitude, initialLatitude) and vector (altitudeVector, longitudeVector, latitudeVector)
        // We want to calculate the distance between the line and point (altitude, longitude, latitude)
        double initialAltitude = InitData.getInitialALTITUDE();
        double initialLongitude = InitData.getInitialLONGITUDE();
        double initialLatitude = InitData.getInitialLATITUDE();
        double altitudeVector = InitData.getAltitudeVector();
        double longitudeVector = InitData.getLongitudeVector();
        double latitudeVector = InitData.getLatitudeVector();
        double x1 = altitude - initialAltitude;
        double y1 = longitude - initialLongitude;
        double z1 = latitude - initialLatitude;
        double t = ( altitudeVector*x1 + longitudeVector*y1 + latitudeVector*z1 ) / (Math.pow(altitudeVector, 2) + Math.pow(longitudeVector, 2) + Math.pow(latitudeVector, 2));
        return Math.sqrt(Math.pow(t*altitudeVector - x1, 2) + Math.pow(t*longitudeVector - y1, 2) + Math.pow(t*latitudeVector - z1, 2));
    }

    /**
     *  method that returns current altitude
     * @return current Altitude
     */
    @Override
    public double getAltitude(){
        return getDoubleProperty(Property.ALTITUDE);
    }

    private void setAltitude(double value){
        setDoubleProperty(Property.ALTITUDE,value);
    }

    /**
     *  method that returns current Latitude
     * @return current Latitude
     */
    @Override
    public double getLatitude(){
        return getDoubleProperty(Property.LATITUDE);
    }

    private void setLatitude(double value){
        setDoubleProperty(Property.LATITUDE,value);
    }

    /**
     *  method that returns current Longitude
     * @return current Longitude
     */
    @Override
    public double getLongitude(){
        return getDoubleProperty(Property.LONGITUDE);
    }

    private void setLongitude(double value){
        setDoubleProperty(Property.LONGITUDE,value);
    }

    /**
     *  method that returns current Roll
     * @return current Roll
     */
    @Override
    public double getRoll(){
        return getDoubleProperty(Property.ROLL);
    }

    private void setRoll(double value){
        setDoubleProperty(Property.ROLL,value);
    }

    /**
     *  method that returns current Pitch
     * @return current Pitch
     */
    @Override
    public double getPitch(){
        return getDoubleProperty(Property.PITCH);
    }

    private void setPitch(double value){
        setDoubleProperty(Property.PITCH,value);
    }

    /**
     *  method that returns current Yaw
     * @return current Yaw
     */
    @Override
    public double getYaw(){
        return getDoubleProperty(Property.YAW);
    }

    private void setYaw(double value){
        setDoubleProperty(Property.YAW,value);
    }

    private void setSpeed(double value){
        setDoubleProperty(Property.SPEED,value);
    }

    /**
     * Method that returns current status of Aileron
     *
     * @return current status of Aileron
     */
    @Override
    public double getAileronStatus(){
        return getDoubleProperty(Property.AILERON);
    }

    /**
     * Method that returns current status of Elevators
     *
     * @return current status of Elevators
     */
    @Override
    public double getElevatorStatus() {
        return getDoubleProperty(Property.ELEVATOR);
    }

    /**
     * Method that returns current status of Rudder
     *
     * @return current status of Rudder
     */
    @Override
    public double getRudderStatus() {
        return getDoubleProperty(Property.RUDDER);
    }

    /**
     *  method that controls ROLL by setting value for ailerons
     */
    @Override
    public void setAileron(double aileronInput){
        setDoubleProperty(Property.AILERON , aileronInput);
    }

    /**
     *  method that controls PITCH by setting value for elevators
     */
    @Override
    public void setElevator(double elevatorInput){
        setDoubleProperty(Property.ELEVATOR , elevatorInput);
    }

    /**
     *  method that controls YAW by setting value for rudder
     */
    @Override
    public void setRudder(double rudderInput){
        setDoubleProperty(Property.RUDDER , rudderInput);
    }

    private double getDoubleProperty(Property p){
        DoublePropertyManager dpm = (DoublePropertyManager) properties.get(p);
        Double value = null;
        try {
            value = dpm.retrieveValue(fgfs);
        } catch (IOException e) {
            exceptionHandler(e);
        }
        return value;
    }

    private void setDoubleProperty(Property p , double value){
        DoublePropertyManager dpm = (DoublePropertyManager) properties.get(p);
        try {
            dpm.updateValue(fgfs,value);
        } catch (IOException e) {
            exceptionHandler(e);
        }
    }

    private String getStringProperty(Property p){
        StringPropertyManager spm = (StringPropertyManager) properties.get(p);
        String value = null;
        try {
            value = spm.retrieveValue(fgfs);
        } catch (IOException e) {
            exceptionHandler(e);
        }
        return value;
    }

    private void setStringProperty(Property p , String value){
        StringPropertyManager spm = (StringPropertyManager) properties.get(p);
        try {
            spm.updateValue(fgfs,value);
        } catch (IOException e) {
            exceptionHandler(e);
        }
    }

    private Boolean getBooleanProperty(Property p){

        BooleanPropertyManager bpm = (BooleanPropertyManager) properties.get(p);
        Boolean value = null;
        try {
            value = bpm.retrieveValue(fgfs);
        } catch (IOException e) {
            exceptionHandler(e);
        }
        return value;
    }

    private void setBooleanProperty(Property p , Boolean value){
        BooleanPropertyManager bpm = (BooleanPropertyManager) properties.get(p);
        try {
            bpm.updateValue(fgfs,value);
        } catch (IOException e) {
            exceptionHandler(e);
        }
    }

    private static synchronized void exceptionHandler(Exception e){
        System.err.println(e.getMessage());
        //System.err.println(e.getCause());
        e.printStackTrace();
        System.err.println("---------------------------------");
    }

}
