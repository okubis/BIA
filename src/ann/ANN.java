package ann;

import java.util.HashMap;

import gene.Connection;
import gene.Node;
import individual.Genotype;

public class ANN {
	Node[] nodes;
	Connection[] connections;
	HashMap<Integer,Integer> indexOf = new HashMap<Integer, Integer>();
	double[] values;
	int[] outputs;
	
	public ANN (Genotype gen){
		int nodeCount = gen.getNodeCount();
		nodes = new Node[nodeCount];
		connections = new Connection[gen.getGenotypeSize() - nodeCount];
		indexOf = new HashMap<Integer, Integer>();
		values = new double[nodeCount];
		for (int i = 0; i < nodeCount; i++) {
			indexOf.put( ((Node) gen.get(i)).getMark() , i);
		}
		outputs = new int[nodeCount];
		int[] processedOutputs = new int[nodeCount];
		int[] outputSum = new int[nodeCount];
		for(int i = nodeCount; i < gen.getGenotypeSize(); i++){
			outputs[indexOf.get( ((Connection) gen.get(i)).getStart() )]++;
		}
		outputSum[0] = 0;
		for(int i = 1; i < nodeCount; i++){
			outputSum[i] = outputSum[i-1] + outputs[i-1];
		}
		for(int i = nodeCount; i < gen.getGenotypeSize(); i++){
			Connection con = (Connection) gen.get(i);
			int indexNode = indexOf.get(con.getStart());
			int index = outputSum[indexNode] + processedOutputs[indexNode]++;
			connections[index] = con;
		}
	}
	
	public ANNOutputData compute(ANNInputData inputs){
		resetValues();
		for (int i = 0; i < 9; i++) {
			if(!nodes[i].isInput()){
				System.err.println("Node " + i + " with mark " + nodes[i].getMark() + " should be an input node!");
			}
		}
		for (int i = 1; i < 4; i++) {
			if(!nodes[nodes.length - i].isOutput()){
				System.err.println("Node " + (nodes.length - i) + " with mark " + nodes[(nodes.length - i)].getMark() + " should be an output node!");
			}
		}
		values[0] = inputs.getAltitude();
		values[1] = inputs.getLatitude();
		values[2] = inputs.getLongitude();
		values[3] = inputs.getPitch();
		values[4] = inputs.getRoll();
		values[5] = inputs.getYaw();
		values[6] = inputs.getAileron();
		values[7] = inputs.getElevator();
		values[8] = inputs.getRudder();
		int edgeIndex = 0;
		int nodeIndex;
		for (int i = 0; i < nodes.length; i++) {
			values[i] = nodes[i].function(values[i]);
			//process one node
			for (int j = 0; j < outputs[i]; j++) {
				Connection con = connections[edgeIndex];
				if(con.isEnabled()){
					nodeIndex = indexOf.get(con.getEnd());
					values[nodeIndex] += con.getWeight()*values[i];
					edgeIndex++;
				}else{
					edgeIndex++;
				}
			}
		}
		ANNOutputData output = new ANNOutputData();
		output.setAileron(values[nodes.length - 3]);
		output.setElevator(values[nodes.length - 2]);
		output.setRudder(values[nodes.length - 1]);
		return output;
	}
	
	public void resetValues(){
		for (int i = 0; i < values.length; i++) {
			values[i] = 0;
		}
	}
}
