package ann;

/**
 * Created by okubis on 5/26/16.
 */
public class ANNOutputData {
    private double aileron;
    private double elevator;
    private double rudder;

    public double getAileron() {
        return aileron;
    }

    public void setAileron(double aileron) {
        this.aileron = aileron;
    }

    public double getElevator() {
        return elevator;
    }

    public void setElevator(double elevator) {
        this.elevator = elevator;
    }

    public double getRudder() {
        return rudder;
    }

    public void setRudder(double rudder) {
        this.rudder = rudder;
    }

	@Override
	public String toString() {
		return "ANNOutputData [aileron=" + aileron + ", elevator=" + elevator
				+ ", rudder=" + rudder + "]";
	}
}
