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

    public PopulationManager(int numberOfThreads,ArrayList<SocketConnectionParameters> usableSockets){
        if(numberOfThreads > usableSockets.size()){
            throw new RuntimeException("MORE SOCKETS NEEDED");
        }else {
            this.numberOfThreads = numberOfThreads;
            this.usableSockets = usableSockets;
            this.marks = new HistoricalMarkingManager();
        }
    }

    public Population init_population(int populationSize) {
        Population newPopulation = new Population(populationSize,marks,numberOfThreads,usableSockets);
        return newPopulation;
    }

    public Population nextGeneration(Population population, int populationSize) {
        Population newPopulation = new Population(population,populationSize,marks,numberOfThreads,usableSockets);
        return null;
    }
}
