class LinkedList2<Value>{
    private Node head;
    private int n;
    private class Node{
        Value value;
        Node next;
        Node(){
            this(null, null);
        }
        Node(Value value, Node next){
            this.value = value;
            this.next = next;
        }
    }
    LinkedList2(){
        n = 0;
        head = new Node(); // sentinel
    }
    /**
     * Adds the specified key value pair at the beginning of the list.
     * first makes sure the key value pair (both!) doesn't already exist in list
     */
    public void add(Value value){
        boolean inList = false;
        Node next = head;
        while((next = next.next) != null){
            if(next.value.equals(value)){
                inList = true;
                break;
            }
        }
        if(!inList){
            addFirst(value);
        }
    }
    private void addFirst(Value value){
        head.next = new Node(value, head.next);
        n++;
    }
    /**
     * Returns true if the list contains the key
     */
    public boolean contains(Value value){
        Node next = head;
        while((next = next.next) != null){
            if (next.value.equals(value)){
                return true;
            }
        }
        return false;
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
            sb.append(next.value);
            sb.append("}");
        }
        sb.append("]");
        return sb.toString();
    }

}
class NotImplementedException2 extends RuntimeException{
    NotImplementedException2(String msg){
        super(msg);
    }
}