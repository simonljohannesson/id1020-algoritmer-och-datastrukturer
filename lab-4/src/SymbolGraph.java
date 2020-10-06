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

 *
 *  Based on:       https://algs4.cs.princeton.edu/41graph/SymbolGraph.java.html
 */

public interface SymbolGraph {
    /**
     * Checks if the key is in the graph.
     */
    public boolean contains(String key);
    /**
     * Returns integer associated with
     */
    public int indexOf(String key) throws NotInGraphException;

    /**
     * Return name of vertex associated with the integer.
     */
    public String nameOf(int vertex);
    /**
     * Return graph associated with the symbol graph..
     */
    public Graph graph();

}
