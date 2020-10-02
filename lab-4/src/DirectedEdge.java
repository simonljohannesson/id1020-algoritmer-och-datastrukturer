public class DirectedEdge implements Edge{
    private final int from, to, weight;

    public DirectedEdge(int from, int to, int weight){
        this.from = from;
        this.to = to;
        this.weight = weight;
    }

    @Override
    public int weight() {
        return weight;
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
        return from + " -" + weight + "- " + to;
    }
}
