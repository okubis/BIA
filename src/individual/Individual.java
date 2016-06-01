package individual;

import gene.*;
import neuroevo.NeatParameters;
import population.HistoricalMarkingManager;

import java.io.Serializable;
import java.util.Random;

public class Individual implements Serializable {
	private static final double SIMILARITY_THRESHOLD = 0.5;
	private static final double C_1 = 0.5;
	private static final double C_2 = 0.45;
	private static final double C_3 = 0.05;
	

	
	private Genotype genotype;
	private double fitness;
	private Individual representative;
	private HistoricalMarkingManager marks;
	private Random rand;
	
	public Individual(Genotype genotype, HistoricalMarkingManager m) {
		this.genotype = genotype;
		this.marks = m;
		rand = new Random();
	}

	public Individual(HistoricalMarkingManager m){
		this.genotype = new Genotype();
		this.marks = m;
		this.rand = new Random();
		for(int i = 0; i < InputNode.INPUTS_COUNT; i++){
			genotype.addNode(new InputNode(4, i));
//			genotype.addNode(new InputNode(rand.nextGaussian(), i));
		}
		for(int i = InputNode.INPUTS_COUNT; i < InputNode.INPUTS_COUNT + OutputNode.OUTPUTS_COUNT; i++){
			genotype.addNode(new OutputNode(1, i));
//			genotype.addNode(new OutputNode(rand.nextGaussian(), i));
		}
		for(int i = 0; i < InputNode.INPUTS_COUNT; i++){
			for(int j = 0; j < OutputNode.OUTPUTS_COUNT; j++){
				genotype.addConnection(connect(i, InputNode.INPUTS_COUNT + j, true));
//				genotype.addConnection(new Connection(i, InputNode.INPUTS_COUNT + j, pop.getConnectionMark(new NodeTuple(i, InputNode.INPUTS_COUNT + j))));
			}
		}
	}
	
	public void setFitness(double fitness){
		this.fitness = fitness;
	}
	
	public void setRepresentative(Individual representative){
		this.representative = representative;
	}
	
	public boolean addHiddenNodeToConnection(Connection con){
		if(genotype.disableConnection(con)){
			NodeTuple tuple = new NodeTuple(con.getStart(), con.getEnd());
			int nodeMark = marks.getNodeMark(tuple);
			HiddenNode inserted = new HiddenNode(4, nodeMark);
			if(genotype.addHiddenNode(inserted, con)){
				genotype.addConnection(new Connection(con.getStart(), nodeMark, marks.getConnectionMark(new NodeTuple(con.getStart(), nodeMark)), 1, false));
				genotype.addConnection(new Connection(nodeMark, con.getEnd(), marks.getConnectionMark(new NodeTuple(nodeMark, con.getEnd())), con.getWeight(), false));
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
		int excess = 0;
		int match = 0;
		double dif = 0;
		Genotype otherGen = other.getGenotype();
		for (int i = 0; i < otherGen.getGenotypeSize(); i++) {
			if(this.genotype.containsGene(otherGen.get(i))){
				excess++;
				if(otherGen.get(i).isConnection()){
					match++;
					Connection con = (Connection) otherGen.get(i);
					dif += Math.abs(con.getWeight() - genotype.getConnectionByMark(con.getMark()).getWeight() );
				}
			}
		}
		int n = otherGen.getGenotypeSize() + this.genotype.getGenotypeSize() - excess;
		int disjoint = n - excess;
		if(excess*C_1/n + disjoint*C_2/n + C_3*dif/match < SIMILARITY_THRESHOLD) return true;
		return false;
	}
	
	public Connection connect(int start, int end, boolean enabled){
		int mark = marks.getConnectionMark(new NodeTuple(start, end));
		
		return new Connection(start, end, mark, 1/2 + rand.nextGaussian(), enabled);
	}

	public void mutate(){
		if(rand.nextDouble() < NeatParameters.PARAMETRIC_MUTATION_PROBABILITY){
			//Parametric mutation
//			for(int i = genotype.getNodeCount(); i < genotype.getGenotypeSize(); i++){
			for(int i = 0; i < genotype.getGenotypeSize(); i++){
				if(rand.nextDouble() < NeatParameters.PARAMETRIC_MUTATION_THRESHOLD) genotype.get(i).mutate(rand);
			}
		}else{
			//Structural mutation
			double prob = rand.nextDouble();
			int tries = 9;
			boolean success = false;
			if(prob < NeatParameters.STRUCTURAL_MUTATION_ADDING_EDGE_PROBABILITY){
				//adding edge
				while(!success && tries > 0){
					int node1 = rand.nextInt(genotype.getNodeCount() - 2);
					int node2 = node1 + 1 + rand.nextInt(genotype.getNodeCount() - node1);
					success = genotype.addConnection(connect(genotype.get(node1).getMark(), genotype.get(node2).getMark(), false));
					tries--;
				}
			}else if(prob < NeatParameters.STRUCTURAL_MUTATION_ADDING_NODE_PROBABILITY){
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
	
	public Individual mating(Individual parent2, HistoricalMarkingManager hsm){
		Genotype offspring = new Genotype();
		Genotype better;
		Genotype worse;
		if (parent2.getRepresentativeFitness() > this.getRepresentativeFitness() || (parent2.getRepresentativeFitness() == this.getRepresentativeFitness() &&
				parent2.getFitness() < this.getFitness())){
			better = this.getGenotype();
			worse = parent2.getGenotype();
		}else{
			worse = this.getGenotype();
			better = parent2.getGenotype();
		}
		int betterNodes = better.getNodeCount();
		int worseNodes = worse.getNodeCount();
		int betterGenes = better.getGenotypeSize();
		int worseGenes = worse.getGenotypeSize();
		for (int i = 0; i < betterNodes; i++) {
			offspring.addNode( ((Node) better.get(i).clone()) );
		}
		for (int i = 0; i < worseNodes; i++) {
			offspring.addNode( ((Node) worse.get(i).clone()) );
		}
		int biggestMark = 0;
		for (int i = 0; i < offspring.getNodeCount(); i++) {
			if(biggestMark < offspring.get(i).getMark()) biggestMark = offspring.get(i).getMark();
		}
		boolean[][] reachable = new boolean[biggestMark + 1][biggestMark + 1];
		for (int i = betterNodes; i < betterGenes; i++) {
			Connection con = (Connection) better.get(i);
			if(!reachable[con.getStart()][con.getEnd()] && offspring.addConnectionRecurrent(con)){
				reachable[con.getEnd()][con.getStart()] = true;
				for (int j = 0; j < biggestMark; j++) {
					if(reachable[con.getStart()][j]){
						reachable[con.getEnd()][j] = true;
					}
				}
			}
		}
		for (int i = worseNodes; i < worseGenes; i++) {
			Connection con = (Connection) worse.get(i);
			if(!reachable[con.getStart()][con.getEnd()] && offspring.addConnectionRecurrent(con)){
				reachable[con.getEnd()][con.getStart()] = true;
				for (int j = 0; j < biggestMark; j++) {
					if(reachable[con.getStart()][j]){
						reachable[con.getEnd()][j] = true;
					}
				}
			}
		}
		offspring.sortNodes(betterNodes, reachable);
		return new Individual(offspring, hsm);
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder("Fitness: " + fitness + "\n");
		s.append(genotype.toString());
		return s.toString();
	}
	
	
	
}
