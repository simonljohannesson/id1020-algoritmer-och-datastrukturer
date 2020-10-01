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
 *  Based on:       https://algs4.cs.princeton.edu/41graph/DepthFirstPath.java.html
 */
public class SearchDF {
    private boolean[] marked;
    private int[] edgeTo;
    private final int source;

    /**
     * Determines all connected vertices from the source vertex.
     */
    public SearchDF(Graph graph, int sourceVertex){
        source = sourceVertex;
        marked = new boolean[graph.vertices()];
        edgeTo = new int[graph.vertices()];
        validateVertex(sourceVertex);
        markConnectedVertices(graph, sourceVertex);
    }

    /**
     * Mark all connected vertices with true in list marked.
     */
    private void markConnectedVertices(Graph graph, int sourceVertex){
        marked[sourceVertex] = true;
        for(int adjacentVertex : graph.adjacent(sourceVertex)){
            if(!marked[adjacentVertex]){
                // mark from where adjacent vertex was accessed
                edgeTo[adjacentVertex] = sourceVertex;
                markConnectedVertices(graph, adjacentVertex);
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
     * Returns path to vertex.
     * Returns null if there is no path.
     */
    public Iterable<Integer> pathTo(int vertex){
        validateVertex(vertex);
        LinkedList<Integer> path = new LinkedList<>();
        if(!marked[vertex]) return null;
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
        SearchDF searchDF = new SearchDF(g, 0);
        System.out.println(searchDF.hasPath(5));
        System.out.println("Path: ");

        for(int i : searchDF.edgeTo){
            System.out.print(i + " ");
        }
        System.out.println();

        for (int p : searchDF.pathTo(5)){
            System.out.print(p);
        }
        System.out.println();
    }
}
