package code.geeksforgeeks.dp;

// https://www.geeksforgeeks.org/floyd-warshall-algorithm-dp-16/
public class FloyWarshall {
    public int[][] floydWarshall(int[][] graph) {
        // result[][] will be the output matrix
        // that will finally have the shortest
        // distances between every pair of vertices */
        int[][] result = new int[graph.length][graph.length];
        int i, j, k;

        // Initialize the solution matrix
        // same as input graph matrix.
        // Or we can say the initial values
        // of shortest distances are based
        // on shortest paths considering
        // no intermediate vertex.
        for (i = 0; i < graph.length; i++)
            for (j = 0; j < graph.length; j++)
                result[i][j] = graph[i][j];

        // Then we update the solution matrix by considering all vertices as an intermediate vertex.
        // The idea is to one by one pick all vertices
        // and updates all shortest paths which include the picked vertex
        // as an intermediate vertex in the shortest path.
        for (k = 0; k < graph.length; k++) {
            // Pick all vertices as source one by one
            for (i = 0; i < graph.length; i++) {
                // Pick all vertices as destination for the
                // above picked source
                for (j = 0; j < graph.length; j++) {
                    // If vertex k is on the shortest path from
                    // i to j, then update the value of result[i][j]
                    if (result[i][k] + result[k][j] < result[i][j])
                        result[i][j] = result[i][k] + result[k][j];
                }
            }
        }
        return result;
    }
}
