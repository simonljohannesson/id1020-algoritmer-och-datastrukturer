public interface ST<Key, Value> {
    /**
     * Put key value pair into the table.
     */
    void put(Key key, Value value);

    /**
     * Gets value paired with key.
     */
    Value get(Key key) throws NotInSTException;
    /**
     * Remove key and value pair from table.
     */
    void delete(Key key);

    /**
     * Check if table contains key.
     */
    boolean contains(Key key);

    /**
     * Checks if table is empty.
     */
    boolean isEmpty();

    /**
     * Returns size of table.
     */
    int size();
    Iterable<Key> keys();
}
