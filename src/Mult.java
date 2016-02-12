
public class Mult extends Binop {
	public Mult() {
	}

	public Mult(Node l, Node r) {
		super(l, r);
	}

	public double eval(double[] data) {
		return lChild.eval(data) * rChild.eval(data);
	}

	public String toString() {
		String s = new String();
		s += "(" + lChild.toString() + " * " + rChild.toString() + ")";
		return s;
	}

	public Node duplicate() {
		Mult alterEgo = new Mult();
		alterEgo.lChild = lChild.duplicate();
		alterEgo.rChild = rChild.duplicate();
		return alterEgo;
	}
}
