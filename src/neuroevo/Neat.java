package neuroevo;

import com.layer.SocketConnectionParameters;
import individual.Individual;
import population.Population;
import population.PopulationManager;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by okubis on 5/26/16.
 */
public class Neat {
    private Population population;
    private PopulationManager populationManager;
    private ArrayList<ArrayList<Integer>> species; // index druhu => Arraylist indexu Individualu, kteri patri tomuto druhu
    private Random rand;

    public Neat(int numberOfThreads, ArrayList<SocketConnectionParameters> usableSockets, int populationSize) {
        rand = new Random();
        populationManager = new PopulationManager(numberOfThreads, usableSockets, populationSize);
        species = new ArrayList<ArrayList<Integer>>();
        population = null;
    }

    //TODO: metoda Evolve (i.e: hlavni metoda NEAT volana z main)
    public void Evolve(int numberOfGenerations) {
        population = populationManager.init_population();
        for (int i = 0; i < numberOfGenerations; i++) {
            ArrayList<Individual> newIndividuals = null;
            // TODO: ONE GENERATION OF NEAT ; long way to go
            // TODO: ASSIGN RESULTING Individuals TO newIndividuals;
            population = populationManager.evaluatePopulation(newIndividuals);
        }
    }

    //TODO: metodka pro rozrazeni do druhu;
    //working in progress

    //TODO: metodka pro vyber turnajem - Druh
    private int selectSpecieByTournament() {
        return 0;
    }
    //TODO: metodka pro vyber turnajem1 - Jedinec
    private int selectIndividualByTournament(int specie) {
        return 0;
    }
    //TODO: metodka pro krizeni
    private ArrayList<Individual> createChildrenByCrossOver() {
        return null;
    }
    //TODO: metodka pro mutaci
    private ArrayList<Individual> mutateChildren(ArrayList<Individual> children) {
        return null;
    }

    //TODO: metodka pro navrat nejlepsiho vysledku celeho behu NEATu
    public Individual getBestResult() {
        return population.getBest();
    }
}
