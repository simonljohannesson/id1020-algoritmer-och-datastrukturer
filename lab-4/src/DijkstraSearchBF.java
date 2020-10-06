/*
 *  Author:         Simon Johannesson
 *  Email:          simonljohannesson@gmail.com, sijohann@kth.se
 *  Created:        2020-10-01
 *  Updated:
 *  Solves problem: Lab 4
 *  Usage:          Compile and use with API.
 *
 *  Dependencies:   DirectedEdge
 *  (own classes)   LinkedList
 *                  EdgeWeightedDigraph
 *                  IndexMinPQ (let's call it own)
 *
 *  Based on:       https://algs4.cs.princeton.edu/44sp/DijkstraSP.java.html
 *                  https://en.wikipedia.org/wiki/Suurballe%27s_algorithm
 */
import java.io.FileNotFoundException;
public class DijkstraSearchBF {
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

    private LinkedList<DirectedEdge> halfPath;
    private boolean hasPath;

    public DijkstraSearchBF(EdgeWeightedDigraph g, int source, int to){
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
                if(e.weight() == INFINITY) continue;
                relax(e);
            }
        }

        // store path if it exists
        hasPath = hasPathTo(to);
        if(hasPath){
            halfPath = new LinkedList<>();
            Iterable<DirectedEdge> firstPath = pathTo(to);
            int startDist = 0;
            for(DirectedEdge e : firstPath){
                this.halfPath.append(new DirectedEdge(e.from(), e.to(), e.weight()));
                startDist += e.weight();
                // set weight of first path of path to infinity
                e.setWeight(INFINITY);
            }
            // do search from to vertex
            search(g, to, startDist);
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

    private void search(EdgeWeightedDigraph g, int source, int startDistance){
        // check for negative weights
        for (DirectedEdge e : g.allEdges()){
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
        distTo[source] = startDistance;

        // determine distances to each vertex by relax method
        pq = new IndexMinPQ<>(g.vertices());
        pq.insert(source, distTo[source]);
        while(!pq.isEmpty()){
            for(DirectedEdge e : g.adjacent(pq.delMin())){
                if(e.weight() == INFINITY) continue;
                relax(e);
            }
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
     * Checks if there is a path between source vertex and to vertex.
     */
    public boolean hasPath(){
        return hasPath;
    }

    /**
     * Returns path between source vertex and to vertex if it exists.
     * Else returns null.
     */
    public Iterable<DirectedEdge> pathTo(){
        return halfPath; // unsafe, should copy
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
        // copy previous path first
        LinkedList<DirectedEdge> path = new LinkedList<>();

        if(halfPath != null){
            for(DirectedEdge e : halfPath){
                // remember, this part is already in order
                path.append(e);
            }
        }
        // goes from back to front, so need to store in a temporary list to get them in correct order
        LinkedList<DirectedEdge> tempPath = new LinkedList<>();
        for(DirectedEdge e = edgeTo[vertex]; e != null; e = edgeTo[e.from()]){
            // goes from back to front
            tempPath.prepend(e);
        }
        // transfer to the path
        for(DirectedEdge e : tempPath){
            path.append(e);
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
            int fd = 5;

            EdgeWeightedDigraph digraph = new EdgeWeightedDigraph("NYC-small.txt", " ");
            System.out.println(digraph);
            DijkstraSearchBF searchBF = new DijkstraSearchBF(digraph, s, t);
//
            for(DirectedEdge e : searchBF.pathTo()){
                System.out.println(e);
            }

            System.out.println(searchBF.hasPathTo(fd));
//            System.out.println("Path from " + s + " to " + t);
//            Iterable<DirectedEdge> iter;
//            System.out.println("start");
//            if(searchBF.hasPathTo(fd)){
////                System.out.println("has path");
//                iter = searchBF.pathTo(fd);
//                for(DirectedEdge e : iter){
//                    System.out.println(e);
//                }
//            }
//            else{
//                System.out.println("Does not exist.");
//            }

        }catch(FileNotFoundException e){
            System.out.println("File does not exist, terminating.");
        }
    }
}
