import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    
    private Item[] queue = (Item[]) new Object[0];
    private int index = 0; // index of the next element 
                           // (index - 1 points to last element)
    
    public boolean isEmpty() {
        return index == 0;
    }
    
    public int size() {
        return index;
    }
    
    public static void main(String[] args) {
        RandomizedQueue<Integer> q = new RandomizedQueue<Integer>();
        for (int i = 0; i < 5; i++) {
            q.enqueue(i);
            System.out.println("size = " + q.size());
        }
    }
    
    public void enqueue(Item item) {
        if (item == null)
            throw new NullPointerException("Attempt to add null");
        int oldSize = queue.length;
        if (index >= oldSize) {
            int newSize;
            if (oldSize == 0) {
                newSize = 1;
            } else {
                newSize = oldSize * 2;
            }
            Item[] queue2 = (Item[]) new Object[newSize];
            for (int i = 0; i < oldSize; i++) {
                queue2[i] = queue[i];
            }
            queue = queue2;
        }
        int randIndex = (int) ((index + 1) * Math.random());
        if (randIndex == index) {
            queue[index] = item;
        } else {
            Item randItem = queue[randIndex];
            queue[randIndex] = item;
            queue[index] = randItem;
        }
        index++;
    }

    public Item dequeue() {
        if (index == 0) 
            throw new NoSuchElementException("Attempt to dequeue empty queue");
        int halfSize;
        if (queue.length == 1) {
            halfSize = 0;
        } else {
            halfSize = queue.length / 2;
        }
        if (index <= halfSize) {
            Item[] queue2 = (Item[]) new Object[halfSize];
            for (int i = 0; i < index; i++) {
                queue2[i] = queue[i];
            }
            queue = queue2;
        }
        return queue[--index];
    }
    
    public Item sample() {
        if (queue.length == 0) 
            throw new NoSuchElementException("Attempt to dequeue empty queue");
        return queue[(int) (index * Math.random())];
    }
    
    private class MyIterator implements Iterator<Item> {
        private RandomizedQueue<Item> randQueue;
            
            MyIterator() {
                randQueue = new RandomizedQueue<Item>();
                for (int i = 0; i < index; i++) {
                    randQueue.enqueue(queue[i]);
                }
            }
            
            public boolean hasNext() { 
                return !randQueue.isEmpty();
            }
            
            public Item next() {
                return randQueue.dequeue();
            }
            
            public void remove() {
                throw new UnsupportedOperationException(
                                     "Attempt to remove element in iterator.");
            }
    }
    
    public Iterator<Item> iterator() {
        return new MyIterator();
    }
}