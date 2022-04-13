package Utilities;

/**
 * @author Darren Sills
 * Generic, custom node to be used to carry data in the list
 */
public class CustomNode<N> {
    public CustomNode<N> next; // reference to the next node in the list, or null if there isn't one
    private N data; // data carried by the node

    public CustomNode(N data){
        this.data = data;
    }

    public N getData() {
        return data;
    }

    public void setData(N data) {
        this.data = data;
    }

    public CustomNode<N> getNext() {
        return next;
    }

    public void setNext(CustomNode<N> next) {
        this.next = next;
    }
}
