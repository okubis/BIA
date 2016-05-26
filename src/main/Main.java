package main;

import com.layer.SocketConnectionParameters;
import com.layer.Toolbox;
import com.properties.AbstractPropertyManager;
import com.properties.BooleanPropertyManager;
import com.properties.Property;
import com.sun.org.apache.xpath.internal.SourceTree;
import org.flightgear.fgfsclient.FGFSConnection;
import org.flightgear.fgfsclient.PropertyField;
import org.flightgear.fgfsclient.PropertyPage;

import javax.swing.*;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by okubis on 5/14/16.
 */
public class Main extends JFrame {
    private static SocketConnectionParameters scp = new SocketConnectionParameters("localhost", 6789);
    private static Toolbox toolbox = new Toolbox();
    private static FGFSConnection fgfs;

    public static BooleanPropertyManager baf = new BooleanPropertyManager("/sim/freeze/master", Property.PITCH);
    public static BooleanPropertyManager baf2 = new BooleanPropertyManager("/sim/freeze/position", Property.LATITUDE);



    public static void main(String[] args) throws IOException {
        //fgfs = new FGFSConnection("localhost", 6789);
        toolbox.initFlight(scp);
        for(int i=0;i<5;i++) {
            System.out.println(toolbox.getLatitude());
            System.out.println(toolbox.getLongitude());
            try {
                Thread.sleep(8000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        toolbox.endFlight();
      //  System.out.println(toolbox.getRoll());
      //  System.out.println(toolbox.getPitch());
      //  System.out.println(toolbox.getYaw());
       // System.out.println(toolbox.Spe);
        // 37.59850793
        // -122.3214854
        // 2030.341683

        /*
        while(true){
            double aileron = 0;

             //   aileron = toolbox.getAileronStatus();

            //System.out.println(aileron);

        //    System.out.println(toolbox.getRoll());
        //    System.out.println(toolbox.getPitch());
        //    System.out.println(toolbox.getYaw());
            toolbox.initFlight(scp);
            System.out.println("-------------------------");
            try {
                Thread.sleep(20000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
          //  toolbox.setAileron(aileron+0.01);
        }

        */

        //TODO: this will be the main class
    }

}
