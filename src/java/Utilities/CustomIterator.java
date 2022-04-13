package Utilities;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author Darren Sills
 * Generic, custom iterator used to iterate through the list
 */
public class CustomIterator<K> implements Iterator<K> {
    private CustomNode<K> pos;

    public CustomIterator(CustomNode<K> head) {
        pos = head;
    } //starting position of the iterator

    @Override
    public boolean hasNext() {
        return pos.getNext() != null;
    }

    @Override
    public K next() {
        if (hasNext()) { //check if the next node exists
            pos = pos.getNext(); //move onto the next node
            return pos.getData(); //get data from the node before moving to the next
        } else {
            throw new NoSuchElementException(); //throw exception if there is no next
        }
    }

    public void set(K o) {
        pos.setData(o);
    }
}