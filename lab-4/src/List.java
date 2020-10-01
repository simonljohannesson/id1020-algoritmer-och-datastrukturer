import java.util.NoSuchElementException;

public interface List<Type> {
    /**
     * Add item to the end of the list
     */
    public void append(Type item);
    /**
     * Add item to the beginning of the list
     */
    public void prepend(Type item);
    /**
     * Remove and return item at index.
     */
    public Type remove(int index);
    /**
     * Return size of list.
     */
    public int size();
    /**
     * Returns object matching with compareTo() in list.
     */
    public Type getMatching(Type item) throws NotInListException;
}
