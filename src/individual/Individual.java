package individual;

import java.util.Random;

import gene.Connection;
import gene.HiddenNode;
import gene.InputNode;
import gene.NodeTuple;
import gene.OutputNode;

public class Individual {
	private static final double C_1 = 0.4;
	private static final double C_2 = 0.4;
	private static final double C_3 = 0.4;
	
	private static final double MUTATION_PROBABILITY = 0.3;
	private static final double PARAMETRIC_MUTATION_THRESHOLD = 0.7;
	private static final double PARAMETRIC_MUTATION_PROBABILITY = 0.6;
	
	private static final double STRUCTURAL_MUTATION_ADDING_EDGE_PROBABILITY = 0.5;
	private static final double STRUCTURAL_MUTATION_ADDING_NODE_PROBABILITY = 0.9;
	
	
	private Genotype genotype;
	private double fitness;
	private Individual representative;
	private Population pop;
	private Random rand;
	
	public Individual(Genotype genotype, Population pop) {
		this.genotype = genotype;
		this.pop = pop;
		rand = new Random();
	}
	
	public Individual(Population population){
		this.genotype = new Genotype();
		this.pop = population;
		this.rand = new Random();
		Random rand = new Random();
		for(int i = 0; i < InputNode.INPUTS_COUNT; i++){
			genotype.addNode(new InputNode(rand.nextDouble(), i));
		}
		for(int i = InputNode.INPUTS_COUNT; i < InputNode.INPUTS_COUNT + OutputNode.OUTPUTS_COUNT; i++){
			genotype.addNode(new OutputNode(rand.nextDouble(), i));
		}
		for(int i = 0; i < InputNode.INPUTS_COUNT; i++){
			for(int j = 0; j < OutputNode.OUTPUTS_COUNT; i++){
				genotype.addConnection(connect(i, InputNode.INPUTS_COUNT + j));
//				genotype.addConnection(new Connection(i, InputNode.INPUTS_COUNT + j, pop.getConnectionMark(new NodeTuple(i, InputNode.INPUTS_COUNT + j))));
			}
		}
	}
	
	public boolean addHiddenNodeToConnection(Connection con){
		if(genotype.disableConnection(con)){
			NodeTuple tuple = new NodeTuple(con.getStart(), con.getEnd());
			int nodeMark = pop.getNodeMark(tuple);
			HiddenNode inserted = new HiddenNode(1, nodeMark, nodeMark);
			if(genotype.addHiddenNode(inserted, con)){
				genotype.addConnection(new Connection(con.getStart(), nodeMark, pop.getConnectionMark(new NodeTuple(con.getStart(), nodeMark)), 1, false));
				genotype.addConnection(new Connection(nodeMark, con.getEnd(), pop.getConnectionMark(new NodeTuple(nodeMark, con.getEnd())), con.getWeight(), false));
				return true;
			}
		}
		return false;
	}

	public Genotype getGenotype() {
		return genotype;
	}

	public double getFitness() {
		return fitness;
	}

	public Individual getRepresentative() {
		return representative;
	}
	
	public double getRepresentativeFitness(){
		return representative.getFitness();
	}
	
	public boolean isSimilarTo(Individual other){
		double sum = 0;
		//TO DO
		
		if(sum < C_1) return true;
		return false;
	}
	
	public Connection connect(int start, int end){
		int mark = pop.getConnectionMark(new NodeTuple(start, end));
		
		return new Connection(start, end, mark, 1/2 + rand.nextGaussian(), false);
	}

	public void mutate(){
		if(rand.nextDouble() < PARAMETRIC_MUTATION_PROBABILITY){
			//Parametric mutation
			for(int i = 0; i < genotype.getGenotypeSize(); i++){
				if(rand.nextDouble() < PARAMETRIC_MUTATION_THRESHOLD) genotype.get(i).mutate(rand);
			}
		}else{
			//Structural mutation
			double prob = rand.nextDouble();
			int tries = 5;
			boolean success = false;
			if(prob < STRUCTURAL_MUTATION_ADDING_EDGE_PROBABILITY){
				//adding edge
				while(!success && tries > 0){
					int node1 = rand.nextInt(genotype.getNodeCount() - 2);
					int node2 = node1 + 1 + rand.nextInt(genotype.getNodeCount() - node1);
					success = genotype.addConnection(connect(genotype.get(node1).getMark(), genotype.get(node2).getMark()));
					tries--;
				}
			}else if(prob < STRUCTURAL_MUTATION_ADDING_NODE_PROBABILITY){
				//adding node
				while(!success && tries > 0){
					int index = genotype.getNodeCount() + rand.nextInt(genotype.getGenotypeSize() - genotype.getNodeCount());
					Connection con = (Connection) genotype.get(index);
					success = this.addHiddenNodeToConnection(con);
					tries--;
				}
			}else{
				//disable or enable edge
				int index = genotype.getNodeCount() + rand.nextInt(genotype.getGenotypeSize() - genotype.getNodeCount());
				Connection con = (Connection) genotype.get(index);
				if(con.isEnabled()){
					genotype.disableConnection(con);
				}else{
					genotype.enableConnection(con);
				}
			}
		}
	}
	
	public Individual mating(Individual parent2){
		Genotype offspring = new Genotype();
		int i1 = 0;
		int i2 = 0;
		boolean better;
		if(parent2.getRepresentativeFitness() < this.getRepresentativeFitness() || (parent2.getRepresentativeFitness() == this.getRepresentativeFitness() && 
				parent2.getFitness() < this.getFitness())){
			better = true;
		}else{
			better = false;
		}
		
		return null;
	}
	
//	public double getPenalty(double x, double y, double z){
//		// let us consider a line given by a point (START_X, START_Y, START_Z) and vector (VECTOR_X, VECTOR_Y, VECTOR_Z) 
//		// We want to calculate the distance between the line and point (x, y, z)
//		double START_X = 1;
//		double START_Y = 1;
//		double START_Z = 1;
//		double VECTOR_X = 1;
//		double VECTOR_Y = 1;
//		double VECTOR_Z = 1;
//		double x1 = x - START_X;
//		double y1 = y - START_Y;
//		double z1 = z - START_Z;
//		double t = ( VECTOR_X*x1 + VECTOR_Y*y1 + VECTOR_Z*z1 ) / (Math.pow(VECTOR_X, 2) + Math.pow(VECTOR_Y, 2) + Math.pow(VECTOR_Z, 2));
//		return Math.sqrt(Math.pow(t*VECTOR_X - x1, 2) + Math.pow(t*VECTOR_Y - y1, 2) + Math.pow(t*VECTOR_Z - z1, 2));
//	}
}
