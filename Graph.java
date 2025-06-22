package tree;

import list.ArrayList;

/**
 * Grafo dirigido y ponderado para Sistema de Gestión y Optimización de Inventarios en Almacenes.
 * Implementa CRUD de nodos y aristas, BFS, DFS, detección de ciclos, componentes conexas y Dijkstra.
 * @param <T> tipo de nodo
 */
public class Graph<T> {
    private class Edge {
        int toIndex;
        double weight;
        Edge(int toIndex, double weight) {
            this.toIndex = toIndex;
            this.weight = weight;
        }
    }

    private ArrayList<T> nodes;
    private ArrayList<ArrayList<Edge>> adj;

    /**
     * Construye un grafo vacío.
     */
    public Graph() {
        nodes = new ArrayList<>();
        adj = new ArrayList<>();
    }

    /**
     * Agrega un nodo si no existe.
     */
    public void addNode(T node) {
        if (!containsNode(node)) {
            nodes.add(node);
            adj.add(new ArrayList<Edge>());
        }
    }

    /**
     * Elimina un nodo y sus aristas.
     */
    public void removeNode(T node) {
        int idx = indexOf(node);
        if (idx < 0) return;
        // Eliminar lista de aristas
        nodes.remove(idx);
        adj.remove(idx);
        // Ajustar aristas entrantes
        for (int i = 0; i < adj.size(); i++) {
            ArrayList<Edge> edges = adj.get(i);
            for (int j = 0; j < edges.size(); j++) {
                Edge e = edges.get(j);
                if (e.toIndex == idx) {
                    edges.remove(j);
                    j--; // ajustar índice
                } else if (e.toIndex > idx) {
                    // corregir índice tras eliminación
                    e.toIndex--;
                }
            }
        }
    }

    /**
     * Comprueba existencia de nodo.
     */
    public boolean containsNode(T node) {
        return indexOf(node) >= 0;
    }

    private int indexOf(T node) {
        for (int i = 0; i < nodes.size(); i++) {
            if (nodes.get(i).equals(node)) return i;
        }
        return -1;
    }

    /**
     * Agrega una arista dirigida con peso.
     */
    public void addEdge(T from, T to, double weight) {
        int i = indexOf(from);
        int j = indexOf(to);
        if (i < 0 || j < 0) return;
        // Actualizar si existe
        ArrayList<Edge> edges = adj.get(i);
        for (int k = 0; k < edges.size(); k++) {
            if (edges.get(k).toIndex == j) {
                edges.get(k).weight = weight;
                return;
            }
        }
        // Insertar nueva
        edges.add(new Edge(j, weight));
    }

    /**
     * Remueve arista dirigida.
     */
    public void removeEdge(T from, T to) {
        int i = indexOf(from);
        int j = indexOf(to);
        if (i < 0 || j < 0) return;
        ArrayList<Edge> edges = adj.get(i);
        for (int k = 0; k < edges.size(); k++) {
            if (edges.get(k).toIndex == j) {
                edges.remove(k);
                return;
            }
        }
    }

    /**
     * Verifica existencia de arista.
     */
    public boolean containsEdge(T from, T to) {
        int i = indexOf(from);
        int j = indexOf(to);
        if (i < 0 || j < 0) return false;
        ArrayList<Edge> edges = adj.get(i);
        for (int k = 0; k < edges.size(); k++) {
            if (edges.get(k).toIndex == j) return true;
        }
        return false;
    }

    /**
     * Realiza recorrido BFS desde nodo inicial.
     */
    public ArrayList<T> bfs(T start) {
        ArrayList<T> order = new ArrayList<>();
        int s = indexOf(start);
        if (s < 0) return order;
        boolean[] visited = new boolean[nodes.size()];
        ArrayList<Integer> queue = new ArrayList<>();
        int head = 0;
        visited[s] = true;
        queue.add(s);
        while (head < queue.size()) {
            int u = queue.get(head++);
            order.add(nodes.get(u));
            ArrayList<Edge> edges = adj.get(u);
            for (int k = 0; k < edges.size(); k++) {
                int v = edges.get(k).toIndex;
                if (!visited[v]) {
                    visited[v] = true;
                    queue.add(v);
                }
            }
        }
        return order;
    }

    /**
     * Realiza recorrido DFS desde nodo inicial.
     */
    public ArrayList<T> dfs(T start) {
        ArrayList<T> order = new ArrayList<>();
        int s = indexOf(start);
        if (s < 0) return order;
        boolean[] visited = new boolean[nodes.size()];
        dfsUtil(s, visited, order);
        return order;
    }

    private void dfsUtil(int u, boolean[] visited, ArrayList<T> order) {
        visited[u] = true;
        order.add(nodes.get(u));
        ArrayList<Edge> edges = adj.get(u);
        for (int k = 0; k < edges.size(); k++) {
            int v = edges.get(k).toIndex;
            if (!visited[v]) dfsUtil(v, visited, order);
        }
    }

    /**
     * Detecta ciclo en grafo dirigido.
     */
    public boolean hasCycle() {
        int n = nodes.size();
        boolean[] visited = new boolean[n];
        boolean[] recStack = new boolean[n];
        for (int i = 0; i < n; i++) {
            if (!visited[i] && cycleUtil(i, visited, recStack)) return true;
        }
        return false;
    }

    private boolean cycleUtil(int u, boolean[] visited, boolean[] recStack) {
        visited[u] = true;
        recStack[u] = true;
        ArrayList<Edge> edges = adj.get(u);
        for (int k = 0; k < edges.size(); k++) {
            int v = edges.get(k).toIndex;
            if (!visited[v] && cycleUtil(v, visited, recStack)) return true;
            else if (recStack[v]) return true;
        }
        recStack[u] = false;
        return false;
    }

    /**
     * Obtiene componentes conexas (para grafo no dirigido se debe simular bidireccionalidad).
     */
    public ArrayList<ArrayList<T>> connectedComponents() {
        int n = nodes.size();
        boolean[] visited = new boolean[n];
        ArrayList<ArrayList<T>> comps = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                ArrayList<T> comp = new ArrayList<>();
                compDfs(i, visited, comp);
                comps.add(comp);
            }
        }
        return comps;
    }

    private void compDfs(int u, boolean[] visited, ArrayList<T> comp) {
        visited[u] = true;
        comp.add(nodes.get(u));
        ArrayList<Edge> edges = adj.get(u);
        for (int k = 0; k < edges.size(); k++) {
            int v = edges.get(k).toIndex;
            if (!visited[v]) compDfs(v, visited, comp);
        }
    }

    /**
     * Encuentra ruta más corta (Dijkstra) entre dos nodos.
     */
    public ArrayList<T> shortestPath(T src, T dst) {
        int n = nodes.size();
        int s = indexOf(src);
        int d = indexOf(dst);
        ArrayList<T> path = new ArrayList<>();
        if (s < 0 || d < 0) return path;
        double[] dist = new double[n];
        int[] prev = new int[n];
        boolean[] used = new boolean[n];
        for (int i = 0; i < n; i++) { dist[i] = Double.MAX_VALUE; prev[i] = -1; used[i] = false; }
        dist[s] = 0;
        for (int iter = 0; iter < n; iter++) {
            int u = -1;
            double best = Double.MAX_VALUE;
            for (int i = 0; i < n; i++) {
                if (!used[i] && dist[i] < best) { best = dist[i]; u = i; }
            }
            if (u < 0) break;
            used[u] = true;
            for (int k = 0; k < adj.get(u).size(); k++) {
                Edge e = adj.get(u).get(k);
                int v = e.toIndex;
                double w = e.weight;
                if (dist[u] + w < dist[v]) {
                    dist[v] = dist[u] + w;
                    prev[v] = u;
                }
            }
        }
        // Reconstruir camino
        int cur = d;
        while (cur >= 0) {
            path.add(0, nodes.get(cur));
            cur = prev[cur];
        }
        return path;
    }

    /**
     * Representación básica del grafo.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < nodes.size(); i++) {
            sb.append(nodes.get(i)).append(" -> ");
            ArrayList<Edge> edges = adj.get(i);
            for (int k = 0; k < edges.size(); k++) {
                Edge e = edges.get(k);
                sb.append(nodes.get(e.toIndex)).append("(").append(e.weight).append("), ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
