package main;

import individual.Individual;
import population.Population;
import population.PopulationSerializationManager;

import java.io.File;

/**
 * Created by okubis on 6/2/16.
 */
public class Test {
    public static void main(String[] args) {
        String fs = File.separator;
        for (int j = 1; j < 49; j++) {
            String file = System.getProperty("user.home") + File.separator + "NEAT_OUT" + File.separator + "a" + File.separator + "aLastRun" + File.separator + "population_" + j + ".ser";
            Population population = PopulationSerializationManager.importFromFile(file);
            Individual best = population.getBest();
            System.out.println(j + " & " + best.getFitness() + "\\\\");
            System.out.println("\\hline");
        }
    }
}
