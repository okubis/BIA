package population;

import com.layer.SocketConnectionParameters;
import gene.InputNode;
import gene.NodeTuple;
import gene.OutputNode;
import individual.Individual;

import java.util.ArrayList;
import java.util.HashMap;

public class Population {
	private ArrayList<Individual> individuals;
	private int indexOfBestSoFar;

	//TODO: constructor - CAPART
	//TODO: other methods - CAPART


	public Population(int populationSize, HistoricalMarkingManager marks, int numberOfThreads, ArrayList<SocketConnectionParameters> usableSockets) {
		individuals = new ArrayList<Individual>(populationSize);
		for(int i=0;i<populationSize;i++){
			individuals.add(new Individual(marks));
		}
		evaluatePopulation(numberOfThreads,usableSockets);
	}

	public Population(Population population, int populationSize, HistoricalMarkingManager marks, int numberOfThreads, ArrayList<SocketConnectionParameters> usableSockets) {
		individuals = new ArrayList<Individual>(populationSize);
	}

	private void evaluatePopulation(int numberOfThreads, ArrayList<SocketConnectionParameters> usableSockets) {
		// TODO: Compute fitness fo each individual in population, use threadpool

	}

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
