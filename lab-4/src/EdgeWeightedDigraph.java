import java.io.File;
import java.io.FileNotFoundException;
import java.util.IllegalFormatException;
import java.util.InputMismatchException;
import java.util.Scanner;

/*
 *  Author:         Simon Johannesson
 *  Email:          simonljohannesson@gmail.com, sijohann@kth.se
 *  Created:        2020-10-02
 *  Updated:
 *  Solves problem: Lab 4
 *  Usage:          Compile and use with API.
 *
 *  Dependencies:   Bag
 *  (own classes)
 *
 *  Based on:       https://algs4.cs.princeton.edu/44sp/EdgeWeightedDigraph.java.html
 */
public class EdgeWeightedDigraph{
    private final int VERTICES;
    private int edges;
    private Bag<DirectedEdge>[] adj;
    private int[] indegree;

    /**
     * Initialize an empty graph with V vertices and 0 edges.
     */
    public EdgeWeightedDigraph(int V){
        if(V < 0) throw new IllegalArgumentException();
        this.VERTICES = V;
        this.indegree = new int[V];
        adj = (Bag<DirectedEdge>[]) new Bag[V];
        for(int i = 0; i < V; i++){
            adj[i] = new Bag<DirectedEdge>();
        }
    }
    /**
     * Import graph from file.
     */
    public EdgeWeightedDigraph(String fileName, String delimiter) throws FileNotFoundException, InputMismatchException {
        File file = new File(fileName);
        Scanner scanner = new Scanner(file);
        try {
            VERTICES = scanner.nextInt();
            this.edges = scanner.nextInt();
            this.indegree = new int[VERTICES];
            adj = (Bag<DirectedEdge>[]) new Bag[VERTICES];
            for(int i = 0; i < VERTICES; i++){
                adj[i] = new Bag<DirectedEdge>();
            }
            while(scanner.hasNextLine()){
                int v = scanner.nextInt();
                int w = scanner.nextInt();
                int weight = scanner.nextInt();
                validateVertex(v);
                validateVertex(w);
                addEdge(new DirectedEdge(v, w, weight));
            }

        }catch (InputMismatchException e){
            throw new InputMismatchException("File '" + fileName + "' is in incorrect format.");
        }
    }
//    @Override
    public void addEdge(DirectedEdge edge) {
        int v = edge.from();
        int w = edge.to();
        validateVertex(v);
        validateVertex(w);
        adj[v].add(edge);
        indegree[w]++;
    }

//    @Override
    public Iterable<DirectedEdge> adjacent(int vertex) {
        validateVertex(vertex);
        return adj[vertex];
    }

//    @Override
    public int indegree(int vertex) {
        validateVertex(vertex);
        return indegree[vertex];
    }
//    @Override
    public int outdegree(int vertex) {
        validateVertex(vertex);
        return adj[vertex].size();
    }
//    @Override
    public int edges() {
        return edges;
    }

//    @Override
    public Iterable<DirectedEdge> allEdges() {
        Bag<DirectedEdge> edges = new Bag<>();
        for(int v = 0; v < VERTICES; v++){
            for(DirectedEdge edge : adj[v]){
                edges.add(edge);
            }
        }
        return edges;
    }

//    @Override
    public int vertices() {
        return VERTICES;
    }
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Vertices: ").append(VERTICES).append(" \nEdges: ").append(edges).append("\n");
        for (int i = 0; i < VERTICES; i++){
            sb.append(i).append(" : ");
            for(Edge edge : adj[i]){
                sb.append(edge.to()).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
    private void validateVertex(int vertex){
        if (vertex < 0  ||  VERTICES <= vertex) throw new IllegalArgumentException(
                "Vertex cannot be: "
                        + vertex
                        + " it must be between (inclusive) 0 and "
                        + (VERTICES -1));
    }
}
