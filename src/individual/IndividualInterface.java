package individual;

/**
 * Created by okubis on 5/14/16.
 */
public interface IndividualInterface {

    // has to have this getter
    abstract ANNInterface getGenome();

    // has to have this getter
    abstract Double getFitness();
}
