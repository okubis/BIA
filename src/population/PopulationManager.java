package population;

import com.layer.SocketConnectionParameters;
import individual.Individual;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by okubis on 5/26/16.
 */
public class PopulationManager extends AbstractPopulationManager {
    private int numberOfThreads;
    private ArrayList<SocketConnectionParameters> usableSockets;
    private HistoricalMarkingManager marks;
    private int populationSize;


    public PopulationManager(int numberOfThreads,ArrayList<SocketConnectionParameters> usableSockets , int populationSize){
        if(numberOfThreads > usableSockets.size()){
            throw new RuntimeException("MORE SOCKETS NEEDED");
        }else {
            this.numberOfThreads = numberOfThreads;
            this.usableSockets = usableSockets;
            this.marks = new HistoricalMarkingManager();
            this.populationSize = populationSize;
        }
    }

    /**
     *
     * @return initial Population
     */
    public Population init_population() {
        Population newPopulation = new Population(populationSize,marks,numberOfThreads,usableSockets);
        return newPopulation;
    }

    /**
     *
     * @param newIndividuals individuals to be evaluated and put into population
     * @return  new population of evaluated individuals
     */
    public Population evaluatePopulation(ArrayList<Individual> newIndividuals) {
        Population newPopulation = new Population(newIndividuals,populationSize,marks,numberOfThreads,usableSockets);
        return null;
    }
}
