/*
 *  Author:         Simon Johannesson
 *  Email:          simonljohannesson@gmail.com, sijohann@kth.se
 *  Created:        2020-10-01
 *  Updated:
 *  Solves problem: Lab 4
 *  Usage:          Compile and use with API.
 *
 *  Dependencies:   LinkedList
 *  (own classes)
 *
 *  Based on:       https://algs4.cs.princeton.edu/34hash/
 */
public class HashTable<Key , Value> implements ST<Key, Value>{
    private class Pair{
        Key key;
        Value value;
        Pair(Key key, Value value){
            this.key = key;
            this.value = value;
        }

        @Override
        public boolean equals(Object object) {
            if(!(object instanceof HashTable.Pair)) return false;
            Pair p = (Pair) object;
            return  this.key.equals(p.key);
        }
    }
    private static final int INIT_SIZE = 20;
    private LinkedList<Pair>[] list;
    private int M;
    private int numKeys;


    public HashTable(){
        this(INIT_SIZE);
    }
    public HashTable(int capacity){
        list = (LinkedList<Pair>[]) new LinkedList[capacity];
        M = capacity;
        numKeys = 0;
    }

    /**
     * Returns a hash of the hashcode of a key.
     */
    private int hash(Key key){
        return (key.hashCode() & 0x7fffffff) % M;
    }

    @Override
    public void put(Key key, Value value) {
        int hash = hash(key);
        // index empty create new list
        if(this.list[hash] == null){
            LinkedList<Pair> list = new LinkedList<>();
            list.append(new Pair(key, value));
            this.list[hash] = list;
            numKeys++;
        }else{
            try{
                // try to find key pair in list and change value
                Pair stored = this.list[hash].getMatching(new Pair(key, value));
                stored.value = value;
            }catch (NotInListException e){
                // key not stored add new
                this.list[hash].append(new Pair(key, value));
                numKeys++;
            }
        }
    }

    @Override
    public Value get(Key key) throws NotInSTException{
        try{
            int hash = hash(key);
            if(this.list[hash] == null) throw new NotInSTException();
            Pair stored = this.list[hash].getMatching(new Pair(key, null));
            return stored.value;
        }catch (NotInListException e){
            throw new NotInSTException();
        }
    }

    @Override
    public void delete(Key key) {
//        numKeys--;
    }

    @Override
    public boolean contains(Key key) {
        try{
            get(key);
            return true;
        } catch(NotInSTException e){
            return false;
        }
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public int size(){
        return numKeys;
    }

    @Override
    public Iterable<Key> keys() {
        LinkedList<Key> list = new LinkedList<>();
        for(int i = 0; i < M; i++){
            if(this.list[i] != null){
                for(Pair p : this.list[i]){
                    list.append(p.key);
                }
            }
        }
        return list;
    }
    public static void main(String[] args){
        HashTable<String, String> ht = new HashTable<>();
        for(int i = 10; i < 20; i++){
            ht.put(Integer.toString(i), Integer.toString(i*10));
        }
        for(String key : ht.keys()){
            try {
                System.out.println("Key: " + key + " val: " + ht.get(key));
                if(key.equals("12")){
                    System.out.println("replacing with: " + "heeeeelo");
                    ht.put(key, "heeeeelo");
                    System.out.println("Value is now: " + ht.get(key));
                }
            }catch (NotInSTException e){
                System.out.println("Key: " + key + " not in HT");
            }
        }
        try {
            System.out.println("Key: " + 88 + " val: " + ht.get(Integer.toString(88)));
        }catch (NotInSTException e){
            System.out.println("Key: " + 88 + " not in HT");
        }
        System.out.println(ht.size());
    }

}
