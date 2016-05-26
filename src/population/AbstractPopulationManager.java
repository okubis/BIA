package population;

import java.util.Random;

/**
 * Created by okubis on 5/26/16.
 */
public abstract class AbstractPopulationManager {
    public abstract Population init_population( int populationSize);
    public abstract Population nextGeneration(Population p, int populationSize);



}
