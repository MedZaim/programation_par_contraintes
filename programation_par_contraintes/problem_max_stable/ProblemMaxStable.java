package problem_max_stable;

import ilog.concert.IloException;
import ilog.concert.IloLinearNumExpr;
import ilog.concert.IloNumVar;
import ilog.cplex.IloCplex;

import java.nio.file.Path;
import java.nio.file.Paths;

public class ProblemMaxStable {
    private short[][] C;
    private int n;
    private IloCplex modele;
    private IloNumVar[] x;

    public ProblemMaxStable(short[][] C) {
        this.C = C;
        this.n = C.length;
        try {
            modele = new IloCplex();
            createModele();
            createVariables();
            createConstraints();
            createFonctionObj();
            if (solve()) {
                printSolution();
            } else {
                System.out.println("No solution found.");
            }
        } catch (IloException e) {
            System.err.println("Concert exception caught: " + e);
        }
    }

    private void createModele() {
        try {
            // Initialize the model
            System.out.println("Model created successfully.");
        } catch (Exception e) {
            System.err.println("Error creating model: " + e);
        }
    }

    private void createVariables() {
        try {
            x = modele.boolVarArray(n); // Create boolean decision variables
        } catch (IloException e) {
            throw new RuntimeException("Error creating variables: " + e);
        }
    }

    private void createConstraints() {
        try {
            for (int i = 0; i < n; i++) {
                for (int j = i+1; j < n; j++) {
                    if (C[i][j] == 1) { // Assuming adjacency matrix representation
                        IloLinearNumExpr lin = modele.linearNumExpr();
                        lin.addTerm(1.0, x[i]);
                        lin.addTerm(1.0, x[j]);
                        modele.addLe(lin, 1.0); // Constraint: Adjacent nodes can't both be selected
                    }
                }
            }
        } catch (IloException e) {
            throw new RuntimeException("Error creating constraints: " + e);
        }
    }

    private void createFonctionObj() {
        try {
            double[] weights = new double[n];
            for (int i = 0; i < n; i++) {
                weights[i] = 1.0; // Default weight for each node
            }
            IloLinearNumExpr fon = modele.scalProd(x, weights); // Maximize sum of selected nodes
            modele.addMaximize(fon);
        } catch (IloException e) {
            throw new RuntimeException("Error creating objective function: " + e);
        }
    }

    private boolean solve() {
        try {
            return modele.solve();
        } catch (IloException e) {
            System.err.println("Error solving the model: " + e);
            return false;
        }
    }

    private double[] getSolution() {
        try {
            return modele.getValues(x);
        } catch (IloException e) {
            System.err.println("Error retrieving solution: " + e);
            return new double[0];
        }
    }

    private void printSolution() {
        double[] solution = getSolution();
        System.out.println("Maximum stable set:");
        for (int i = 0; i < solution.length; i++) {
            if (solution[i] == 1.0) {
                System.out.println("Node[" + i + "] is part of the stable set.");
            }
        }
    }

    public static void main(String[] args) {
        Path path = Paths.get("C:\\Users\\lenovo\\Desktop\\MLAIM\\programation_par_contraintes\\MSSP1\\brock200_1.clq");
        short[][] adjacencyMatrix = Matrix.loadMatrix(path.toString());
        //display the adjacency matrix
        System.out.println("Loaded adjacency matrix:");
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            for (int j = 0; j < adjacencyMatrix[i].length; j++) {
                System.out.print(adjacencyMatrix[i][j] + " ");
            }
            System.out.println();
        }

        ProblemMaxStable problemMaxStable = new ProblemMaxStable(adjacencyMatrix);

        problemMaxStable.printSolution();
    }
}
