import java.util.*;
import java.text.*;

/** A container of GPTrees. */
public class Generation {
	private int numTrees;

	public int getNumTrees() {
		return numTrees;
	}

	public void setNumTrees(int numTrees) {
		this.numTrees = numTrees;
	}

	private GPTree[] population;
	private double[] fitness;

	Generation() {

	}

	Generation(GPTree[] treeArray) {
		population = treeArray;
	}

	/**
	 * Specifies the number of GPTrees in this generation, the factories used to
	 * generate the individual trees, the maximum tree depth, and the Random
	 * object used.
	 */
	Generation(int numTrees, OperatorFactory o, TerminalFactory t, int m,
			Random r) {
		this.numTrees = numTrees;
		population = new GPTree[numTrees];
		for (int i = 0; i < numTrees; i++)
			population[i] = new GPTree(o, t, m, r);
	}

	/**
	 * Evaluate each tree in this generation, and store reciprocal of each
	 * tree's fitness value to an array
	 */
	public void evalAll(DataSet d) {
		fitness = new double[numTrees];
		for (int i = 0; i < numTrees; i++) {
			double temp = population[i].eval(d);
			fitness[i] = 1 / temp;
		}
	}

	/**
	 * Print the symbolicEval of each tree, and its fitness value. Very lengthy!
	 */
	public void printAll() {
		for (int i = 0; i < numTrees; i++) {
			System.out.println(population[i]);
			System.out.println("This tree's fitness is "
					+ NumberFormat.getInstance().format(
							population[i].getFitness()));
		}
	}

	/** Prints the tree with the lowest fitness value. */
	public void printBestTree() {
		Arrays.sort(population);
		// printAll();
		System.out.println(population[0]);
		System.out.println("Its fitness is " + population[0].getFitness());
	}

	/**
	 * Return tree through Roulette Wheel selection. Records the cumulative
	 * fitness. It does not matter if the population is sorted. These cumulative
	 * scores are sorted by itself as each fitness it contains is greater than
	 * the previous one. The probability of selecting a tree is directly
	 * proportional to tree's fitness score. In other words, the greater the
	 * difference between the probability of corresponding cumulative fitness,
	 * the tree is more probable to be selected. (Note: As selection is done
	 * with replacement, the same tree may be selected many times.)
	 * 
	 * @return tree selected through Roulette Wheel selection.
	 */
	public GPTree chooseTreeProportionalToFitness() {
		double cumulativeFitness[] = new double[population.length];
		cumulativeFitness[0] = fitness[0];
		for (int i = 1; i < population.length; i++) {
			cumulativeFitness[i] = cumulativeFitness[i - 1] + fitness[i];
		}
		Random r = new Random();
		// generating a random number between 0 and last value in
		// cumulativefitness
		double randomFitness = r.nextDouble()
				* cumulativeFitness[cumulativeFitness.length - 1];
		int index = Arrays.binarySearch(cumulativeFitness, randomFitness);
		if (index < 0) {// if index is less than 0, then index = insertion point
			index = Math.abs(index) - 1;
		}
		return population[index];
	}
}
