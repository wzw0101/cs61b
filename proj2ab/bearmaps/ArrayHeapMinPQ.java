package bearmaps;

import java.lang.reflect.Field;
import java.util.*;

public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T> {

    private final static int FIRST = 1;
    private final static int DEFAULT_CAPACITY = 2;
    private final static int RESIZING_FACTOR = 2;

    private static class HeapNode<E> {
        E item;
        double priority;

        public HeapNode(E item, double priority) {
            this.item = item;
            this.priority = priority;
        }

        public E getItem() {
            return item;
        }

        public double getPriority() {
            return priority;
        }

        @Override
        public int hashCode() {
            return item.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) return true;
            if (obj == null) return false;
            if (obj.getClass() != getClass()) return false;
            HeapNode<E> oNode = (HeapNode<E>) obj;
            return item.equals(oNode.item);

        }
    }

    private HeapNode<T>[] nodes;
    private Map<T, Integer> map;
    private int last;

    public ArrayHeapMinPQ() {
        nodes = (HeapNode<T>[]) new HeapNode[DEFAULT_CAPACITY];
        last = FIRST;
        map = new HashMap<>();
    }

    private void swimUp(int i) {
        while(i > FIRST && nodes[parent(i)].priority > nodes[i].priority) {
            swapNodes(i, parent(i));
            i = parent(i);
        }
    }

    private void swimDown(int i) {
        boolean done = false;
        while (!done) {
            if (leftChild(i) < last && nodes[i].priority > nodes[leftChild(i)].priority) {
                swapNodes(i, leftChild(i));
                i = leftChild(i);
            } else if (rightChild(i) < last && nodes[i].priority > nodes[rightChild(i)].priority) {
                swapNodes(i, rightChild(i));
                i = rightChild(i);
            } else {
                done = true;
            }
        }
    }

    private void swapNodes(int i, int j) {
        map.put(nodes[i].item, j);
        map.put(nodes[j].item, i);

        HeapNode<T> temp = nodes[i];
        nodes[i] = nodes[j];
        nodes[j] = temp;
    }

    private int parent(int i) {
        return i / 2;
    }

    private int leftChild(int i) {
        return i * 2;
    }

    private int rightChild(int i) {
        return i * 2 + 1;
    }

    private void resize(int length) {
        HeapNode<T>[] newHeap = (HeapNode<T>[]) new HeapNode[length];
        System.arraycopy(nodes, FIRST, newHeap, FIRST, last - FIRST);
        nodes = newHeap;
    }

    @Override
    public void add(T item, double priority) {
        if (contains(item)) {
            throw new IllegalArgumentException("item already exists");
        }

        if (last == nodes.length) {
            resize(RESIZING_FACTOR * nodes.length);
        }

        int l = last++;
        HeapNode<T> node = new HeapNode<>(item, priority);
        map.put(item, l);
        nodes[l] = node;
        swimUp(l);

    }

    @Override
    public boolean contains(T item) {
        return map.containsKey(item);
    }

    @Override
    public T getSmallest() {
        return nodes[FIRST].item;
    }

    @Override
    public T removeSmallest() {
        if (size() == 0) {
            throw new IllegalStateException("Removing an item from an empty priority queue");
        }

        last -= 1;
        T result = nodes[FIRST].item;
        nodes[FIRST] = nodes[last];
        nodes[last] = null;
        map.remove(result);
        if (nodes[FIRST] != null) map.put(nodes[FIRST].item, FIRST);

        swimDown(FIRST);

        if (nodes.length >= DEFAULT_CAPACITY && ((size() / ((double) nodes.length - 1)) <= 0.25)) {
            resize(nodes.length / RESIZING_FACTOR);
        }

        return result;
    }

    @Override
    public int size() {
        return last - FIRST;
    }

    @Override
    public void changePriority(T item, double priority) {
        if (!contains(item))
            throw new NoSuchElementException("item is not contained in the priority queue");

        int i = map.get(item);
        nodes[i].priority = priority;
        if (i > FIRST && nodes[i].priority < nodes[parent(i)].priority) {
            swimUp(i);
        } else {
            swimDown(i);
        }
    }

}
