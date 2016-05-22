package individual;

import gene.Connection;
import gene.Gene;
import gene.HiddenNode;
import gene.InputNode;
import gene.OutputNode;

import java.util.ArrayList;

public class Genotype {
	private ArrayList<Gene> genotype;
	private int nodeCount;
	
	public int getGenotypeSize(){
		return genotype.size();
	}

	public Genotype(ArrayList<Gene> genotype, int nodeCount) {
		this.genotype = genotype;
		this.nodeCount = nodeCount;
	}
	
	public Genotype(){
		genotype = new ArrayList<Gene>();
		nodeCount = InputNode.INPUTS_COUNT + OutputNode.OUTPUTS_COUNT;
		//TO DO, generate some connections
	}
	
	public void addNode(HiddenNode node){
		genotype.add(nodeCount++, node);
	}
	
	public void addConnection(Connection con){
		genotype.add(con);
	}
	
	public void removeConnection(Connection con){
		genotype.remove(con);
	}
	
	public void addHiddenNodeToConnection(Connection con, int mark){
		genotype.remove(con);
		HiddenNode inserted = new HiddenNode(1, mark);
		addNode(inserted);
		addConnection(new Connection(con.getStart(), inserted, mark, con.getWeight(), true));
		addConnection(new Connection(inserted, con.getEnd(), mark, con.getWeight(), true));
	}
}
