package ANN;

import java.util.HashMap;

import gene.Connection;
import gene.Node;
import individual.Genotype;

public class ANN {
	Node[] nodes;
	Connection[] cons;
	HashMap<Integer,Integer> indexOf = new HashMap<Integer, Integer>();
	double[] values;
	
	public ANN (Genotype gen){
		int nodeCount = gen.getNodeCount();
		nodes = new Node[nodeCount];
		cons = new Connection[gen.getGenotypeSize() - nodeCount];
		indexOf = new HashMap<Integer, Integer>();
		values = new double[nodeCount];
		for (int i = 0; i < nodeCount; i++) {
			indexOf.put( ((Node) gen.get(i)).getMark() , i);
		}
		int[] outputs = new int[nodeCount];
		int[] processedOutputs = new int[nodeCount];
		for(int i = nodeCount; i < gen.getGenotypeSize(); i++){
			outputs[indexOf.get( ((Node) gen.get(i)).getMark() )]++;
		}
	}
}
