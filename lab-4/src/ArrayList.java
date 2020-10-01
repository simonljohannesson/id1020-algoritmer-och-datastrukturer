import java.util.Iterator;

public class ArrayList<Type> implements Iterable<Type>{
    private static final int INIT_SIZE = 20;
    private Type[] list;
    private int size;

    ArrayList(){
        this(20);
    }
    ArrayList(int capacity){
        list = (Type[]) new Object[capacity];
        size = 0;
    }

    public void append(Type item){
        if(size() == list.length) resize(list.length * 2);
        list[size++] = item;
    }
    public int size(){
        return size;
    }

    public void putAt(int index, Type item){
        if (index < 0  ||  index >= size()) throw new IndexOutOfBoundsException();
        list[index] = item;
    }
    public Type getAt(int index){
        if (index < 0  ||  index >= size()) throw new IndexOutOfBoundsException();
        return list[index];
    }

    private void resize(int capacity){
        Type[] newList = (Type[]) new Object[capacity];
        for(int i = 0; i < size; i++){
            newList[i] = list[i];
        }
        list = newList;
    }

    @Override
    public Iterator<Type> iterator() {
        return new Iterator<Type>() {
            int index = 0;
            @Override
            public boolean hasNext() {
                return index < size();
            }

            @Override
            public Type next() {
                return list[index++];
            }
        };
    }
    public static void main(String[] args){
        ArrayList<Integer> a = new ArrayList<>(10);
        for(int i = 0; i < 100; i++){
            a.append(i);
        }
        for(Integer i : a){
            System.out.println(i);
        }
        System.out.println(a.getAt(99));
    }
}
