package Utilities;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Iterator;

/**
 * @author Darren Sills
 * Generic, custom linked list implementing iterator
 */
public class CustomList<E> implements Iterable<E> {
    private int size = 0; //size of the list
    public CustomNode<E> head; //first node in the list

    /**
     * Add an element to the list
     * @param e element to be added to the list
     */
    public void add(E e) {
        if(head == null){ //if no nodes in the list, head becomes the first node
            head = new CustomNode<>(e);
        }
        CustomNode<E> newE = new CustomNode<>(e);
        CustomNode<E> current = head;

        while (current.getNext() != null) {  //iterate through the list from the head
            current = current.getNext();    // and then add the new node at the end
        }
        current.setNext(newE); //the last node's "next" reference set to our new node

        size++; //add one to the size
    }

    public void set(int index, E e) {
        CustomNode<E> current = head;

        if (head != null) {
            current = head.getNext();
            for (int i = 0; i < index; i++) { //iterate until reaching given index
                if (current.getNext() == null) { //if the index is at null, return null
                    return;
                } else {
                    current = current.getNext();
                }
            }
            current.setData(e);
        }
    }

    /**
     * Get the index of a given element
     * @param index index of the element to be returned
     * @return the element at index
     */
    public E get(int index) {
        CustomNode<E> current;

        if (head != null) { //nothing happens if the list is empty
            current = head.getNext();
            for (int i = 0; i < index; i++) { //iterate until reaching given index
                if (current.getNext() == null) { //if the index is at null, return null
                    return null;
                } else {
                    current = current.getNext();
                }
            }
            return current.getData(); //return the element at the index
        }
        return null;
    }

    /**
     * Remove all elements from the list
     */
    public void clear() {
        head = null;
        size = 0;
    }

    /**
     * Removes the element at the specified position in this list.
     * @param index index of the element to be returned
     * @return the element deleted
     */
    public E remove(int index) {
        CustomNode<E> current = head;

        if (head != null) {
            for (int i = 0; i < index; i++) {
                if (current.getNext() == null) {
                    return null;
                } else {
                    current = current.getNext();
                }
            }
            current.setNext(current.getNext().getNext());
            size--; //decrease the size of the list
        }
        return null;
    }

    /**
     * removes the given element from the list.
     * @param e element to be removed from the list
     */
    public void remove(E e) {
        CustomNode<E> current = head;

        if (head != null) {
            while (current.getNext() != null) {
                if (current.getNext().getData().equals(e)) { //iterate until finding e
                    current.setNext(current.getNext().getNext());
                    size--;
                    return; //exit out of the method
                } else {
                    current = current.getNext();
                }
            }
        }
    }

    /**
     * Removes the given element from the list.
     * @param e element to be removed from the list
     * @return the index of e
     */
    public int indexOf(E e) {
        CustomNode<E> current;

        if (head != null) {
            current = head.getNext();
            for (int i = 0; i < size; i++) {
                if (current.getData().equals(e)) { //return i when finding e
                    return i;
                }
                current = current.getNext();
            }
        }
        return 80085;
    }

    /**
     * Checks if the list contains the given element
     * @param e element to be found
     * @return true if the list contains e, false otherwise
     */
    public boolean contains(E e) {
        CustomNode<E> current = head;

        if (head != null) {
            if (current.getData().equals(e)) {
                return true;
            }
            for (int i = 0; i < size; i++) {
                current = current.getNext();
                if (current.getData().equals(e)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Gets the size of the list
     * @return the size of the list
     */
    public int size() {
        return size;
    }

    /**
     * Checks if the list is empty
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Outputs a string to list the elements in the list in a readable format
     */
    public String toString() {
        StringBuilder toString = new StringBuilder();

        if (head != null) {
            CustomNode<E> current = head.getNext();
            while (current != null) {
                toString.append("[").append(current.getData().toString()).append("]");
                current = current.getNext();
            }
        }
        return toString.toString();
    }

    /**
     * Starts the iterator from the head and iterates through the list
     */
    @Override
    public Iterator<E> iterator() {
        return new CustomIterator<>(head);
    }

    public <T extends Comparable<? super T>> void sort(CustomList<T> list) {
        Object[] a = new Object[size()];
        a = list.toArray(a);
        Arrays.sort(a);
        CustomIterator<T> i = (CustomIterator<T>) list.iterator();
            for (Object o : a) {
                i.next();
                i.set((T) o);
            }
    }

    public <T> T[] toArray(T[] array) {
        int size = size();
        if (array.length < size) {
            // If array is too small, allocate the new one with the same component type
            array = (T[]) Array.newInstance(array.getClass().getComponentType(), size);
        } else if (array.length > size) {
            // If array is to large, set the first unassigned element to null
            array[size] = null;
        }

        int i = 0;
        for (E e: this) {
            // No need for checked cast - ArrayStoreException will be thrown
            // if types are incompatible, just as required
            array[i] = (T) e;
            i++;
        }
        return array;
    }
}
