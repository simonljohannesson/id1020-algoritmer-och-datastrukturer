/**
 * Representation of a stack.
 * @param <T> the type of object stored on the stack
 */
public interface Stack<T> {
    /**
     * Adds item to top of stack.
     * @param item
     */
    public void push(T item);

    /**
     * Removes and returns item on the top of the stack.
     * @return item on top of stack
     */
    public T pop();

    /**
     * Checks if stack is empty.
     * @return true if empty, false if not empty
     */
    public boolean isEmpty();

    /**
     * Checks size of stack.
     * @return the size of stack
     */
    public int size();
}