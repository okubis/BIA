package neuroevo;

import com.layer.SocketConnectionParameters;
import individual.GenotypeFileManager;
import individual.Individual;
import population.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
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
	private int firstPopulationIndex;

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
		firstPopulationIndex = 1;
	}

	public Neat(int numberOfThreads, ArrayList<SocketConnectionParameters> usableSockets, int populationSize, Population population, HistoricalMarkingManager marks, int startFrom) {
		rand = new Random();
		populationManager = new PopulationManager(numberOfThreads, usableSockets, populationSize, marks);
		species = new ArrayList<ArrayList<Integer>>();
		this.population = population;
		firstPopulationIndex = startFrom;
	}

    /**
     * method for running the NEAT
     *
     * @param numberOfGenerations number of generations the NEAT should run evolution for
     */
    public void Evolve(int numberOfGenerations) {
		if (population == null) {
			population = populationManager.init_population();
		} else {
			System.out.println("Population loaded");
		}
		for (int i = firstPopulationIndex; i <= numberOfGenerations; i++) {
			System.out.println("current best result has fitness: " + population.getBest().getFitness());
			System.out.println("Computed at: " + new SimpleDateFormat("HH.mm.ss").format(new java.util.Date()));
			System.out.println("computing generation " + i + "/" + numberOfGenerations);
			ArrayList<Individual> newIndividuals = null;
			divideIntoSpecies();
            newIndividuals = createChildrenByCrossOver();
            newIndividuals = mutateChildren(newIndividuals);
            newIndividuals.add(population.getBest());
            population = populationManager.evaluatePopulation(newIndividuals);
			try {
				saveResult(i);
			} catch (Exception e) {
			}
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
					if (currentIndividual.getFitness() < population.getIndividualByItsPosition(best.get(j)).getFitness()) {
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


    private int selectSpecieByTournament() {
		int bestIndex = 0;
		double bestValue = Double.MAX_VALUE;
		for (int i = 0; i < NeatParameters.SPECIES_TOURNAMENT_SIZE; i++) {
			int index = rand.nextInt(species.size());
			double value = population.getIndividualByItsPosition(species.get(index).get(0)).getRepresentativeFitness();
			if (value < bestValue) {
				bestIndex = index;
				bestValue = value;
			}
		}
		return bestIndex;
	}


	private int selectIndividualByTournament(int specie) {
		int bestIndex = 0;
		double bestValue = Double.MAX_VALUE;
		for (int i = 0; i < NeatParameters.INDIVIDUALS_TOURNAMENT_SIZE; i++) {
			int index = rand.nextInt(species.get(specie).size());
			double value = population.getIndividualByItsPosition(species.get(specie).get(index)).getFitness();
			if (value < bestValue) {
				bestIndex = index;
				bestValue = value;
			}
		}
		return bestIndex;
	}


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

    private ArrayList<Individual> mutateChildren(ArrayList<Individual> children) {
    	for (int i = 0; i < children.size(); i++) {
			children.get(i).mutate();
		}
        return children;
    }

    /**
     * method for obtaining the result of the NEAT algorithm
     * @return best solution found during the run of the NEAT
     */
    public Individual getBestResult() {
        return population.getBest();
    }

	/**
	 * method for obtaining of the historical markings used by the evolution
	 *
	 * @return the historical markings for nodes and connections
	 */
	public HistoricalMarkingManager getMarkings() {
		return populationManager.getMarks();
	}

	private void saveResult(int i) throws FileNotFoundException, UnsupportedEncodingException {
		String outputFolder = System.getProperty("user.home") + File.separator + "NEAT_OUT" + File.separator;
		String markingsFileName = outputFolder + "markings_" + i + ".txt";
		String genotypeFileName = outputFolder + "genotype_" + i + ".txt";
		String populationFileName = outputFolder + "population_" + i + ".ser";
		MarkingsFileManager.writeToFile(markingsFileName, populationManager.getMarks());
		GenotypeFileManager.writeToFile(genotypeFileName, population.getBest().getGenotype());
		PopulationSerializationManager.exportToFile(populationFileName, population);
	}
}
