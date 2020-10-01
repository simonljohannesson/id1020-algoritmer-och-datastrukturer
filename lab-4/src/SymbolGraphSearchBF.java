/*
 *  Author:         Simon Johannesson
 *  Email:          simonljohannesson@gmail.com, sijohann@kth.se
 *  Created:        2020-10-01
 *  Updated:
 *  Solves problem: Lab 4
 *  Usage:          Compile and use with API.
 *
 *  Dependencies:   SymbolGraph
 *  (own classes)   SearchBF
 *                  UndirectedGraph
 *                  NotInGraphException
 *                  LinkedList
 *
 *  Based on:       https://algs4.cs.princeton.edu/41graph/BreadthFirstPaths.java.html
 */
public class SymbolGraphSearchBF {
    SearchBF searchBF;
    SymbolGraph symbolGraph;
    String source;

    SymbolGraphSearchBF(SymbolGraph symbolGraph, String vertex){
        try {
            this.source = vertex;
            this.symbolGraph = symbolGraph;
            searchBF = new SearchBF(symbolGraph.graph(), symbolGraph.indexOf(vertex));

        } catch (NotInGraphException e) {
            throw new IllegalArgumentException();
        }
    }
    // TODO: determine if necessary
    private void validateVertex(String vertex){
        if(!symbolGraph.contains(vertex))throw new IllegalArgumentException();
    }
    /**
     * Checks if there is a path to vertex.
     */
    public boolean hasPath(String vertex){
        try{
            return searchBF.hasPath(symbolGraph.indexOf(vertex));
        }catch(NotInGraphException e){
            throw new IllegalArgumentException();
        }
    }
    public Iterable<String> pathTo(String vertex){
        try{
            LinkedList<String> path = new LinkedList<>();
            for(int i : searchBF.pathTo(symbolGraph.indexOf(vertex))){
                path.append(symbolGraph.nameOf(i));
            }
            return path;
        }catch (NotInGraphException e){
            throw new IllegalArgumentException();
        }
    }

    public static void main(String[] args){
        SymbolGraph sg = new SymbolGraph("database.txt", " ");
        UndirectedGraph g = sg.graph();
        System.out.println(g);
        SymbolGraphSearchBF searchBF = new SymbolGraphSearchBF(sg, "CA");

        System.out.println("CA has path to : " + searchBF.hasPath("PA"));
        for(String v : searchBF.pathTo("PA")){
            System.out.print(v + " ");
        }
        System.out.println();
    }
}