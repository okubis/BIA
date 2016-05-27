package ann;

import com.layer.AbstractToolbox;
import com.layer.SocketConnectionParameters;
import com.layer.Toolbox;
import individual.Individual;

import java.util.concurrent.Callable;

/**
 * Created by okubis on 5/27/16.
 */
public class Evaluator implements Callable<Individual> {
    private Individual individual;
    private ANN ann;
    private long timeLimit;
    private long period;
    private AbstractToolbox toolbox;
    private SocketConnectionParameters scp;

    /**
     * @param SCParams  parameters of the socket to be used by this Evaluator
     * @param ind   Evaluated Individual
     * @param timeLimit Timelimit for evaluation in milliseconds
     * @param period period after which we check our position and recompute the output of ANN
     */
    public Evaluator(SocketConnectionParameters SCParams, Individual ind, long timeLimit, long period){
        this.scp = SCParams;
        this.toolbox = new Toolbox();
        this.individual = ind;
        this.timeLimit = timeLimit;
        this.ann = new ANN(ind.getGenotype());
    }

    /**
     *
     * @return  individual with fitness
     * @throws Exception
     */
    public Individual call() throws Exception {
        long startTime = System.currentTimeMillis();
        int numOfCycles = (int) (timeLimit/period);
        double[][] positions = new double[numOfCycles][3];
        toolbox.initFlight(scp);
        for(int i =0;i<numOfCycles;i++){


        }
        toolbox.endFlight();

        return individual;
    }
}
