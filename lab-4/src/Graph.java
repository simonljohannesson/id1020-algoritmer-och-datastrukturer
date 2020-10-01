/*
 *  Author:         Simon Johannesson
 *  Email:          simonljohannesson@gmail.com, sijohann@kth.se
 *  Created:        2020-10-01
 *  Updated:
 *  Solves problem: Lab 4
 *  Usage:
 *
 *  Dependencies:
 *  (own classes)
 *
 *  Based on:
 */
public interface Graph {
    /**
     * Add undirected edge to graph.
     */
    public abstract void addEdge(int v, int w);
    /**
     * Return vertices adjacent to vertex.
     */
    public Iterable<Integer> adjacent(int vertex);
    /**
     * Return degree of vertex.
     */
    public int degree(int vertex);

    /**
     * Return number of edges in graph.
     */
    public int edges();

    /**
     * Return number of vertices in graph.
     */
    public int vertices();

}
