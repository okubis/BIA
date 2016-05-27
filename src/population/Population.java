package population;

import ann.Evaluator;
import com.layer.SocketConnectionParameters;
import com.layer.TimeConstants;
import individual.Individual;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.*;

public class Population {
	private ArrayList<Individual> individuals;
	private int indexOfBestSoFar;
	private static ExecutorService executor;
	private static CompletionService<Individual> completionService;

	/**
	 * internal constructor
	 * @param populationSize	size of this population
     */
	private Population(int populationSize){
		// init individuals
		individuals = new ArrayList<Individual>(populationSize);
	}

	/**
	 * Init constructor
	 * @param populationSize
	 * @param marks
	 * @param numberOfThreads
	 * @param usableSockets
     */
	public Population(int populationSize, HistoricalMarkingManager marks, int numberOfThreads, ArrayList<SocketConnectionParameters> usableSockets) {
		this(populationSize);
		// create new Individuals
		ArrayList<Individual> newIndividuals = createNewIndividuals(populationSize,marks);
		// inti population
		init(newIndividuals,populationSize,marks,numberOfThreads,usableSockets);
	}

	/**
	 * Evaluation Constructor
	 * @param newIndividuals
	 * @param populationSize
	 * @param marks
	 * @param numberOfThreads
	 * @param usableSockets
     */
	public Population(ArrayList<Individual> newIndividuals, int populationSize, HistoricalMarkingManager marks, int numberOfThreads, ArrayList<SocketConnectionParameters> usableSockets) {
		this(populationSize);
		// inti population
		init(newIndividuals,populationSize,marks,numberOfThreads,usableSockets);
	}


	/**
	 * creates Individuals for initial population
	 * @param populationSize	size of the population
	 * @param marks	historical markings (should NOT contain any marking yet)
     * @return	list of the new Individuals without fitness
     */
	private ArrayList<Individual> createNewIndividuals(int populationSize, HistoricalMarkingManager marks){
		ArrayList<Individual> newIndividuals = new ArrayList<Individual>(populationSize);
		for(int i=0;i<populationSize;i++){
			newIndividuals.add(new Individual(marks));
		}
		return newIndividuals;
	}

	/**
	 * computes fitness for all the given individuals and adds them into this population
	 * @param newIndividuals	future members of this population to compute fitness for
	 * @param populationSize	size of this population
	 * @param marks	historical markings of the nodes
	 * @param numberOfThreads	number of threads we can use concurrently
	 * @param usableSockets	sockets usable for fitness evaluation
     */
	private void init(ArrayList<Individual> newIndividuals, int populationSize, HistoricalMarkingManager marks, int numberOfThreads, ArrayList<SocketConnectionParameters> usableSockets){
		// evaluate individuals and store them in this population
		evaluateAndStoreIndividuals(populationSize,newIndividuals,numberOfThreads,usableSockets);
		// find the best and store its index
		findTheBestSoFar();
	}


	/**
	 *	This method computes fitness for each individual and adds it to this population
	 * @param newIndividuals	future members of this population to compute fitness for
	 * @param populationSize	size of this population
	 * @param numberOfThreads	number of threads we can use concurrently
	 * @param usableSockets	sockets usable for fitness evaluation
     */
	private void evaluateAndStoreIndividuals(int populationSize, ArrayList<Individual> newIndividuals, int numberOfThreads, ArrayList<SocketConnectionParameters> usableSockets) {
		int received = 0;
		boolean errors = false;
		executor = Executors.newFixedThreadPool(numberOfThreads);
		completionService = new ExecutorCompletionService<Individual>(executor);

		for(int i =0;i<populationSize;i++){
			Evaluator evaluator = new Evaluator(usableSockets.get(i),newIndividuals.get(i), TimeConstants.EVALUATOR_TIMELIMIT,TimeConstants.EVALUATION_PERIOD);
			completionService.submit(evaluator);
		}

		while ((received < populationSize) && !errors) {
			try {
				// wait until some Individual is evaluated
				Future<Individual> resultFuture = completionService.take(); // blocks if none available

				// retrieve the evaluated Individual
				Individual individualWithFitness = resultFuture.get();

				//add the evaluated individual into the population
				individuals.add(individualWithFitness);	// no need for sync, since here we run serially not in parallel

				// we have received another evaluated individual, so let's get yet another one,
				// or end if it was the last one
				received++;

			} catch (Exception e) {
				System.err.println("SYSTEM SPATNE NASTAVIL NEKTERE VECI... s pozdravem, Ty nam taky Vas MICROSOFT (TM)");
				System.err.println(e.getMessage());
				System.err.println("------------------------------------");
				e.printStackTrace();
				errors = true;
			}
		}
	}

	/**
	 * method for finding the best Individual in this population
	 */
	private void findTheBestSoFar() {
		double fitness = individuals.get(0).getFitness();
		indexOfBestSoFar=0;
		for(int i=1;i<individuals.size();i++){
			if(individuals.get(i).getFitness()<fitness){
				indexOfBestSoFar =i;
				fitness = individuals.get(indexOfBestSoFar).getFitness();
			}
		}
	}

	/**
	 * returns Individual at given Position in this population (keep in mind that this implementation of population is not sorted by fitness. The
	 * ordering of the individuals is set randomly during the creation of each instance.)
	 * @param index	index of the Individual to you want to obtain
	 * @return	Individual at desired position in this population
	 * @throws IndexOutOfBoundsException thrown if the index is lower than 0, or larger then populationSize-1
     */
	public Individual getIndividualByItsPosition(int index){
		if(index >= individuals.size()){
			throw new IndexOutOfBoundsException("index has to be between 0 and "+ (individuals.size()-1)+" inclusive, i.e.: at least 0 and at most sizeOfPopulation-1");
		}
		return individuals.get(index);
	}

	public Individual getBest(){
		return individuals.get(indexOfBestSoFar);
	}

}
