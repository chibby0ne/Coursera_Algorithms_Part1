/*  
* @file: TestDeque.java
* @brief: 
* @author: Antonio Gutierrez
* @date: 2015-02-18
*/

import java.util.Iterator;

public class TestDeque {
    private Deque<Integer> deque;

    // test null addition (front)
    public void TestAddNullItemFirst() {
        try {
            deque.addFirst(null);
        } catch(NullPointerException e){
            StdOut.println("NullPointerException catched!");
            // e.printStackTrace();
        }
        StdOut.println("Test Add Null item first passed!");
    }

    // test null addition (Last)
    public void TestAddNullItemLast() {
        try {
            deque.addLast(null);
        } catch(NullPointerException e){
            StdOut.println("NullPointerException catched!");
            // e.printStackTrace();
        }
        StdOut.println("Test Add Null item passed!");
    }

    
    // test if empty
    // test remove from an empty deque
    public void TestEmpty() {
        // if (deque.isEmpty() == true) {
        //     assert(deque.removeFirst());
        //     StdOut.println("It really is not empty!");
        
        while (!deque.isEmpty()) {
            deque.removeFirst();
        }
        try {
            deque.removeFirst();
        } catch(Exception e){
            StdOut.println("Exception when removing from empty deque catched!");
        }
        StdOut.println("Test Empty passed!");

    }

    // test add front
    public void TestAddFirst() {
        int v = 4;
        Integer val = new Integer(v);
        deque.addFirst(4);
        assert(val == deque.removeFirst());
        StdOut.println("Test Add First passed!");
    }

    // test add last
    public void TestAddLast() {
        int v = 4;
        Integer val = new Integer(v);
        deque.addLast(v);
        assert(val == deque.removeLast());
        StdOut.println("Test Add Last passed!");
    }

    // remove first
    public void TestRemoveFirst() {
        int v = 4;
        Integer val = new Integer(v);
        deque.addFirst(val);
        assert(val == deque.removeFirst());
        StdOut.println("Test Remove First passed!");
    }

    // remove last
    public void TestRemoveLast() {
        int v = 4;
        Integer val = new Integer(v);
        deque.addLast(val);
        assert(val == deque.removeLast());
        StdOut.println("Test Remove Last passed!");
    }

    // remove method in the iterator
    // iterator operation
    // next() when the deque is empty
    public void TestIterator() {
        for (int i = 1; i < 10; i++) {
            deque.addFirst(i);
        }
        Iterator<Integer> it = deque.iterator();
        int val = 1;
        while (it.hasNext()) {
            assert(val == it.next());
            val++;
        }
        try {
            it.remove();
        } catch(Exception e){
            StdOut.println("Exception caught when using remove() from iterator");
        }
        StdOut.println("Test Iterator passed!");
    }

    public static void main(String[] args) {
        TestDeque td = new TestDeque();
        // td.TestIterator();
        td.TestEmpty();
        td.TestAddNullItemFirst();
        td.TestAddNullItemLast();
        td.TestAddFirst();
        td.TestAddLast();
        td.TestRemoveFirst();
        td.TestRemoveLast();

    }
}

