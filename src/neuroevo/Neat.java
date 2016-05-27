package neuroevo;

import java.util.ArrayList;

import com.layer.SocketConnectionParameters;

import population.*;
import individual.*;
import ann.*;

import com.layer.SocketConnectionParameters;

import population.AbstractPopulationManager;
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


	
    private void divideIntoSpecies(){
    	this.species = new ArrayList<ArrayList<Integer>>();
    	ArrayList<Integer> best = new ArrayList<Integer>();
    	for (int i = 0; i < populationManager.getPopulationSize(); i++) {
			boolean neededNewSpecies = true;
			for (int j = 0; j < species.size(); j++) {
				Individual currentIndividual = population.getIndividualByItsPosition(i);
				if(currentIndividual.isSimilarTo(
						population.getIndividualByItsPosition(species.get(j).get(0)))){
					neededNewSpecies = false;
					species.get(j).add(i);
					if(currentIndividual.getFitness() > population.getIndividualByItsPosition(best.get(j)).getFitness()){
						best.set(j, i);
					}
					break;
				}
			}
			if(neededNewSpecies){
				species.add(new ArrayList<Integer>());
				species.get(species.size() - 1).add(i);
				best.add(i);
			}
		}
    	for (int i = 0; i < species.size(); i++) {
			for (int j = 0; j < species.get(i).size(); j++) {
				population.getIndividualByItsPosition(species.get(i).get(j)).setRepresentative(
						population.getIndividualByItsPosition(best.get(i)));
			}
		}
    }
    	
    public Individual getBestResult() {
        return population.getBest();
    }
}
