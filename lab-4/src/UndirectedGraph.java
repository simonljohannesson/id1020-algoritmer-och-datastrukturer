/*
 *  Author:         Simon Johannesson
 *  Email:          simonljohannesson@gmail.com, sijohann@kth.se
 *  Created:        2020-10-01
 *  Updated:
 *  Solves problem: Lab 4
 *  Usage:          Compile and use with API.
 *
 *  Dependencies:   Bag
 *  (own classes)
 *
 *  Based on:       https://algs4.cs.princeton.edu/41graph/Graph.java.html
 */
public class UndirectedGraph {
    private final int VERTICES;
    private int edges;
    private Bag<Integer>[] adj;

    /**
     * Initialize an empty graph with V vertices and 0 edges.
     */
    UndirectedGraph(int V){
        if(V < 0) throw new IllegalArgumentException();
        this.VERTICES = V;
        adj = (Bag<Integer>[]) new Bag[V];
        for(int i = 0; i < V; i++){
            adj[i] = new Bag<Integer>();
        }
    }
    // throws exception if entered vertex is invalid
    private void validateVertex(int vertex){
        if(0 > vertex  ||  vertex >= VERTICES) throw new IllegalArgumentException(
                "Vertex cannot be: " + vertex + " number of vertices in graph: " + VERTICES);
    }
    /**
     * Add undirected edge to graph.
     */
    public void addEdge(int v, int w){
        validateVertex(v);
        validateVertex(w);
        edges++;
        adj[v].add(w);
        adj[w].add(v);
    }
    /**
     * Return vertices adjacent to vertex.
     */
    public Iterable<Integer> adjacent(int vertex){
        validateVertex(vertex);
        return adj[vertex];
    }
    /**
     * Return degree of vertex.
     */
    public int degree(int vertex){
        validateVertex(vertex);
        return adj[vertex].size();
    }

    /**
     * Return number of edges in graph.
     */
    public int edges(){
        return edges;
    }

    /**
     * Return number of vertices in graph.
     */
    public int vertices(){
        return VERTICES;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Vertices: " + VERTICES + " \nEdges: " + edges + "\n");
        for (int i = 0; i < VERTICES; i++){
            sb.append(i + " : ");
            for(Integer edge : adj[i]){
                sb.append(edge + " ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
    public static void main(String[] args){
        UndirectedGraph g = new UndirectedGraph(10);
        g.addEdge(0,9);
        System.out.println(g);

    }

}
