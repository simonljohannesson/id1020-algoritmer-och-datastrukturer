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
public interface Digraph {
    /**
     * Return all edges in graph.
     */
    public Iterable<Edge> allEdges();
    /**
     * Add edge to graph.
     */
    public abstract void addEdge(Edge edge);
    /**
     * Return vertices adjacent to vertex.
     */
    public Iterable<Edge> adjacent(int vertex);
    /**
     * Return indegree of vertex.
     */
    public int indegree(int vertex);
    /**
     * Return out of vertex.
     */
    public int outdegree(int vertex);
    /**
     * Return number of edges in graph.
     */
    public int edges();

    /**
     * Return number of vertices in graph.
     */
    public int vertices();
}
