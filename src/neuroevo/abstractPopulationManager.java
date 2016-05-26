package neuroevo;

import java.util.Random;

/**
 * Created by okubis on 5/26/16.
 */
public abstract class abstractPopulationManager {
    public abstract void init_population(Random rand);
    public abstract Population nextGeneration(Population p,Random rand);

}
