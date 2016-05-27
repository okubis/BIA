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

        int numOfCycles = (int) (timeLimit/period);
        double[][] positions = new double[numOfCycles][3]; //altitude, longitude, latitude - corresponds to getPenalty in toolbox
        ANNInputData in = new ANNInputData();
        ANNOutputData out = new ANNOutputData();

        // start flight

        toolbox.initFlight(scp);
        for(int i =0;i<numOfCycles;i++){
            long startTime = System.currentTimeMillis();

            // retrieve location
            in.setAltitude(toolbox.getAltitude());
            in.setLatitude(toolbox.getLatitude());
            in.setLongitude(toolbox.getLongitude());

            // retrieve orientation
            in.setRoll(toolbox.getRoll());
            in.setPitch(toolbox.getPitch());
            in.setYaw(toolbox.getYaw());

            // retrieve control setting
            in.setAileron(toolbox.getAileronStatus());
            in.setElevator(toolbox.getElevatorStatus());
            in.setRudder(toolbox.getRudderStatus());

            // store position for fitness computation
            positions[i][0] = in.getAltitude();
            positions[i][1] = in.getLongitude();
            positions[i][2] = in.getLatitude();

            // compute inputs of the simulator by evaluating ANN over ANNInputData
            out = ann.compute(in);

            // set aileron, elevator and rudder
            toolbox.setAileron(out.getAileron());
            toolbox.setElevator(out.getElevator());
            toolbox.setRudder(out.getRudder());

            long currentTime = System.currentTimeMillis();
            while(currentTime-startTime<period){
                currentTime = System.currentTimeMillis();
            }
        }

        // end flight
        toolbox.endFlight();

        // compute fitness of Individual
        double fitness = 0.;
        for(int i=0;i<numOfCycles;i++){
            fitness+=toolbox.getPenalty(positions[i][0],positions[i][1],positions[i][2]);
        }
        fitness/=numOfCycles;

        //set fitness of Individual;
        individual.setFitness(fitness);
        return individual;
    }
}
