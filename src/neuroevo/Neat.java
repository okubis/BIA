package neuroevo;

import com.layer.SocketConnectionParameters;
import population.Population;
import population.PopulationManager;

import java.util.ArrayList;

/**
 * Created by okubis on 5/26/16.
 */
public class Neat {
    private Population population;
    private PopulationManager populationManager;
    private ArrayList<ArrayList<Integer>> species; // index druhu => Arraylist indexu Individualu, kteri patri tomuto druhu
    
    public Neat(int numberOfThreads, ArrayList<SocketConnectionParameters> usableSockets, int populationSize) {
        populationManager = new PopulationManager(numberOfThreads, usableSockets, populationSize);
        species = new ArrayList<ArrayList<Integer>>();
        population = populationManager.init_population();
    }

    //TODO: metoda Evolve (i.e: hlavni metoda NEAT volana z main)
    //TODO: metodka pro rozrazeni do druhu;
    //TODO: metodka pro vyber turnajem - Druh
    //TODO: metodka pro vyber turnajem1 - Jedinec
    //TODO: metodka pro krizeni
    //TODO: metodka pro mutaci
    //TODO: metodka pro navrat nejlepsiho vysledku celeho behu NEATu
}
