//-----------------------------------------------------
// Title: FlightOperationsManager Class
// Author: Bibi Arezo Massum
// ID: 99200552068
// Section: 02
// Assignment: 1
// Description: This program gives us the shortest path between two cities as well as number of cities in that path and final waiting time during this process … 
//----------------------------------------------------


//package algo2;
import java.util.*;

public class FlightOperationsManager {
    
    static class Edge {
        int dest, cost;
        Edge(int dest, int cost) {
            this.dest = dest;
            this.cost = cost;
        }
    }
    
    public static void main(String[] args) {
    	//reading the input from user
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();
        int t = sc.nextInt();
        int c = sc.nextInt();
        
        // Create adjacency list to represent the graph
        List<Edge>[] adjList = new List[n+1];
        for (int i = 1; i <= n; i++) {
            adjList[i] = new ArrayList<Edge>();
        }
        for (int i = 1; i <= m; i++) {
            int u = sc.nextInt();
            int v = sc.nextInt();
            adjList[u].add(new Edge(v, c));
            adjList[v].add(new Edge(u, c));
        }
        
        int x = sc.nextInt();
        int y = sc.nextInt();
        
        // Dijkstra's algorithm to find shortest path from city X to city Y
        int[] distance = new int[n+1];  //unknown distance from source to n+1
        int[] previous = new int[n+1];  //previous node in optimal path from source 
        boolean[] visited = new boolean[n+1]; //checking for nodes if its visited or not
        Arrays.fill(distance, Integer.MAX_VALUE);
        distance[x] = 0;  //distance of starting point from source
        //using priority queue to select the closest node to the set of already visited nodes.
        PriorityQueue<Integer> pq = new PriorityQueue<Integer>((a, b) -> distance[a] - distance[b]);
        pq.offer(x);
        while (!pq.isEmpty()) {
            int u = pq.poll();
            if (u == y) {
                break;
            }
            if (visited[u]) {
                continue;
            }
            visited[u] = true;
            for (Edge e : adjList[u]) {
                int v = e.dest;
                int cost = e.cost;
                //calculating waiting time
                int waitingTime = (distance[u] / t) % 2 == 0 ? 0 : t - (distance[u] % t);
                int newDistination = distance[u] + cost + waitingTime;
                if (newDistination < distance[v]) {
                    distance[v] = newDistination;
                    previous[v] = u;
                    pq.offer(v);
                }
            }
        }
        
        // Reconstruct path from city X to city Y
        int curr = y;
        List<Integer> path = new ArrayList<Integer>();
        while (curr != x) {
            path.add(curr);
            curr = previous[curr];
        }
        path.add(x);
        Collections.reverse(path);
        
        // Print output
        System.out.println(path.size());
        for (int i : path) {
            System.out.print(i + " ");
        }
        System.out.println();
        System.out.println(distance[y]);
    }
}
