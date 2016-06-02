package main;

import ann.Evaluator;
import com.layer.SocketConnectionParameters;
import com.layer.TimeConstants;
import individual.Genotype;
import individual.GenotypeFileManager;
import individual.Individual;
import population.HistoricalMarkingManager;
import population.MarkingsFileManager;
import population.Population;
import population.PopulationSerializationManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by okubis on 5/31/16.
 */
public class ResultVisualizer {
    private static String inputFolder;
    private static HistoricalMarkingManager mngr;
    private static Individual individual;
    private static Population population;
    private static ArrayList<SocketConnectionParameters> scp;
    private static final int FIRST_SOCKET = 2000;

    private static final int index = 1;


    public static void main(String[] args) throws Exception {
        initSocketConnectionParameters();
        loadInput();
        loadResult();
        population = loadPopulation(index);
        Evaluator evaluator;//= new Evaluator(scp.get(0), individual, TimeConstants.EVALUATOR_TIMELIMIT, TimeConstants.EVALUATION_PERIOD);
        //individual = evaluator.call();
        System.out.println("Fitness: " + population.getBest().getFitness());
        loadConfirmation();
        evaluator = new Evaluator(scp.get(0), individual, 360 * TimeConstants.EVALUATOR_TIMELIMIT, TimeConstants.EVALUATION_PERIOD);
        evaluator.call();
    }

    private static Population loadPopulation(int index) throws FileNotFoundException {
        String populationFileName = inputFolder + "population_" + index + ".ser";
        return PopulationSerializationManager.importFromFile(populationFileName);
    }

    private static void loadConfirmation() {
        System.out.println("Do you wish to let the sim run longer? (y/n), deafault yes.");
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();
        if (s.toLowerCase().equals("n"))
            System.exit(0);
    }


    private static void loadResult() throws FileNotFoundException, UnsupportedEncodingException {
        String markingsFileName = inputFolder + "markings_" + index + ".txt";
        String genotypeFileName = inputFolder + "genotype_" + index + ".txt";
        mngr = MarkingsFileManager.readFromFile(markingsFileName);
        Genotype g = GenotypeFileManager.readFromFile(genotypeFileName);
        Individual i = new Individual(g, mngr);

        individual = i;
    }

    private static void initSocketConnectionParameters() {
        scp = new ArrayList<SocketConnectionParameters>(1);
        for (int i = 0; i <= 1; i++) {
            SocketConnectionParameters conParams = new SocketConnectionParameters("localhost", FIRST_SOCKET + i);
            scp.add(conParams);
        }
    }

    private static void loadInput() {
        System.out.println("Specify where the output should be stored (Path to folder)");
        Scanner sc = new Scanner(System.in);
        //outputFile = sc.nextLine();
        inputFolder = System.getProperty("user.home") + File.separator + "NEAT_OUT" + File.separator;
    }
}
