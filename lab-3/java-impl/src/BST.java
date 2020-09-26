import java.util.Iterator;
import java.util.NoSuchElementException;

public class BST<Key extends Comparable<Key>, Value>{
    private Node root;
    private class Node{
        private Key key;
        private Value val;
        private Node left, right;
        private int size;           // nodes in subtrees

        public Node(Key key, Value val, int size){
            this.key = key;
            this.val = val;
            this.size = size;
        }
    }
    public BST(){}
    public int size(){
        return size(root);
    }
    private int size(Node x){
        if(x==null) return 0;
        else return x.size;
    }
    public boolean isEmpty(){
        return size() == 0;
    }
    private Value get(Node x, Key key){
        if (key == null) throw new IllegalArgumentException("Argument 'key' cannot be null.");
        if (x == null) return null;  // at bottom of tree, key not in symbol table
        int cmp = key.compareTo(x.key);
        if      (cmp < 0) return get(x.left, key);
        else if (cmp > 0) return get(x.right, key);
        else              return x.val;
    }
    public Value get(Key key){
        return get(root, key);
    }
    public boolean contains(Key key){
        if (key == null) throw new IllegalArgumentException("Argument 'key' cannot be null.");
        return get(key) != null;
    }
    private Node put(Node x, Key key, Value val){
        if(x == null) return new Node(key, val, 1); // bottom of tree
        int cmp = key.compareTo(x.key);
        if      (cmp < 0) x.left = put(x.left, key, val);
        else if (cmp > 0) x.right = put(x.right, key, val);
        else              x.val = val;                      // overwrite old value
        // on the way back up the stack update sizes
        x.size = 1+ size(x.left) + size(x.right);
        return x;
    }
    public void put(Key key, Value val){
        if (key == null) throw new IllegalArgumentException("Argument 'key' cannot be null.");
        if (val == null) {
            delete(key);
            return;
        }
        root = put(root, key, val);
    }
    private Node min(Node x){
        if(x.left == null) return x; // no more smaller keys
        else return min(x.left);
    }
    private Node max(Node x){
        if(x.right == null) return x; // no more bigger keys
        else return max(x.right);
    }
    public Key min(){
        if(isEmpty()) throw new NoSuchElementException("Symbol table empty.");
        return min(root).key;
    }
    public Key max(){
        if(isEmpty()) throw new NoSuchElementException("Symbol table empty.");
        return max(root).key;
    }
    private Node deleteMin(Node x){
        if(x.left == null) return x.right; // 'cut out' x from the list at the bottom of tree
        x.left = deleteMin(x.left); // keep following the left fork
        // update sizes on the way back on the stack
        x.size = 1+ size(x.left) + size(x.right);
        return x;
    }
    public void deleteMin(){
        if(isEmpty()) throw new NoSuchElementException("Symbol table empty.");
        root = deleteMin(root);
    }
    private Node deleteMax(Node x){
        if(x.right == null) return x.left; // 'cut out' x from the list at the bottom of tree
        x.right = deleteMax(x.right);     // follow the right fork
        x.size = 1 + size(x.left) + size(x.right);
        return x;
    }
    public void deleteMax(){
        if(isEmpty()) throw new NoSuchElementException("Symbol table empty.");
        root = deleteMax(root);
    }
    private Node delete(Node x, Key key){
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if      (cmp < 0)  x.left = delete(x.left, key);
        else if (cmp > 0)  x.right = delete(x.right, key);
        else { // key to delete found
            // if there is only one branch just return that branch
            if(x.right == null) return x.left;
            if(x.left == null)  return x.right;
            // comment, could just as well go left and take max(temp.left)
            Node temp = x;
            // this is where the old x node is 'written over' with the nearest value bigger than itself
            x = min(temp.right);
            x.right = deleteMin(temp.right);
            x.left = temp.left;
        }
        // update size
        x.size = 1 + size(x.left) + size(x.right);
        return x;
    }
    private void keys(Node x, Queue<Key> queue){
        if(x == null)       return;
        if(x.left != null)  keys(x.left, queue);
        queue.enqueueLast(x.key);
        if(x.right != null) keys(x.right, queue);
    }
    public Iterable<Key> keys() {
        Queue<Key> queue = new Queue<>();
        keys(root, queue);
        return queue;
    }
    public void delete(Key key){
        if (key == null) throw new IllegalArgumentException("Argument 'key' cannot be null.");
        root = delete(root, key);
    }


    public static void main(String[] args){

        BST<String, Integer> bst = new BST<>();
        for(int i = 0; i < 25; ++i){
            String s = Integer.toString(i);
            bst.put(s, i*2);
        }
        for(int i = 15; i < 20; ++i){
            bst.delete(Integer.toString(i));
        }

        for(String key : bst.keys()){
            System.out.println("Key: '" + key + "' Val: " + bst.get(key));
        }
    }
}
