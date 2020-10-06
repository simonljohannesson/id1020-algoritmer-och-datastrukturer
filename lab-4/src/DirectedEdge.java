public class DirectedEdge implements Edge, Comparable<DirectedEdge>{
    private int from, to, weight;

    public DirectedEdge(int from, int to, int weight){
        this.from = from;
        this.to = to;
        this.weight = weight;
    }
    public DirectedEdge(DirectedEdge edge){
        this(edge.from(), edge.to(), edge.weight());
    }

    @Override
    public int weight() {
        return weight;
    }
    public void setWeight(int weight){
        this.weight = weight;
    }

    @Override
    public int from() {
        return from;
    }

    @Override
    public int to() {
        return to;
    }
    @Override
    public String toString(){
        return from + " " + to + ": " + weight;
    }

    @Override
    public int compareTo(DirectedEdge edge) {
        if(this.weight == edge.weight() && this.from == edge.from() && this.to == edge.to()) return 0;
        else return -1;
    }
}
