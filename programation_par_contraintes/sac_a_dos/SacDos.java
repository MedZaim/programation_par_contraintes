package sac_a_dos;

import ilog.concert.*;
import ilog.cplex.*;

public class SacDos {
    double[] c;
    double[] b;
    int n;
    double capacite;
    IloCplex modele;
    IloNumVar[] x;

    public SacDos(double[] c, double[] b, double capacite) {
        this.c = c;
        this.b = b;
        n = c.length;
        this.capacite = capacite;
        try {
            modele = new IloCplex();
            createModele();
            System.out.println(modele.toString());
        } catch (IloException e) {
            System.err.println(" Concert exception caught : " + e);
        }

    }

    public void createModele() {
        try {
            x = modele.boolVarArray(n);
            IloLinearNumExpr lin = modele.scalProd(x, b);
            modele.addLe(lin, capacite);
            IloLinearNumExpr fon = modele.scalProd(x, c);
            modele.addMaximize(fon);
        } catch (IloException e) {
            System.err.println(" Concert exception caught : " + e);
        }
    }

    public boolean solve() {
        boolean hasSolved = false;
        try {
            hasSolved = modele.solve();
        } catch (IloException e) {
            System.err.println(" Concert exception caught : " + e);
        }
        return hasSolved;
    }

    public double[] getSolution() {
        double[] s = new double[n];
        try {
            s = modele.getValues(x);
        } catch (IloException ex) {
            System.err.println(" Concert exception caught : " + ex);
        }
        return s;
    }

    public void getsolutionSac() {
        if (solve()) {
            double[] d = getSolution();
            System.out.println(" Les objets dans le sac:");
            for(int i = 0; i < d.length; i++){
                if (d[i] == 1)
                System.out.println("Obj[" + (i + 1) + "]");
            }
        }
    }

}