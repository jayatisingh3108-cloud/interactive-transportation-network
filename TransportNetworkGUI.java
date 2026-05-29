import javax.swing.*;
import java.awt.*;
import java.util.*;
public class TransportNetworkGUI {
    static ArrayList<String> cities = new ArrayList<>();
    static ArrayList<int[]> edges = new ArrayList<>();
    static JTextArea output;
    // Print function
    static void print(String s) {
        output.append(s + "\n");}
    // ================= DIJKSTRA =================
    static void dijkstra(int src) {
        int n = cities.size();
        int[] dist = new int[n];
        boolean[] vis = new boolean[n];

        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[src] = 0;
        for (int i = 0; i < n; i++) {
            int u = -1;
            for (int j = 0; j < n; j++)
                if (!vis[j] && (u == -1 || dist[j] < dist[u]))
                    u = j;
            vis[u] = true;
            for (int[] e : edges) {
                if (e[0] == u && dist[u] + e[2] < dist[e[1]])
                    dist[e[1]] = dist[u] + e[2];
                if (e[1] == u && dist[u] + e[2] < dist[e[0]])
                    dist[e[0]] = dist[u] + e[2];}}
        print("\n=== SHORTEST PATHS ===");
        for (int i = 0; i < n; i++)
            print(cities.get(i) + " : " + dist[i] + " km");}
    // ================= BFS =================
    static void bfs(int src) {
        boolean[] vis = new boolean[cities.size()];
        Queue<Integer> q = new LinkedList<>();

        q.add(src);
        vis[src] = true;

        print("\n=== BFS TRAVERSAL ===");

        while (!q.isEmpty()) {
            int u = q.poll();
            print(cities.get(u));

            for (int[] e : edges) {
                int nb = (e[0] == u) ? e[1] : (e[1] == u ? e[0] : -1);
                if (nb != -1 && !vis[nb]) {
                    vis[nb] = true;
                    q.add(nb);
                }
            }
        }
    }

    // ================= REACHABILITY =================
    static void reachability(int src) {
        boolean[] vis = new boolean[cities.size()];
        Queue<Integer> q = new LinkedList<>();

        q.add(src);
        vis[src] = true;

        while (!q.isEmpty()) {
            int u = q.poll();
            for (int[] e : edges) {
                int nb = (e[0] == u) ? e[1] : (e[1] == u ? e[0] : -1);
                if (nb != -1 && !vis[nb]) {
                    vis[nb] = true;
                    q.add(nb);
                }
            }
        }

        print("\n=== REACHABILITY ===");
        boolean connected = true;

        for (int i = 0; i < vis.length; i++) {
            print(cities.get(i) + " : " + (vis[i] ? "YES" : "NO"));
            if (!vis[i]) connected = false;
        }

        print(connected ? "CONNECTED" : "DISCONNECTED");
    }

    // ================= PRIM'S MST =================
    static void prims() {
        int n = cities.size();
        int[] key = new int[n];
        int[] parent = new int[n];
        boolean[] mst = new boolean[n];

        Arrays.fill(key, Integer.MAX_VALUE);
        key[0] = 0;
        parent[0] = -1;

        for (int i = 0; i < n; i++) {
            int u = -1;
            for (int j = 0; j < n; j++)
                if (!mst[j] && (u == -1 || key[j] < key[u]))
                    u = j;

            mst[u] = true;

            for (int[] e : edges) {
                if (e[0] == u && !mst[e[1]] && e[2] < key[e[1]]) {
                    key[e[1]] = e[2];
                    parent[e[1]] = u;
                }
                if (e[1] == u && !mst[e[0]] && e[2] < key[e[0]]) {
                    key[e[0]] = e[2];
                    parent[e[0]] = u;
                }
            }
        }

        print("\n=== MIN COST NETWORK ===");
        int total = 0;

        for (int i = 1; i < n; i++) {
            print(cities.get(parent[i]) + " - " + cities.get(i) + " : " + key[i] + " km");
            total += key[i];
        }

        print("Total Cost = " + total + " km");
    }

    // ================= MAIN =================
    public static void main(String[] args) {

        JFrame frame = new JFrame("Transportation Network");
        frame.setSize(600, 450);

        output = new JTextArea();
        output.setEditable(false);

        frame.add(new JScrollPane(output));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // DATA
        cities.addAll(Arrays.asList("Delhi","Mumbai","Kolkata","Chennai","Hyderabad","Bangalore"));

        edges.add(new int[]{0,1,1400});
        edges.add(new int[]{0,2,900});
        edges.add(new int[]{0,4,1500});
        edges.add(new int[]{1,4,700});
        edges.add(new int[]{1,5,980});
        edges.add(new int[]{2,3,1650});
        edges.add(new int[]{3,4,630});
        edges.add(new int[]{3,5,350});
        edges.add(new int[]{4,5,570});

        int src = 0;

        dijkstra(src);
        bfs(src);
        reachability(src);
        prims();
    }
}
