import java.util.Random;

/**
 * Evolves a tree by creating new generations through crossover.
 * 
 * @author Adithya Balasubramanian
 *
 */
public class Evolver {
	private GPTree[] newGenTrees;
	Generation g;
	DataSet d;

	public Evolver(Generation g, DataSet d, Random r) {
		this.g = g;
		this.d = d;
	}

	/**
	 * Chooses pairs of trees, proportional to their fitness, and crosses them
	 * over to make a new pair of children. These children are then stored in a
	 * new Generation object. This process is repeated until the new generation
	 * is complete.
	 * @see TestGPTree
	 * @return new generation of trees after crossover.
	 */
	public Generation makeNewGeneration() {
		GPTree[] treePair = new GPTree[g.getNumTrees()];
		Random rand = new Random();
		for (int i = 0; i < treePair.length; i++) {
			GPTree tree1 = g.chooseTreeProportionalToFitness().duplicate();
			tree1.eval(d);
			GPTree tree2 = g.chooseTreeProportionalToFitness().duplicate();
			tree2.eval(d);
			TestGPTree.crossover(tree1, tree2, rand);
			treePair[i] = tree1;
			if (i + 1 < treePair.length) {
				treePair[i + 1] = tree2;
				i++;
			}
		}
		Generation gen1 = new Generation(treePair);
		//gen1.numTrees = treePair.length;
		gen1.setNumTrees(treePair.length);
		gen1.evalAll(d);
		gen1.printBestTree();
		return gen1;
	}
}
