import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class SymbolGraph {
    HashTable<String, Integer> ht = new HashTable<>();
    String[] keys;
    UndirectedGraph graph;

    /**
     * Builds graph by reading from file.
     */
    public SymbolGraph(String fileName, String delimiter){
        ht = new HashTable<>();

        File file = new File(fileName);
        try {
            Scanner scanner = new Scanner(file);
            // create symbol table for vertex name to index
            // add vertex name (keys) to symbol table and assign index
            while(scanner.hasNextLine()){
                String line[] = scanner.nextLine().split(delimiter);
                for(int i = 0; i < line.length; i++){
                    if(!ht.contains(line[i])){
                        ht.put(line[i], ht.size());
                    }
                }
            }
            // create inverted array for index to name
            try{
                keys = new String[ht.size()];
                for(String key : ht.keys()){
                    keys[ht.get(key)] = key;
                }
            }catch(NotInSTException e){
                e.printStackTrace(); // Not going to happen unless there is a bug
            }
            // create the underlying graph
            graph = new UndirectedGraph(ht.size());
            scanner = new Scanner(file);
            while(scanner.hasNextLine()){
                String[] line = scanner.nextLine().split(delimiter);
                for(int i = 1; i < line.length; i++){
                    try{
                        // add edge for adjacent vertices
                        graph.addEdge(ht.get(line[0]), ht.get(line[i]));
                    }catch (NotInSTException e){
                        e.printStackTrace(); // Not happening unless there is a bug
                    }
                }
            }
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("Not a valid path to file: " + fileName);
        }
    }

    /**
     * Checks if the key is in the graph.
     */
    public boolean contains(String key){
        return ht.contains(key);
    }
    /**
     * Returns integer associated with
     */
    public int indexOf(String key) throws NotInGraphException{
        try {
            return ht.get(key);
        } catch (NotInSTException e) {
            throw new NotInGraphException();
        }
    }

    /**
     * Return name of vertex associated with the integer.
     */
    public String nameOf(int vertex){
        validateVertex(vertex);
        return keys[vertex];
    }
    private void validateVertex(int vertex){
        if (vertex < 0  ||  keys.length <= vertex) throw new IllegalArgumentException(
                "Vertex cannot be: "
                + vertex
                + " it must be between (inclusive) 0 and "
                + (keys.length -1));
    }

    /**
     * Return graph associated with the symbol graph..
     */
    public UndirectedGraph graph(){
        return graph;
    }
    public static void main(String[] args){
        System.out.println("Working Directory = " + System.getProperty("user.dir"));
        SymbolGraph sg = new SymbolGraph("database.txt", " ");
        System.out.println(sg.graph());

        System.out.println(sg.contains("LO"));
        System.out.println(sg.nameOf(5));
        try{
            System.out.println(sg.indexOf("AR"));
        }catch (NotInGraphException e){
            e.printStackTrace();
        }
    }
}
