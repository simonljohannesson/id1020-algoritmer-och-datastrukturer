public class HashTable2<Key, Value> {
    private static final int INIT_SIZE = 20;
    private LinkedList[] arrayList;
    private int M;

    public HashTable2(){
        this(INIT_SIZE);
    }
    public HashTable2(int capacity){
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
