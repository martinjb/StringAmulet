package Engine;

public interface intEngine {
	int[] supplyValues();
	int computeCost();
	String computeResults();
	int[] computeExp();
	int lvlFromExp(int exp);
	double costPerExp();
	
}
