/*  
* @file: Deque.java
* @brief: 
* @author: Antonio Gutierrez
* @date: 2015-02-18
*/

import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
    
    private Node<Item> first;
    private Node<Item> last;
    private int size;

    private class Node<Item> {
        private Node<Item> prev;
        private Item item;
        private Node<Item> next;
    }

    // construct an empty queue
    public Deque() {
        size = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new java.lang.NullPointerException();
        }

        Node<Item> n = new Node<Item>();
        if (size == 0) {
            first = n;
            last = n;
            first.next = null;
        } else {
            // hold oldFirst
            Node<Item> oldFirst = first;
            // first is now new node created
            first = n;
            // oldFirst.prev is the new node (first now)
            oldFirst.prev = first;
            // first.next = old first
            first.next = oldFirst;
        }
        first.prev = null;
        first.item = item;
        ++size;
    }


    // add the item to the end
    public void addLast(Item item) {
        if (item == null) {
            throw new java.lang.NullPointerException();
        }

        Node<Item> n = new Node<Item>();
        if (size == 0) {
            first = n;
            last = n;
            first.prev = null;
        } else {
           Node<Item> oldLast = last; 
           last = n;
           oldLast.next = last;
           last.prev = oldLast;
           
        }
        last.next = null;
        last.item = item;   
        ++size; 
    }


    // remove and return the item from the front 
    public Item removeFirst() {
        if (size == 0) {
            throw new java.util.NoSuchElementException();
        }
        Item it =  first.item;
        if (first == last) {
            first = null;
            last = null;
        } else {
            first = first.next;
            first.prev = null;
        }
        --size;
        return it;
    }

    // remove and return the item from the end
    public Item removeLast() {
        // check if the deque is empty
        if (size == 0) {
            throw new java.util.NoSuchElementException();
        }
        Item it = last.item;
        if (first == last) {
            first = null;
            last = null;
        } else {
            last = last.prev;   
            last.next = null;
        }

        --size;
        return it;
    }

    
    // return and iterator over the item in order from front to end
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {

        private Node<Item> current;

        public ListIterator() {
            current = first;
        }

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new java.lang.UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }
            Item it = current.item;
            current = current.next;
            return it;
        }
    }


    // unit testing
    public static void main(String[] args) {
        Deque<String> d = new Deque<String>();

        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            if (s.equals("-")) {
                StdOut.println("Pop: " + d.removeLast());
                StdOut.println("Now size is : " + d.size());
                if (d.isEmpty()) {
                    StdOut.println("Is empty!!");
                } else {
                    StdOut.println("Is NOT empty!!");
                }
            } else {
                d.addFirst(s);
            }
        }
        for (String s : d) {
            StdOut.println(s);
        }
    }
}
