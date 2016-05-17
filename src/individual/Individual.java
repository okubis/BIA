package individual;

public class Individual {
	private static final double SIMILARITY_THRESHOLD = 0.4;
	
	private Genotype genotype;
	private double fitness;
	private Individual representative;
	
	public Individual(Genotype genotype) {
		this.genotype = genotype;
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
		
		if(sum < SIMILARITY_THRESHOLD) return true;
		return false;
	}
	
//	public double getPenalty(double x, double y, double z){
//		// let us consider a line given by a point xp, yp, zp and vector xv, yv, zv 
//		// We want to calculate the distance between the line and point x, y, z
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
