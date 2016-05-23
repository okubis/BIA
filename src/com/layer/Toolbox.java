package com.layer;

import org.flightgear.fgfsclient.FGFSConnection;
import org.flightgear.fgfsclient.PropertyPage;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by okubis on 5/17/16.
 */
public class Toolbox extends AbstractToolbox {
    private FGFSConnection fgfs;
    private HashMap pages;

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
        initPages();

        // TODO: Implement the rest

        return true;
    }

    private void initPages() {

        /*
        this.pages = new HashMap();
        PropertyPage page = new PropertyPage(this.fgfs, "Simulation");
        page.addField("/sim/aircraft", "Aircraft:");
        page.addField("/sim/startup/airport-id", "Airport ID:");
        page.addField("/sim/time/gmt", "Current time (GMT):");
        page.addField("/sim/startup/trim", "Trim on ground (true/false):");
        page.addField("/sim/sound/audible", "Sound enabled (true/false):");
        page.addField("/sim/startup/browser-app", "Web browser:");
        this.addPage(page);


        /position/latitude-deg
        /position/longitude-deg
        /position/altitude-ft


        page = new PropertyPage(this.fgfs, "View");
        page.addField("/sim/view-mode", "View mode:");
        page.addField("/sim/current-view/field-of-view", "Field of view (deg):");
        page.addField("/sim/current-view/pitch-offset-deg", "View pitch offset (deg):");
        page.addField("/sim/current-view/heading-offset-deg", "View heading offset (deg):");
        this.addPage(page);
        page = new PropertyPage(this.fgfs, "Location");
        page.addField("/position/altitude-ft", "Altitude (ft):");
        page.addField("/position/longitude-deg", "Longitude (deg):");
        page.addField("/position/latitude-deg", "Latitude (deg):");
        page.addField("/orientation/roll-deg", "Roll (deg):");
        page.addField("/orientation/pitch-deg", "Pitch (deg):");
        page.addField("/orientation/heading-deg", "Heading (deg):");
        this.addPage(page);
        page = new PropertyPage(this.fgfs, "Weather");
        page.addField("/environment/wind-from-heading-deg", "Wind direction (deg FROM):");
        page.addField("/environment/params/base-wind-speed-kt", "Wind speed (kt):");
        page.addField("/environment/params/gust-wind-speed-kt", "Maximum gust (kt):");
        page.addField("/environment/wind-from-down-fps", "Updraft (fps):");
        page.addField("/environment/temperature-degc", "Temperature (degC):");
        page.addField("/environment/dewpoint-degc", "Dewpoint (degC):");
        page.addField("/environment/pressure-sea-level-inhg", "Altimeter setting (inHG):");
        this.addPage(page);
        page = new PropertyPage(this.fgfs, "Clouds");
        page.addField("/environment/clouds/layer[0]/type", "Layer 0 type:");
        page.addField("/environment/clouds/layer[0]/elevation-ft", "Layer 0 height (ft):");
        page.addField("/environment/clouds/layer[0]/thickness-ft", "Layer 0 thickness (ft):");
        page.addField("/environment/clouds/layer[1]/type", "Layer 1 type:");
        page.addField("/environment/clouds/layer[1]/elevation-ft", "Layer 1 height (ft):");
        page.addField("/environment/clouds/layer[1]/thickness-ft", "Layer 1 thickness (ft):");
        page.addField("/environment/clouds/layer[2]/type", "Layer 2 type:");
        page.addField("/environment/clouds/layer[2]/elevation-ft", "Layer 2 height (ft):");
        page.addField("/environment/clouds/layer[2]/thickness-ft", "Layer 2 thickness (ft):");
        page.addField("/environment/clouds/layer[3]/type", "Layer 3 type:");
        page.addField("/environment/clouds/layer[3]/elevation-ft", "Layer 3 height (ft):");
        page.addField("/environment/clouds/layer[3]/thickness-ft", "Layer 3 thickness (ft):");
        page.addField("/environment/clouds/layer[4]/type", "Layer 4 type:");
        page.addField("/environment/clouds/layer[4]/elevation-ft", "Layer 4 height (ft):");
        page.addField("/environment/clouds/layer[4]/thickness-ft", "Layer 4 thickness (ft):");
        this.addPage(page);
        page = new PropertyPage(this.fgfs, "Velocities");
        page.addField("/velocities/airspeed-kt", "Airspeed (kt):");
        page.addField("/velocities/speed-down-fps", "Descent speed (fps):");
        this.addPage(page);
        this.getContentPane().add(this.tabs);
        (new Thread(new FGFSDemo.Updater())).start();
        /*
        */
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
        // TODO: Implement
        return 0.0;
    }

    /**
     *  method that returns current Latitude
     * @return current Latitude
     */
    @Override
    public double getLatitude(){
        // TODO: Implement
        return 0.0;
    }

    /**
     *  method that returns current Longitude
     * @return current Longitude
     */
    @Override
    public double getLongitude(){
        // TODO: Implement
        return 0.0;
    }

    /**
     *  method that returns current Roll
     * @return current Roll
     */
    @Override
    public double getRoll(){
        // TODO: Implement
        return 0.0;
    }

    /**
     *  method that returns current Pitch
     * @return current Pitch
     */
    @Override
    public double getPitch(){
        // TODO: Implement
        return 0.0;
    }

    /**
     *  method that returns current Yaw
     * @return current Yaw
     */
    @Override
    public double getYaw(){
        // TODO: Implement
        return 0.0;
    }

    /**
     * Method that returns current status of Aileron
     *
     * @return current status of Aileron
     */
    @Override
    public double getAileronStatus() {
        return 0;
    }

    /**
     * Method that returns current status of Elevators
     *
     * @return current status of Elevators
     */
    @Override
    public double getElevatorsStatus() {
        return 0;
    }

    /**
     * Method that returns current status of Rudder
     *
     * @return current status of Rudder
     */
    @Override
    public double getRudderStatus() {
        return 0;
    }

    /**
     *  method that controls ROLL by setting value for ailerons
     */
    @Override
    public void setAileron(double aileronInput){
        // TODO: Implement
    }

    /**
     *  method that controls PITCH by setting value for elevators
     */
    @Override
    public void setElevators(double elevatorsInput){
        // TODO: Implement
    }

    /**
     *  method that controls YAW by setting value for rudder
     */
    @Override
    public void setRudder(double rudderInput){
        // TODO: Implement
    }

}
