package problem_max_stable;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Matrix {
    // Load the graph matrix from a txt file
    public static short[][] loadMatrix(String path) {
        short[][] matrix = null;

        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            int size = 0;

            // Read the file line by line
            while ((line = reader.readLine()) != null) {
                // Skip comment lines
                if (line.startsWith("c")) {
                    continue;
                }

                // Read the size of the matrix
                if (line.startsWith("p edge")) {
                    String[] parts = line.split(" ");
                    size = Integer.parseInt(parts[2]);
                    matrix = new short[size][size];
                }

                // Read the edges
                if (line.startsWith("e")) {
                    String[] parts = line.split(" ");
                    int node1 = Integer.parseInt(parts[1]) - 1;
                    int node2 = Integer.parseInt(parts[2]) - 1;
                    matrix[node1][node2] = 1;
                    matrix[node2][node1] = 1;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return matrix;
    }
}
