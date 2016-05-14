package individual;

import java.util.List;

/**
 * Created by okubis on 5/14/16.
 */
public interface IndividualManagerInterface {

    // simple crossover interface to be modified and implemented
    abstract List<IndividualInterface> crossOver(IndividualInterface parent1, IndividualInterface parent2);

    // simple mutation interface to be modified and implemented
    abstract IndividualInterface mutate(IndividualInterface individual);

    // simple fitness call, should use com.layer for eval, to be modified and implemented
    abstract Double computeFitness(IndividualInterface individual);
}
