package es.datastructur.synthesizer;

import java.util.Iterator;

public class ArrayRingBuffer<T> implements BoundedQueue<T> {

    public static final int DEFAULT_CAPACITY = 7;
    public static final int STARTING_LOCALTION = 0;
    private T[] items;
    private int capacity;
    private int size;
    private int first;
    private int last;

    public ArrayRingBuffer() {
        this(ArrayRingBuffer.DEFAULT_CAPACITY);
    }

    public ArrayRingBuffer(int capacity) {
        this.size = 0;
        this.capacity = capacity;
        this.first = this.last = ArrayRingBuffer.STARTING_LOCALTION;
        items = (T[]) new Object[capacity];
    }

    @Override
    public int capacity() {
        return capacity;
    }

    @Override
    public int fillCount() {
        return size;
    }

    @Override
    public void enqueue(T x) {
        if (isFull()) {
            throw new RuntimeException("Trying to add an item into a full buffer.");
        }
        items[last] = x;
        last = (last + 1) % capacity;
        size++;
    }

    @Override
    public T dequeue() {
        T result = peek();
        items[first] = null;
        first = (first + 1) % capacity;
        size--;
        return result;
    }

    @Override
    public T peek() {
        if (isEmpty()) {
            throw new RuntimeException("Trying getting an item from an empty buffer.");
        }
        return items[first];
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayRingBufferIterator();
    }

    private class ArrayRingBufferIterator implements Iterator<T> {

        private int cursor = first;

        @Override
        public boolean hasNext() {
            return cursor != last;
        }

        @Override
        public T next() {
            T result = ArrayRingBuffer.this.items[cursor];
            cursor = (cursor + 1) % capacity;
            return result;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != ArrayRingBuffer.class) {
            return false;
        }
        ArrayRingBuffer other = (ArrayRingBuffer) obj;
        if (other.items.length != items.length) {
            return false;
        }
        for (int i = 0; i < items.length; i++) {
            if (!items[i].equals(other.items[i])) {
                return false;
            }
        }
        return true;
    }
}
