package population;

import individual.Individual;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by okubis on 5/26/16.
 */
public abstract class AbstractPopulationManager {
    public abstract Population init_population();
    public abstract Population evaluatePopulation(ArrayList<Individual> newIndividuals);



}
