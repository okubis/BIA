package main;

import com.layer.SocketConnectionParameters;
import individual.Individual;
import neuroevo.Neat;

import javax.swing.*;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by okubis on 5/14/16.
 */
public class Main extends JFrame {
    private static ArrayList<SocketConnectionParameters> scp;
    private static Neat neatInstance;
    private static int populationSize;
    private static int numberOfThreads;
    private static int numberOfGenerations;
    private static final int FIRST_SOCKET = 2000;
    private static Individual result;


    public static void main(String[] args) throws Exception {
        readInput();
        initSocketConnectionParameters();
        neatInstance = new Neat(numberOfThreads, scp, populationSize);
        neatInstance.Evolve(numberOfGenerations);
        result = neatInstance.getBestResult();
        //TODO: what to do with the result...
        // serialize? save as string?
        saveResult();
    }

    private static void saveResult() {
    }

    private static void initSocketConnectionParameters() {
        scp = new ArrayList<SocketConnectionParameters>(populationSize);
        for (int i = 0; i < populationSize; i++) {
            SocketConnectionParameters conParams = new SocketConnectionParameters("localhost", FIRST_SOCKET + i);
        }
    }

    private static void readInput() throws Exception {
        BufferedInputStream inBuffer = new BufferedInputStream(System.in);
        System.out
                .println("Specify number of threads this algorithm can use to evaluate fitness function in parallel: ");
        numberOfThreads = readInteger(inBuffer);
        System.out.println("Specify size of population used by the NEAT algorithm: ");
        populationSize = readInteger(inBuffer);
        System.out.println("Specify number of generations computed by the NEAT algorithm: ");
        numberOfGenerations = readInteger(inBuffer);
        // should be closed, but not in the case of console, right?
        //inBuffer.close();
    }

    private static int readInteger(InputStream input) throws Exception {
        int loaded = 0;
        boolean isDigit = true;
        boolean somethingLoaded = false;

        while (isDigit) {
            int in = input.read();
            if (in >= '0' && in <= '9') {
                isDigit = true;
                loaded = loaded * 10 + in - '0';
                somethingLoaded = true;
            } else {
                if (somethingLoaded)
                    isDigit = false;
            }
        }
        return loaded;
    }

}
