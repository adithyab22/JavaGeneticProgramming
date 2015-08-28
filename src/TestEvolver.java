import java.util.Random;

/**
 * Implementation class for Evolver. Print the fitness of the best tree in the
 * first generation, then evolve 5 successive generations, printing the fitness
 * of the best tree of each generation.
 * 
 * @author Adithya Balasubramanian
 *
 */
public class TestEvolver {

	static int maxDepth = 5;
	static int numTrees = 3;
	static Random rand = new Random();

	public static void main(String[] args) {
		DataSet ds = new DataSet(
				"C:/Users/Adithya/workspace/CrossoverForStudents/Data2.dat");
		Node[] ops = { new Plus(), new Minus(), new Mult(), new Divide() };
		OperatorFactory o = new OperatorFactory(ops);
		TerminalFactory t = new TerminalFactory(ds.numIndepVars());
		Generation gen1 = new Generation(numTrees, o, t, maxDepth, rand);
		gen1.evalAll(ds);
		gen1.printBestTree();
		for (int i = 1; i <= 5; i++) {
			Evolver e = new Evolver(gen1, ds, rand);
			gen1 = e.makeNewGeneration();
		}
	}
}
