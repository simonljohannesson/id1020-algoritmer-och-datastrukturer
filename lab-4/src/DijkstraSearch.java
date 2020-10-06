import java.io.FileNotFoundException;

/*
 *  Author:         Simon Johannesson
 *  Email:          simonljohannesson@gmail.com, sijohann@kth.se
 *  Created:        2020-10-01
 *  Updated:
 *  Solves problem: Lab 4
 *  Usage:          Compile and use with API.
 *
 *  Dependencies:
 *  (own classes)
 *
 *  Based on:       https://algs4.cs.princeton.edu/44sp/DijkstraSP.java.html
 */
public class DijkstraSearch {
    //TODO: implement a priority queue as a heap
    // class for use with the queue
//    private class IndexKey implements Comparable<IndexKey>{
//        Integer index;
//        Integer key;
//        IndexKey(int index, int key){
//            this.index = index;
//            this.key = key;
//        }
//        @Override
//        public int compareTo(IndexKey that) {
//            return this.key.compareTo(that.key);
//        }
//    }
    // dijkstra class
    private static final int INFINITY = Integer.MAX_VALUE;
    private int[] distTo;
    private DirectedEdge[] edgeTo;
    private IndexMinPQ<Integer> pq;

    public DijkstraSearch(EdgeWeightedDigraph g, int source){
        // check for negative weights
        for (Edge e : g.allEdges()){
            if (e.weight() < 0) throw new IllegalArgumentException("Edge with egative weight detected.");
        }
        distTo = new int[g.vertices()];
        edgeTo = new DirectedEdge[g.vertices()];

        validateVertex(source);

        // set all distances to infinity
        for(int v = 0; v < g.vertices(); v++){
            distTo[v] = INFINITY;
        }
        // distance to source vertex is 0
        distTo[source] = 0;

        // determine distances to each vertex by relax method
        pq = new IndexMinPQ<>(g.vertices());
        pq.insert(source, distTo[source]);
        while(!pq.isEmpty()){
            for(DirectedEdge e : g.adjacent(pq.delMin())){
                relax(e);
            }
        }
    }
    private void relax(DirectedEdge edge){
        int v = edge.from();
        int w = edge.to();
        // check if this edge provides is a closer path to the 'TO' vertex.
        if(distTo[w] > distTo[v] + edge.weight()){
            // update distTo and edgeTo
            distTo[w] = distTo[v] + edge.weight();
            edgeTo[w] = edge;
            // make sure the priority queue is updated
            if(pq.contains(w)) pq.decreaseKey(w, distTo[w]);
            else               pq.insert(w, distTo[w]);
        }
    }

    /**
     * Checks if there is a path to vertex from source.
     */
    public boolean hasPathTo(int vertex){
        validateVertex(vertex);
        return distTo[vertex] != INFINITY;
    }

    /**
     * Return distance to a vertex.
     * If no path exists returns Integer.MAX_VALUE. //TODO: should throw some exception
     */
    public int distTo(int vertex){
        validateVertex(vertex);
        return distTo[vertex];
    }

    /**
     * Returns path as an iterable.
     * If there is no path return null;
     */
    public Iterable<DirectedEdge> pathTo(int vertex){
        if (!hasPathTo(vertex)) return null;
        Bag<DirectedEdge> path = new Bag<>();
        for(DirectedEdge e = edgeTo[vertex]; e != null; e = edgeTo[e.from()]){
            path.add(e);
        }
        return path;
    }

    private void validateVertex(int vertex){
        if (vertex < 0  ||  distTo.length <= vertex) throw new IllegalArgumentException(
                "Vertex cannot be: "
                        + vertex
                        + " it must be between (inclusive) 0 and "
                        + (distTo.length -1));
    }
    public static void main(String[] args){
        try {
            int s = 1;
            int t = 7;

            EdgeWeightedDigraph digraph = new EdgeWeightedDigraph("NYC-small.txt", " ");
            System.out.println(digraph);
            DijkstraSearch search = new DijkstraSearch(digraph, s);

            System.out.println(search.hasPathTo(t));
            System.out.println("Path from " + s + " to " + t);
            Iterable<DirectedEdge> iter;
            if((iter = search.pathTo(t)) != null){
                for(DirectedEdge e : search.pathTo(t)){
                    System.out.print(e + " ");
                }
                System.out.println();
            }else{
                System.out.println("Does not exist.");
            }

        }catch(FileNotFoundException e){
            System.out.println("File does not exist, terminating.");
        }
    }
}