import java.util.NoSuchElementException;
import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {

    private int size = 0;
    private Element first = null;
    private Element last = null;

    private class Element {
        
        private Item item;
        private Element next;
        private Element previous;
        
        Element(Item item, Element previous, Element next) {
            this.item = item;
            this.previous = previous;
            this.next = next;
        }
    }
    
    public static void main(String[] args) {
        Deque<Integer> d = new Deque<Integer>();
        d.addFirst(1);
        d.addFirst(2);
        d.addFirst(3);
        d.addFirst(4);
        d.print();
        while (!d.isEmpty()) {
            d.removeLast();
            System.out.println("size = " + d.size());
            d.print();
        }
    }
    
    private void print() {
        for (Item i : this) {
            System.out.print(i + " ");
        }
        System.out.println();
    }
    
    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void addFirst(Item item) {
        if (item == null)
            throw new NullPointerException("Attempt to add null");
        if (size == 0) {
            first = new Element(item, null, null);
            last = first;
        } else {
            first = new Element(item, null, first);
            first.next.previous = first;
        }
        size++;
    }

    public void addLast(Item item) {
        if (item == null)
            throw new NullPointerException("Attempt to add null");
        if (size == 0) {
            last = new Element(item, null, null);
            first = last;
        } else {
            last.next = new Element(item, last, null);
            last = last.next;
        }
        size++;
    }

    public Item removeFirst() {
        if (size == 0) 
            throw new NoSuchElementException(
                                "Attemt to remove element from empty deque.");
        Item firstItem = first.item;
        if (size > 1) { 
            first.next.previous = null;
        } else if (size == 1) {
            last = null;
        }
        first = first.next;
        size--;
        return firstItem;
    }

    public Item removeLast() {
        if (last == null)
            throw new NoSuchElementException(
                                "Attemt to remove element from empty deque.");
        Item lastItem = last.item;
        if (size > 1) {
            last.previous.next = null;
        } else if (size == 1) {
            first = null;
        }
        last = last.previous;
        size--;
        return lastItem;
    }

    public Iterator<Item> iterator() {
        return new Iterator<Item>() {
            private Element element = first;
            
            public boolean hasNext() { return element != null; }
            
            public Item next() {
                if (!hasNext())
                    throw new NoSuchElementException(
                                              "No more elements in iterator.");
                Element oldElement = element;
                element = element.next;
                return oldElement.item;
            }
            
            public void remove() {
                throw new UnsupportedOperationException(
                                     "Attempt to remove element in iterator.");
            }
        };
    }
}