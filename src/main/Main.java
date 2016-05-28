package main;

import com.layer.SocketConnectionParameters;
import individual.GenotypeFileManager;
import individual.Individual;
import neuroevo.Neat;
import population.MarkingsFileManager;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

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
    private static String outputFolder;


    public static void main(String[] args) throws Exception {
        readInput();
        initSocketConnectionParameters();
        neatInstance = new Neat(numberOfThreads, scp, populationSize);
        neatInstance.Evolve(numberOfGenerations);
        result = neatInstance.getBestResult();
        saveResult();
        System.out.println("result in the form of a String: ");
        System.out.println("Fitness: " + result.getFitness());
        System.out.println("---------------------------------");
        System.out.println(result.getGenotype().toString());
    }

    private static void saveResult() throws FileNotFoundException, UnsupportedEncodingException {
        String markingsFileName = outputFolder + "markings.txt";
        String genotypeFileName = outputFolder + "genotype.txt";
        MarkingsFileManager.writeToFile(markingsFileName, neatInstance.getMarkings());
        GenotypeFileManager.writeToFile(genotypeFileName, result.getGenotype());
    }

    private static void initSocketConnectionParameters() {
        scp = new ArrayList<SocketConnectionParameters>(populationSize);
        for (int i = 0; i <= populationSize; i++) {
            SocketConnectionParameters conParams = new SocketConnectionParameters("localhost", FIRST_SOCKET + i);
            scp.add(conParams);
        }
    }

    private static void readInput() throws Exception {
        BufferedInputStream inBuffer = new BufferedInputStream(System.in);
        System.out
                .println("Specify number of threads this algorithm can use to evaluate fitness function in parallel: ");
        // numberOfThreads = readInteger(inBuffer);
        numberOfThreads = 3;
        System.out.println("Specify size of population used by the NEAT algorithm: ");
        //populationSize = readInteger(inBuffer);
        populationSize = 50;
        System.out.println("Specify number of generations computed by the NEAT algorithm: ");
        // numberOfGenerations = readInteger(inBuffer);
        numberOfGenerations = 10;
        System.out.println("Specify where the output should be stored (Path to folder)");
        Scanner sc = new Scanner(System.in);
        //outputFile = sc.nextLine();
        outputFolder = System.getProperty("user.home") + File.separator + "NEAT_OUT" + File.separator;
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
