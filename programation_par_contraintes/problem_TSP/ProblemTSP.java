/**
 * @author : Mohamed Zaim
 * @mailto : mohamed.zaim97@gmail.com
 * @created : 04/12/2024, Wednesday
 **/


package problem_TSP;
import ilog.concert.IloException;
import ilog.concert.IloLinearNumExpr;
import ilog.concert.IloNumVar;
import ilog.cplex.IloCplex;

/**
 @author lenovo
 **/
public class ProblemTSP {
    private double[][] c;
    private int nbVilles;
    private IloCplex modele;
    private IloNumVar[][] x;

    public ProblemTSP(double[][] c) {
        this.c = c;
        this.nbVilles = c.length;
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

    private void printSolution() {
        try {
            System.out.println("Solution status: " + modele.getStatus());
            System.out.println("Solution value: " + modele.getObjValue());
            for (int i = 0; i < nbVilles; i++) {
                for (int j = 0; j < nbVilles; j++) {
                    if (modele.getValue(x[i][j]) == 1) {
                        System.out.println("Ville " + i + " -> Ville " + j);
                    }
                }
            }
        } catch (IloException e) {
            System.err.println("Concert exception caught: " + e);
        }
    }

    private boolean solve() {
        boolean hasSolved = false;
        try {
            hasSolved = modele.solve();
        } catch (IloException e) {
            System.err.println("Concert exception caught: " + e);
        }
        return hasSolved;
    }

    private void createModele() {
        try {
            // Initialize the model
            System.out.println("Model created successfully.");
        } catch (Exception e) {
            System.err.println("Error creating model: " + e);
        }
    }

    private IloNumVar[][] createVariables() {
        try {
            x = new IloNumVar[nbVilles][nbVilles]; // Create boolean decision variables
            for (int i = 0; i < nbVilles; i++) {
                    x[i] = modele.boolVarArray(nbVilles);
            }
        } catch (IloException e) {
            throw new RuntimeException("Error creating variables: " + e);
        }
        return x;
    }

    private void createConstraints() {
        try {
            for (int i = 0; i < nbVilles; i++) {
                IloLinearNumExpr lin = modele.linearNumExpr();
                for (int j = 0; j < nbVilles; j++) {
                    lin.addTerm(1.0, x[i][j]);
                }
                modele.addEq(lin, 1.0);
            }

            for (int j = 0; j < nbVilles; j++) {
                IloLinearNumExpr lin = modele.linearNumExpr();
                for (int i = 0; i < nbVilles; i++) {
                    lin.addTerm(1.0, x[i][j]);
                }
                modele.addEq(lin, 1.0);
            }
        } catch (IloException e) {
            throw new RuntimeException("Error creating constraints: " + e);
        }
    }

    private IloLinearNumExpr createFonctionObj() {
        try {
            IloLinearNumExpr fon = modele.linearNumExpr();
            for (int i = 0; i < nbVilles; i++) {
                for (int j = 0; j < nbVilles; j++) {
                    fon.addTerm(c[i][j], x[i][j]);
                }
            }
            return fon;
        } catch (IloException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        double[][] c = {
                {0, 10, 15, 20,25},
                {10, 0, 35, 25,15},
                {15, 35, 0, 30,20},
                {20, 25, 30, 0, 10},
                {25, 15, 20, 10, 0}
        };
        ProblemTSP tsp = new ProblemTSP(c);
        tsp.printSolution();
    }
}