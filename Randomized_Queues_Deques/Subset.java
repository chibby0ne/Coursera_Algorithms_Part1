/*  
* @file: Subset.java
* @brief: 
* @author: Antonio Gutierrez
* @date: 2015-02-23
*/

public class Subset {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> r = new RandomizedQueue<String>();
        String s = "";
        while (!StdIn.isEmpty()) {
            s = StdIn.readString();
            r.enqueue(s);
        }
        while (k-- > 0) {
            System.out.println(r.dequeue());
        }
    }
}
