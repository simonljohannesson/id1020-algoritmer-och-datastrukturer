public interface Edge {
    /**
     * Weight of edge
     */
    int weight();

    /**
     * Vertex this edge points from.
     */
    int from();

    /**
     * Vertex this edge points to.
     */
    int to();

    /**
     * String representation.
     */
    String toString();
}
