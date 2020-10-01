/*
 *  Author:         Simon Johannesson
 *  Email:          simonljohannesson@gmail.com, sijohann@kth.se
 *  Created:        2020-10-01
 *  Updated:
 *  Solves problem: Lab 4
 *  Usage:          Compile and use with API.
 *
 *  Dependencies:   UndirectedGraph
 *  (own classes)   LinkedList
 *
 *
 *  Based on:       https://algs4.cs.princeton.edu/41graph/BreadthFirstPath.java.html
 */
public class SearchBF {
    private final int INFINITY = Integer.MAX_VALUE;
    private boolean[] marked;
    private int[] edgeTo;
    private int[] distTo;
    private final int source;

    /**
     * Determines all connected vertices from the source vertex.
     */
    public SearchBF(Graph graph, int sourceVertex){
        source = sourceVertex;
        marked = new boolean[graph.vertices()];
        edgeTo = new int[graph.vertices()];
        distTo = new int[graph.vertices()];
        for(int i = 0; i < distTo.length; i++){
            distTo[i] = INFINITY;
        }
        validateVertex(sourceVertex);
        markConnectedVerticesBF(graph, sourceVertex);
    }
    /**
     * Mark all connected vertices with true in list marked.
     * Uses breadth first to traverse.
     */
    private void markConnectedVerticesBF(Graph graph, int sourceVertex){
        LinkedList<Integer> queue = new LinkedList<>();
        distTo[sourceVertex] = 0;
        marked[sourceVertex] = true;
        queue.append(sourceVertex);
        while(0 < queue.size()){
            int v = queue.remove(0);
            for(int w : graph.adjacent(v)){
                if(!marked[w]){
                    marked[w] = true;
                    distTo[w] = distTo[v] + 1;
                    edgeTo[w] = v;
                    queue.append(w);
                }
            }
        }
    }
    /**
     * Checks if there is a path to vertex.
     */
    public boolean hasPath(int vertex){
        validateVertex(vertex);
        return marked[vertex];
    }
    /**
     * Return distance to vertex.
     */
    public int distTo(int vertex){
        validateVertex(vertex);
        return distTo[vertex];
    }
    /**
     * Returns shortest path between the source vertex and vertex.
     */
    public Iterable<Integer> pathTo(int vertex){
        validateVertex(vertex);
        if(!hasPath(vertex)) return null;
        LinkedList<Integer> path = new LinkedList<>();
        int x;
        for(x = vertex; x != source; x = edgeTo[x]){
            path.prepend(x);
        }
        path.prepend(x);
        return path;
    }
    private void validateVertex(int vertex){
        if(vertex < 0 || marked.length <= vertex) throw new IllegalArgumentException("Vertex cannot be: "
                + vertex
                + " it must be between (inclusive) 0 and "
                + (marked.length -1));
    }
    public static void main(String[] args){
        SymbolGraphUndirected sg = new SymbolGraphUndirected("database-small.txt", " ");
        Graph g = sg.graph();
        System.out.println(g);
        SearchBF searchBF = new SearchBF(g, 0);
        System.out.println(searchBF.hasPath(5));
        System.out.println("Path: ");

        for(int i : searchBF.edgeTo){
            System.out.print(i + " ");
        }
        System.out.println();

        for (int p : searchBF.pathTo(5)){
            System.out.print(p);
        }
        System.out.println();
    }
}
