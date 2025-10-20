import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Minimal singly-linked list implementation without Collections.
 */
public class MyLinkedList<T> implements Iterable<T> {
    private MyLinkedListNode<T> head;
    private int size = 0;

    public MyLinkedList() { head = null; }

    public void addFirst(T value) {
        MyLinkedListNode<T> n = new MyLinkedListNode<>(value);
        n.next = head;
        head = n;
        size++;
    }

    public void addLast(T value) {
        MyLinkedListNode<T> n = new MyLinkedListNode<>(value);
        if (head == null) { head = n; size++; return; }
        MyLinkedListNode<T> cur = head;
        while (cur.next != null) cur = cur.next;
        cur.next = n;
        size++;
    }

    public boolean remove(T value) {
        MyLinkedListNode<T> cur = head, prev = null;
        while (cur != null) {
            if ((cur.data == null && value == null) || (cur.data != null && cur.data.equals(value))) {
                if (prev == null) head = cur.next; else prev.next = cur.next;
                size--;
                return true;
            }
            prev = cur;
            cur = cur.next;
        }
        return false;
    }

    public int size() { return size; }

    public T getAt(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        MyLinkedListNode<T> cur = head;
        for (int i=0;i<index;i++) cur = cur.next;
        return cur.data;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private MyLinkedListNode<T> cur = head;
            @Override
            public boolean hasNext() { return cur != null; }
            @Override
            public T next() {
                if (cur == null) throw new NoSuchElementException();
                T d = cur.data; cur = cur.next; return d;
            }
        };
    }
}