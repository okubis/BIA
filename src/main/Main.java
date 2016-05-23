package main;

import org.flightgear.fgfsclient.FGFSConnection;
import org.flightgear.fgfsclient.PropertyPage;

import javax.swing.*;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by okubis on 5/14/16.
 */
public class Main extends JFrame {
    private FGFSConnection fgfs;
    private JTabbedPane tabs;
    private HashMap pages;

    public Main(String host, int port) throws IOException {
        super("FlightGear Client Console");
        this.fgfs = new FGFSConnection(host, port);
        this.tabs = new JTabbedPane();
        this.pages = new HashMap();
        PropertyPage page = new PropertyPage(this.fgfs, "Simulation");
        page.addField("/sim/aircraft", "Aircraft:");
        page.addField("/sim/startup/airport-id", "Airport ID:");
        page.addField("/sim/time/gmt", "Current time (GMT):");
        page.addField("/sim/startup/trim", "Trim on ground (true/false):");
        page.addField("/sim/sound/audible", "Sound enabled (true/false):");
        page.addField("/sim/startup/browser-app", "Web browser:");
        this.addPage(page);
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
        page.addField("/environmFGFSDemoent/clouds/layer[4]/type", "Layer 4 type:");
        page.addField("/environment/clouds/layer[4]/elevation-ft", "Layer 4 height (ft):");
        page.addField("/environment/clouds/layer[4]/thickness-ft", "Layer 4 thickness (ft):");
        this.addPage(page);
        page = new PropertyPage(this.fgfs, "Velocities");
        page.addField("/velocities/airspeed-kt", "Airspeed (kt):");
        page.addField("/velocities/speed-down-fps", "Descent speed (fps):");
        this.addPage(page);
        this.getContentPane().add(this.tabs);
        (new Thread(new Main.Updater())).start();
    }

    private void addPage(PropertyPage page) {
        this.tabs.add(page.getName(), new JScrollPane(page));
        this.pages.put(page.getName(), page);
    }

    public static void main(String[] args) throws Exception {
        if(args.length != 2) {
            System.err.println("Usage: FGFSDemo <host> <port>");
            System.exit(2);
        }

        Main gui = new Main(args[0], Integer.parseInt(args[1]));
        gui.setDefaultCloseOperation(3);
        gui.pack();
        gui.show();
    }

    class Updater implements Runnable {
        Updater() {
        }

        public void run() {
            while(true) {
                int index = Main.this.tabs.getSelectedIndex();
                //if(index > -1) {
                if(true){
                    String e = Main.this.tabs.getTitleAt(index);
                    PropertyPage page = (PropertyPage) Main.this.pages.get(e);
                    System.out.println("HERE");
                    try {
                        System.out.println("try");
                        page.update();
                        System.out.println("ok");
                    } catch (IOException var6) {
                        System.out.println("err");
                        ;
                    }
                }

                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException var5) {
                    ;
                }
            }
        }
    }
    /*
    public static void main(String[] args) {
        //TODO: this will be the main class
    }
    */
}
