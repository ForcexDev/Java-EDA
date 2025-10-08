package AyudantiaPresolemne;

import java.util.*;

public class BipartiteGraph {
    public boolean isBipartite(int[][] graph) {
        int n = graph.length;
        int[] flag = new int[n];  
        Arrays.fill(flag, -1);  
        
        for (int i = 0; i < n; i++) {
            if (flag[i] == -1) {  
                if (!bfs(graph, flag, i)) {
                    return false; 
                }
            }
        }
        return true;  
    }

    // Función BFS para marcar nodos
    private boolean bfs(int[][] graph, int[] flag, int start) {
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(start);
        flag[start] = 0;  

        while (!queue.isEmpty()) {
            int node = queue.poll();
            for (int neighbor : graph[node]) {
                if (flag[neighbor] == -1) {  
                    flag[neighbor] = 1 - flag[node];  
                    queue.offer(neighbor);
                } else if (flag[neighbor] == flag[node]) {  
                    return false;  
                }
            }
        }
        return true;  
    }

    public static void main(String[] args) {
        BipartiteGraph solution = new BipartiteGraph();
        
        // Ejemplo 1      //Nodo 0, nodo 1, nodo 2, nodo 3;  Nodo 0: conectado a 1,2,3; nodo 1: conectado a 0,2; nodo 2: conectado a 0,1,3; nodo 3: conectado a 0,2;
        int[][] graph1 = {{1,2,3},{0,2},{0,1,3},{0,2}};
        System.out.println(solution.isBipartite(graph1));  // Output: false

        // Ejemplo 2
        int[][] graph2 = {{1,3},{0,2},{1,3},{0,2},{1,5},{4,2} };
        System.out.println(solution.isBipartite(graph2));  // Output: true
    }
}
