public class ArrayDeque<E> implements Deque<E> {

    // size == (capacity + lp - fp - 1) % capacity
    private int size;
    private int capacity;
    private E[] items;
    // When size == 0 or size == capacity, lp == fp + 1;
    // fp and lp refer to the next position the new item should be put in.
    private int fp;
    private int lp;

    public ArrayDeque() {
        size = 0;
        capacity = 8;
        fp = 0;
        lp = 1;
        items = (E[]) new Object[capacity];
    }

    public ArrayDeque(ArrayDeque other) {
        size = other.size;
        capacity = other.capacity;
        fp = other.fp;
        lp = other.lp;
        items = (E[]) new Object[capacity];
        for (int i = 0; i < other.size(); i++) {
            addLast((E) other.get(i));
        }
    }

    /** resize method will only be invoked when size == capacity, i.e. fp == lp */
    private void resize(int expandedCapacity) {
        E[] expandedList = (E[]) new Object[expandedCapacity];
        // Copy items after fp till the end of the array to the new list where the first item is put in index 1.
        System.arraycopy(items, lp, expandedList, 1, capacity - lp);
        // Copy items before lp till the start of the array to the new list,
        //     where the first item is put after the last item placed during the last arraycopy operation.
        System.arraycopy(items, fp, expandedList, 1 + capacity - lp, fp + 1);
        fp = 0;
        lp = capacity + 1;
        capacity = expandedCapacity;
        items = expandedList;
    }

    @Override
    public void addFirst(E item) {
        if (size == capacity) {
            resize(2 * capacity);
        }

        items[fp] = item;
        // mod operation prodeuce negative result when its dividend is a negative integer
        // add a divisor to the result to produce a positive remainder.
        fp = --fp % capacity + capacity;
        size++;
    }

    @Override
    public void addLast(E item) {
        if (size == capacity) {
            resize(2 * capacity);
        }

        items[lp] = item;
        lp = ++lp % capacity;
        size++;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        for (int i = 0; i < size(); i++) {
            System.out.print(String.valueOf(get(i)) + " ");
        }
    }

    @Override
    public E removeFirst() {
        if (size == 0) {
            System.err.println("Trying to remove the first item from an empty list");
            return null;
        }
        // Note that fp may refer to the last item in a real array.
        fp = ++fp % capacity;
        E result = items[fp];
        items[fp] = null;
        size--;
        return result;
    }

    @Override
    public E removeLast() {
        if (size == 0) {
            System.err.println("Trying to remove the last item from an empty list");
            return null;
        }
        // Mod operation prodeuce negative result when its dividend is a negative integer.
        // Add a divisor to the result to produce a positive remainder.
        // Note that lp may refer to the first item in a real array.
        lp = --lp % capacity + capacity;
        E result = items[lp];
        items[lp] = null;
        size--;
        return result;
    }

    @Override
    public E get(int index) {
        if (index < 0 || index >= size) {
            System.err.println("Index out of bounds!");
            return null;
        }

        return items[(index + fp + 1) % capacity];
    }

    public static void main(String[] args) {

    }
}
