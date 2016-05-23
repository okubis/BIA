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



    /**
     * method that will add plane to the sim. and set its initial position, speed etc.
     * @return  true if successful
     */
    @Override
    public boolean initFlight(SocketConnectionParameters socketParameters){
        // init connection
        try {
            fgfs = new FGFSConnection(socketParameters.getHost(), socketParameters.getPort());
        }catch(IOException ioe){
            System.err.println(ioe.getMessage());
            System.err.println(ioe.getCause());
            ioe.printStackTrace();
            return false;
        }
        initProperties();

        for(Property p : Property.values()){
            try {
                properties.get(p).retrieveValue(fgfs);
            } catch (IOException e) {
                //TODO: DETERMINE HOW TO HADLE THIS EXCEPTION
                System.err.println(e.getMessage());
                e.printStackTrace();
                return false;
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

        // add orientations managers
        addDoubleManager("/orientation/roll-deg",Property.ROLL);
        addDoubleManager("/orientation/pitch-deg",Property.PITCH);
        addDoubleManager("/orientation/heading-deg",Property.YAW);
    }

    private void addDoubleManager(String name, Property caption){
        DoublePropertyManager dpm = new DoublePropertyManager(name, caption);
        this.properties.put(caption,dpm);
    }

    /**
     * method that will remove the plane from the sim.
     * @return  true if successful
     */
    @Override
    public boolean endFlight(){
        // TODO: Implement the flight ending in the flight gear so that the plane does not exist

        // end the connection
        try {
            //TODO: IF CONNECTION IS TO BE REUSED DO NOT CLOSE IT...
            fgfs.close();
        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
            System.err.println(ioe.getCause());
            ioe.printStackTrace();
            return false;
        }
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
        //TODO: implement , EDAs task :)
        return 0.0;
    }

    /**
     *  method that returns current altitude
     * @return current Altitude
     */
    @Override
    public double getAltitude(){
        return getDoubleProperty(Property.ALTITUDE);
    }

    /**
     *  method that returns current Latitude
     * @return current Latitude
     */
    @Override
    public double getLatitude(){
        return getDoubleProperty(Property.LATITUDE);
    }

    /**
     *  method that returns current Longitude
     * @return current Longitude
     */
    @Override
    public double getLongitude(){
        return getDoubleProperty(Property.LONGITUDE);
    }

    /**
     *  method that returns current Roll
     * @return current Roll
     */
    @Override
    public double getRoll(){
        return getDoubleProperty(Property.ROLL);
    }

    /**
     *  method that returns current Pitch
     * @return current Pitch
     */
    @Override
    public double getPitch(){
        return getDoubleProperty(Property.PITCH);
    }

    /**
     *  method that returns current Yaw
     * @return current Yaw
     */
    @Override
    public double getYaw(){
        return getDoubleProperty(Property.YAW);
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
            //TODO: DETERMINE HOW TO HADLE THIS
        }
        return value;
    }

    private void setDoubleProperty(Property p , double value){
        DoublePropertyManager dpm = (DoublePropertyManager) properties.get(p);
        try {
            dpm.updateValue(fgfs,value);
        } catch (IOException e) {
            //TODO: DETERMINE HOW TO HADLE THIS
        }
    }

    private String getStringProperty(Property p){
        StringPropertyManager spm = (StringPropertyManager) properties.get(p);
        String value = null;
        try {
            value = spm.retrieveValue(fgfs);
        } catch (IOException e) {
            //TODO: DETERMINE HOW TO HADLE THIS
        }
        return value;
    }

    private void setStringProperty(Property p , String value){
        StringPropertyManager spm = (StringPropertyManager) properties.get(p);
        try {
            spm.updateValue(fgfs,value);
        } catch (IOException e) {
            //TODO: DETERMINE HOW TO HADLE THIS
        }
    }

    private Boolean getBooleanProperty(Property p){

        BooleanPropertyManager bpm = (BooleanPropertyManager) properties.get(p);
        Boolean value = null;
        try {
            value = bpm.retrieveValue(fgfs);
        } catch (IOException e) {
            //TODO: DETERMINE HOW TO HADLE THIS
        }
        return value;
    }

    private void setBooleanProperty(Property p , Boolean value){
        BooleanPropertyManager bpm = (BooleanPropertyManager) properties.get(p);
        try {
            bpm.updateValue(fgfs,value);
        } catch (IOException e) {
            //TODO: DETERMINE HOW TO HADLE THIS
        }
    }

}
