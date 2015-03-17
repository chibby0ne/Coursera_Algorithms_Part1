/*  
* @file: RandomizedQueue.java
* @brief: 
* @author: Antonio Gutierrez
* @date: 2015-02-18
*/

import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private int queueSize;
    private Item[] queue;

    // construct and empty randomized queue
    public RandomizedQueue() {
        queue = (Item[]) new Object[1];
        queueSize = 0;
    }

    public boolean isEmpty() {
        return queueSize == 0;
    }

    public int size() {
        return queueSize;
    }

    public void enqueue(Item item) {
        if (item == null) {
            throw new java.lang.NullPointerException();
        }
        if (queue.length == queueSize) {
            resize(2 * queue.length);
        }
        queue[queueSize++] = item;
    }

    private void resize(int newSize) {
        Item[] newArray = (Item[]) new Object[newSize];
        for (int i = 0; i < queueSize; i++) {
            newArray[i] = queue[i];
        }
        queue = newArray;
    }


    public Item dequeue() {
        if (queueSize == 0) {
            throw new java.util.NoSuchElementException();
        }
        int rand = StdRandom.uniform(queueSize);
        Item val = queue[rand];
        queue[rand] = queue[queueSize - 1];
        queue[queueSize - 1] = null;
        queueSize--;
        if (queueSize == queue.length / 4) {
            resize(queue.length / 2);
        }
        return val;
    }

    public Item sample() {
        /*
         * If empty throw no such element exception, else return a random item
         */
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        return queue[StdRandom.uniform(queueSize)];
    }

    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private Item[] iteratorArray;
        private int it;

        public ListIterator() {
            iteratorArray = (Item[]) new Object[queueSize];
            for (int i = 0; i < queueSize; i++) {
                iteratorArray[i] = queue[i];
            }
            StdRandom.shuffle(iteratorArray);
            it = 0;
        }

        public boolean hasNext() {
            return it < queueSize;
        }

        public void remove() {
            throw new java.lang.UnsupportedOperationException();
        }

        public Item next() {
            if (it == queueSize) {
                throw new java.util.NoSuchElementException();
            }
            return iteratorArray[it++];
        }

    }


    /*
     * Unit test
     */
    public static void main(String[] args) {
        RandomizedQueue<String> q = new RandomizedQueue<String>();

        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            if (s.equals("-")) {
                StdOut.println("RandomDequeue: " + q.dequeue());
                StdOut.println("Now size is : " + q.size());
                if (q.isEmpty()) {
                    StdOut.println("Is empty!!");
                } else {
                    StdOut.println("Is NOT empty!!");
                }
            } else {
                q.enqueue(s);
            }
        }
        for (String s : q) {
            StdOut.println(s);
        }
    }

}
