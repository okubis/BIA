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

    /**
     * constructor
     *
     * @param numberOfThreads number of threads that can be used concurrently
     * @param usableSockets   description of sockets usable for fitness evaluations
     * @param populationSize  size of the population used during the evolution
     */
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
            divideIntoSpecies();
            // -> explicit fitness sharing evolution
            // TODO: ASSIGN RESULTING Individuals TO newIndividuals;
            population = populationManager.evaluatePopulation(newIndividuals);
        }
    }

    /**
     * changes global variable species, no need for its initialization besides the call of this function
     */
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
    	ArrayList<Individual> result = new ArrayList<Individual>();
    	for (int i = 0; i < populationManager.getPopulationSize() - 1; i++) {
			int specie = selectSpecieByTournament();
			int index1 = selectIndividualByTournament(specie);
			int index2;
			if(rand.nextDouble() < NeatParameters.CROSS_SPECIES_MATING_PROBABILITY){
				int specie2 = selectSpecieByTournament();
				index2 = selectIndividualByTournament(specie2);
			}else{
				index2 = selectIndividualByTournament(specie);
			}
			result.add(population.getIndividualByItsPosition(index1).mating(population.getIndividualByItsPosition(index2), populationManager.getMarks()));
		}
        return result;
    }
    //TODO: metodka pro mutaci
    private ArrayList<Individual> mutateChildren(ArrayList<Individual> children) {
    	for (int i = 0; i < children.size(); i++) {
			children.get(i).mutate();
		}
        return children;
    }

    //TODO: metodka pro navrat nejlepsiho vysledku celeho behu NEATu
    public Individual getBestResult() {
        return population.getBest();
    }
}
