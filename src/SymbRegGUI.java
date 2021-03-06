
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

import javax.swing.border.*;

import java.util.*;

public class SymbRegGUI extends JFrame {
	JButton openButton = new JButton("Choose Data File");
    JLabel filenameLabel = new JLabel("Current File");
    JTextField filename = new JTextField(20);
    JTextArea fileContents = new JTextArea(7, 10);
    JScrollPane fileScrollPane = new JScrollPane(fileContents);
    JButton RunButton = new JButton("Run Symbolic Regression");
    JTextArea output = new JTextArea(20, 40);
    JScrollPane outputScrollPane = new JScrollPane(output);


    // Parameters group
    JLabel treesPerGenLabel = new JLabel("Trees Per Generation");
    int treesPerGenVal;
	JTextField treesPerGen  = new JTextField("500", 5);

    JLabel numGensLabel = new JLabel("Number of Generations");
    int numGensVal;
	JTextField numGens  = new JTextField("20", 5);

    JLabel randomSeedLabel = new JLabel("Random Number Seed");
    int seedVal;
	JTextField seed  = new JTextField("100", 5);

    // operators group
    JLabel operatorsLabel = new JLabel("Choose Operators");
	JCheckBox plus = new JCheckBox("Plus");
    boolean plusVal;
    JCheckBox minus = new JCheckBox("Minus");
    boolean minusVal;
    JCheckBox mult = new JCheckBox("Mult");
    boolean multVal;
    JCheckBox divide = new JCheckBox("Divide");
    boolean divideVal;


    public SymbRegGUI() {
        setTitle("Symbolic Regression");
        Box fileOpenDisplayBox = Box.createVerticalBox();
        openButton.addActionListener(new OpenL());
        fileOpenDisplayBox.add(openButton);
        fileOpenDisplayBox.add(Box.createVerticalStrut(4));
        fileOpenDisplayBox.add(filenameLabel);
        fileOpenDisplayBox.add(filename);
        fileOpenDisplayBox.add(Box.createVerticalStrut(4));
        fileOpenDisplayBox.add(fileScrollPane);
        JPanel fileOpenDisplayPanel = new JPanel();
        fileOpenDisplayPanel.add(fileOpenDisplayBox);
        Border brd = BorderFactory.createMatteBorder(1,1,1,1, Color.black);
        fileOpenDisplayPanel.setBorder(brd);

        // a Box containing 3 Boxes
		Box parametersBox = Box.createVerticalBox();
        parametersBox.add(Box.createHorizontalStrut(15));
        	Box treesPerGenBox = Box.createHorizontalBox();
				treesPerGenBox.add(treesPerGenLabel);
                //treesPerGenBox.add(Box.createHorizontalStrut(100));
                treesPerGen.setMaximumSize(new Dimension(50,20));
                treesPerGenBox.add(Box.createHorizontalGlue());
            	treesPerGenBox.add(treesPerGen);
                treesPerGen.setHorizontalAlignment(JTextField.TRAILING);
				treesPerGen.addActionListener(new treesPerGenL());
        	parametersBox.add(treesPerGenBox);
        	Box numGensBox = Box.createHorizontalBox();
				numGensBox.add(numGensLabel);
                numGens.setMaximumSize(new Dimension(50,20));
                numGensBox.add(Box.createHorizontalGlue());
              	numGensBox.add(numGens);
                numGens.setHorizontalAlignment(JTextField.TRAILING);
                numGens.addActionListener(new numGensL());
        	parametersBox.add(numGensBox);
        	Box randomSeedBox = Box.createHorizontalBox();
				randomSeedBox.add(randomSeedLabel);
                seed.setMaximumSize(new Dimension(50,20));
                randomSeedBox.add(Box.createHorizontalGlue());
            	randomSeedBox.add(seed);
                seed.setHorizontalAlignment(JTextField.TRAILING);
                seed.addActionListener(new seedL());
        	parametersBox.add(randomSeedBox);
            JPanel parametersPanel = new JPanel();
            parametersPanel.add(parametersBox);
			Border paramsBrd = BorderFactory.createMatteBorder(1,1,1,1, Color.black);
       		parametersPanel.setBorder(paramsBrd);

        // a Box inside a JPanel
		Box operatorsBox = Box.createVerticalBox();
        	operatorsBox.add(Box.createHorizontalStrut(15));
        	operatorsBox.add(Box.createVerticalStrut(3));
        	operatorsBox.add(operatorsLabel);
        	operatorsBox.add(plus);
            plus.setSelected(true);
            plus.addActionListener(new plusL());
        	operatorsBox.add(minus);
            minus.setSelected(true);
            minus.addActionListener(new minusL());
        	operatorsBox.add(mult);
            mult.setSelected(true);
            mult.addActionListener(new multL());
        	operatorsBox.add(divide);
            divide.setSelected(true);
            divide.addActionListener(new divideL());
        	operatorsBox.add(Box.createHorizontalStrut(15));
        JPanel operatorsPanel = new JPanel();
		operatorsPanel.add(operatorsBox);
        Border opsBrd = BorderFactory.createMatteBorder(1,1,1,1, Color.black);
        operatorsPanel.setBorder(opsBrd);
		Box paramsAndOpsBox = Box.createVerticalBox();
        paramsAndOpsBox.add(parametersPanel);
        paramsAndOpsBox.add(operatorsPanel);
        JPanel paramsAndOpsPanel = new JPanel();
        paramsAndOpsPanel.add(paramsAndOpsBox);
        JPanel outputPanel = new JPanel();
        Box outputBox = Box.createVerticalBox();
        RunButton.addActionListener(new DisplayOutput());
        outputBox.add(RunButton);
        outputBox.add(outputScrollPane);
        outputPanel.add(outputBox);
        Container cp = getContentPane();
        cp.setLayout(new FlowLayout());
        cp.add(fileOpenDisplayPanel);
        cp.add(paramsAndOpsPanel);
        cp.add(outputPanel);
    }
//test
	class OpenL implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JFileChooser c = new JFileChooser("C:/Users/Adithya/workspace/CrossoverForStudents/Data2.dat");
			int rVal = c.showOpenDialog(SymbRegGUI.this);
			if (rVal == JFileChooser.APPROVE_OPTION) {
				String longName = new String(c.getCurrentDirectory().toString()
                    + "\\" + c.getSelectedFile().getName());
				filename.setText(longName);
                SimpleInput si = new SimpleInput(longName);
                String line;
                while ((line = si.nextLine()) != null) {
                    fileContents.append(line);
                    fileContents.append("\n");
				}
			}
			if (rVal == JFileChooser.CANCEL_OPTION) {
				filename.setText("");
			}
		}
	}
	
	class DisplayOutput implements ActionListener {
		Random rand = new Random();
		public void actionPerformed(ActionEvent e) {
				DataSet ds = new DataSet(filename.getText());
				Node[] ops = { new Plus(), new Minus(), new Mult(), new Divide() };
				OperatorFactory o = new OperatorFactory(ops);
				TerminalFactory t = new TerminalFactory(ds.numIndepVars());
				Generation gen1 = new Generation(3, o, t, 5, rand);
				gen1.evalAll(ds);
				gen1.printBestTree();
				for (int i = 1; i <= 5; i++) {
					Evolver e1 = new Evolver(gen1, ds, rand);
					gen1 = e1.makeNewGeneration();
					output.append(gen1.chooseTreeProportionalToFitness().toString());
					output.append("\n");
				}
		}
	}


    class treesPerGenL implements ActionListener {
        public void actionPerformed(ActionEvent e) {
			treesPerGenVal = new Integer(treesPerGen.getText()).intValue();
            String s = new String();
            s += "Changed Trees Per Generation to " + treesPerGenVal + "\n";
            System.out.print(s);
        }
	}
    class numGensL implements ActionListener {
        public void actionPerformed(ActionEvent e) {
			numGensVal = new Integer(numGens.getText()).intValue();
            String s = new String();
            s += "Changed Number of Generations to " + numGensVal + "\n";
            System.out.print(s);  	// use for debugging
        }
	}
    class seedL implements ActionListener {
        public void actionPerformed(ActionEvent e) {
			seedVal = new Integer(seed.getText()).intValue();
            String s = new String();
            s += "Changed Random Number Seed to " + seedVal + "\n";
            System.out.print(s);	// use for debugging
        }
	}

    class plusL implements ActionListener {
        public void actionPerformed(ActionEvent e) {
			plusVal = plus.isSelected();
            String s = new String();
            s += "Plus is ";
            if (!plusVal)
                s += "not ";
            s += "selected" + "\n";
            System.out.print(s);	// use for debugging
        }
	}

    class minusL implements ActionListener {
        public void actionPerformed(ActionEvent e) {
			minusVal =minus.isSelected();
            String s = new String();
            s += "Minus is ";
            if (!minusVal)
                s += "not ";
            s += "selected" + "\n";
            System.out.print(s);	// use for debugging
        }
	}

    class multL implements ActionListener {
        public void actionPerformed(ActionEvent e) {
			multVal =mult.isSelected();
            String s = new String();
            s += "Mult is ";
            if (!multVal)
                s += "not ";
            s += "selected" + "\n";
            System.out.print(s);	// use for debugging
        }
	}

    class divideL implements ActionListener {
        public void actionPerformed(ActionEvent e) {
			divideVal = divide.isSelected();
            String s = new String();
            s += "Divide is ";
            if (!divideVal)
                s += "not ";
            s += "selected" + "\n";
            System.out.print(s);	// use for debugging
        }
	}

    public static void main(String[] args) {
//        try {
//            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//        } catch(Exception e){}

    	Console.run(new SymbRegGUI(), 600, 600);
    }
}
