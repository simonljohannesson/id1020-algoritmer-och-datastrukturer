import javax.management.ValueExp;
import java.util.Iterator;

public class HashTable<Key, Value> {
    private static final int INIT_SIZE = 20;
    private LinkedList[] arrayList;
    private int M;

    public HashTable(){
        this(INIT_SIZE);
    }
    public HashTable(int capacity){
        arrayList = new LinkedList[capacity];
        M = capacity;
    }
    /**
     * Returns a hash that is uniform for the size of the underlying list.
     */
    private int hash(Key key){
        return (key.hashCode() & 0x7fffffff) % M;
    }
    /**
     * Add key value pair to hash table.
     */
    public void add(Key key, Value value){
        int hash = hash(key);
        if (arrayList[hash] == null){
            LinkedList<Key, Value> linkedList = new LinkedList<>();
            linkedList.add(key, value);
            arrayList[hash] = linkedList;
        }else{
            arrayList[hash].add(key, value);
        }
    }
    /**
     * Returns the size of the list at the index specified.
     */
    public int getLength(int index){
        if (arrayList[index] == null) return 0;
        else return arrayList[index].size();
    }
    /**
     * Returns a linked list of values with the matching key.
     */
     public Value get(Key key){
        LinkedList<Key, Value> stored = arrayList[hash(key)];
        return stored.match(key);
    }
    /**
     * Returns an integer array with the number ofkey value
     * pairs entered at the corresponding index in the
     * nderlying array list.
     */
    public int[] getSizeAllIndices(){
        int[] entries = new int[arrayList.length];
        for(int index = 0; index < arrayList.length; ++index){
            entries[index] = getLength(index);
        }
        return entries;
    }
    public void print(){
        for(int i = 0; i < arrayList.length; i++){
            System.out.println(i + ":"+arrayList[i]);
        }
    }
    public static void main(String args[]){
        HashTable<Integer, String> ht = new HashTable<>(10);
        String s1 = "hello";
        ht.add(s1.hashCode(), s1);

        String s2 = "buddy";
        ht.add(s2.hashCode(), s2);

        String s3 = "friend";
        ht.add(s3.hashCode(), s3);

        s3 = "bad2";
        ht.add(s3.hashCode(), s3);

        s3 = "b2ad";
        ht.add(s3.hashCode(), s3);

        s3 = "ba3d";
        ht.add(s3.hashCode(), s3);

        s3 = "b3ad";
        ht.add(s3.hashCode(), s3);

        s3 = "hello";
        ht.add(s3.hashCode(), s3);

        int[] a = ht.getSizeAllIndices();
        for(int i = 0; i < a.length; i++){
            System.out.format("a[%d] = %d\n", i, a[i]);
        }
        ht.print();
    }
}


class LinkedList<Key, Value>{
    private Node head;
    private int n;
    private class Node{
        Key key;
        Value value;
        Node next;
        Node(){
            this(null, null, null);
        }
        Node(Key key, Value value, Node next){
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }
    LinkedList(){
        n = 0;
        head = new Node(); // sentinel
    }
    /**
     * Adds the specified key value pair at the beginning of the list.
     * first makes sure the key value pair (both!) doesn't already exist in list
     */
    public void add(Key key, Value value){
        boolean inList = false;
        Node next = head;
        while((next = next.next) != null){
            if(next.key.equals(key)  &&  next.value.equals(value)){
                inList = true;
                break;
            }
        }
        if(!inList){
            addFirst(key, value);
        }
    }
    private void addFirst(Key key, Value value){
        Node newNode = new Node(key, value, head.next);
        head.next = newNode;
        n++;
    }
    public LinkedList<Key, Value> allValuesMatching(Key key){
        LinkedList<Key, Value> rList = new LinkedList<>();
        Node next = head;
        while((next = next.next) != null){
            if(next.key.equals(key)){
                rList.addFirst(next.key, next.value);
            }
        }
        return rList;
    }

    /**
     * Returns value matching the key, if not found null.
     */
    public Value match(Key key){
        Node next = head;
        while((next = next.next) != null){
            if(next.key.equals(key)){
                return next.value;
            }
        }
        return null;
    }
    /**
     * Returns true if the list contains the key
     */
    public boolean contains(Key key){
        throw new NotImplementedException(
                "LinkedList method contains(Key key) is not implemented yet.");
    }
    /**
     * Returns the value if it is in the list.
     */
    public Value get(Key key) {
        // TODO: can throw NoSuchElementException
        throw new NotImplementedException(
                "LinkedList method get(Key key) is not implemented yet.");
    }
    /**
     * Returns the size of the list.
     */
    public int size(){
        return n;
    }
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        Node next = head;
        while((next = next.next) != null){
            sb.append("{");
            sb.append(next.key);
            sb.append(", ");
            sb.append(next.value);
            sb.append("}");
        }
        sb.append("]");
        return sb.toString();
    }

}

class NotImplementedException extends RuntimeException{
    NotImplementedException(String msg){
        super(msg);
    }
}