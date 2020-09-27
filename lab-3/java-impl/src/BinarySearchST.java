import java.util.Collection;
import java.util.Iterator;

public class BinarySearchST <Key extends Comparable<Key>, Value>{
    private static final int INIT_CAPACITY = 10;
    private Key[] keys;
    private Value[] vals;
    private int n = 0;

    /**
     * Constructor, initialize empty symbol table
     */
    public BinarySearchST(){
        this(INIT_CAPACITY);
    }
    public BinarySearchST(int capacity){
        keys = (Key[]) new Comparable[capacity];
        vals = (Value[]) new Object[capacity];
    }
    private void resize(int capacity){
        //debug
        if(n > capacity){
            System.out.println("resizing to smaller array than what is allowed");
        }
        // create new lists that will replace the old ones
        Key[] new_keys = (Key[]) new Comparable[capacity];
        Value[] new_vals = (Value[]) new Object[capacity];
        // copy all the data
        for (int i = 0; i<n; ++i){
            new_keys[i] = keys[i];
            new_vals[i] = vals[i];
        }
        keys = new_keys;
        vals = new_vals;
    }
    public int size(){
        return n;
    }
    public boolean isEmpty(){
        return size() == 0;
    }
    public int rank(Key key){
        if (key == null) throw new IllegalArgumentException("Argument 'key' cannot be 'null'.");
        // do binary search for the position of key
        int lo = 0, hi = n-1;
        while (lo <= hi){
            int mid = lo + (hi - lo) / 2;
            // if 0:  key == mid
            // if -1: key < mid
            // if 1:  key > mid
            int cmp = key.compareTo(keys[mid]);
            if      (cmp < 0) hi = mid-1;
            else if (cmp > 0) lo = mid+1;
            else return mid;
        }
        // if key is less than the smallest lo will not have moved from index 0
        // if key is bigger than the biggest lo will end up on index n (and while loop will exit)
        // or keys index in list if it had been in the list
        return lo;
    }
    public Value get(Key key){
        if (key == null) throw new IllegalArgumentException("Argument 'key' cannot be null'.");
        if (isEmpty()) return null;
        int i = rank(key);
        if(i < n && keys[i].compareTo(key) == 0) return vals[i];
        return null;
    }
    public boolean contains(Key key){
        if (key == null) throw new IllegalArgumentException("Argument 'key' cannot be null'.");
        return get(key) != null;
    }
    public void delete(Key key){
        if (key == null) throw new IllegalArgumentException("Argument 'key' cannot be null'.");
        if (isEmpty())return;
        int i = rank(key);
        // i bigger than biggest element  ||  i smaller than smallest element
        // i.e. key not in table
        if(i == n  ||  keys[i].compareTo(key) != 0) return;
        // move all elements after the key one step 'left'
        for (int j = i; j < n-1; ++j){
            keys[j] = keys[j+1];
            vals[j] = vals[j+1];
        }
        --n;
        keys[n] = null;
        vals[n] = null;
        // resize if 1/4 full
        if (n > 0  &&  n == keys.length/4) resize(keys.length/2);
    }
    public void put(Key key, Value value){
        if (key == null) throw new IllegalArgumentException("Argument 'key' cannot be null'.");
        if (value == null){
            delete(key);
            return;
        }
        int i = rank(key);
        // if key in table replace it's value
        if (i < n  &&  keys[i].compareTo(key) == 0){
            vals[i] = value;
            return;
        }
        // insert the new key value pair
        if (n == keys.length) resize(2*keys.length);
        for(int j = n; j > i; --j){
            keys[j] = keys[j-1];
            vals[j] = vals[j-1];
        }
        keys[i] = key;
        vals[i] = value;
        ++n;
    }
    public boolean isSorted(){
        for (int i = 0; i < size()-1; ++i){
            if (keys[i].compareTo(keys[i+1]) > 0) return false;
        }
        return true;
    }
    public Iterable<Key> keys(){
        class KeyIterable implements Iterable<Key>{
            class KeyIterator implements Iterator<Key>{
                int index;
                public KeyIterator(){
                    index = 0;
                }
                @Override
                public boolean hasNext() {
                    return index < n;
                }
                @Override
                public Key next() {
                    return keys[index++];
                }
            }
            @Override
            public Iterator<Key> iterator() {
                return new KeyIterator();
            }
        }


        return new KeyIterable();
    }
    public static void main(String[] args){
        BinarySearchST<String, Integer> bsst = new BinarySearchST<>();


        for (int i = 0; i < 500; i++){
            String s = Integer.toString(i);
            bsst.put(s, i*2);
        }
        for (int i = 100; i < 200; i++){
            String s = Integer.toString(i);
            bsst.delete(s);
        }
        for(String key : bsst.keys()){
            System.out.println("Key: '" + key + "' Val: " + bsst.get(key));
        }


    }

}
