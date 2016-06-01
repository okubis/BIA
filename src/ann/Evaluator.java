package ann;

import com.layer.AbstractToolbox;
import com.layer.SocketConnectionParameters;
import com.layer.TimeConstants;
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
        this.period = period;
    }

    /**
     *
     * @return  individual with fitness
     * @throws Exception
     */
    public Individual call() throws Exception {
        long denominator = Math.max(period, TimeConstants.MINIMAL_TIME_NEEDED_FOR_RETRIEVING_VALUES);
        Long numOfCycles = timeLimit / denominator;
        double[][] positions = new double[numOfCycles.intValue()][6]; //altitude, longitude, latitude, (roll, pitch, yaw) - corresponds to getPenalty in toolbox
        ANNInputData in = new ANNInputData();
        ANNOutputData out = new ANNOutputData();
        double fitness = 0.;

        // start flight
        try {
            toolbox.initFlight(scp);
            for (int i = 0; i < numOfCycles; i++) {
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
                positions[i][3] = in.getRoll();
                positions[i][4] = in.getPitch();
                positions[i][5] = in.getYaw();


                // compute inputs of the simulator by evaluating ANN over ANNInputData
                out = ann.compute(in);

                // set aileron, elevator and rudder
                toolbox.setAileron(out.getAileron());
                toolbox.setElevator(out.getElevator());
                toolbox.setRudder(out.getRudder());

                long currentTime = System.currentTimeMillis();
                while (currentTime - startTime < denominator) {
                    currentTime = System.currentTimeMillis();
                }

            }

            // end flight
            while (!toolbox.endFlight()) {
            }

            // compute fitness of Individual

            for (int i = 0; i < numOfCycles; i++) {
                fitness += toolbox.getPenalty(positions[i][0], positions[i][1], positions[i][2], positions[i][3], positions[i][4], positions[i][5]);
            }
            fitness /= numOfCycles;
        } catch (Exception e) {
            fitness = Double.MAX_VALUE;
        } finally {
            try {
                // end flight
                toolbox.endFlight();
            } catch (Exception e) {

            }
        }
        //set fitness of Individual;
        individual.setFitness(fitness);
        return individual;
    }
}
