import com.sun.org.apache.xpath.internal.objects.XNodeSet;

public class LinkedListDeque<T> {

    private Node<T> cursor;

    private static class Node<E> {
        private E _item;
        private Node<E> _prev;
        private Node<E> _next;

        public Node() {
            _item = null;
            _prev = null;
            _next = null;
        }

        public Node(E item, Node<E> prev, Node<E> next) {
            _item = item;
            _prev = prev;
            _next = next;
        }
    }

    private int _size;
    private Node<T> sentF;
    private Node<T> sentL;

    public LinkedListDeque() {
        _size = 0;
        sentF = new Node<>();
//      Avoid the special case when list is null and then a new node is added into the list.
//      In this case, without a fixed last sentinel, the sentL still references the old sentF node,
//          and need to be adjusted additionally.
        sentL = new Node<>(null, sentF, null);
        sentF._next = sentL;
    }

    public LinkedListDeque(LinkedListDeque<T> other) {
        _size = other.size();
        sentF = new Node<>(other.sentF._item, null, null);
        Node<T> cursor = sentF;
        Node<T> otherCursor = other.sentF;
        for (int i = 0; i <= other.size(); i++) {
            cursor._next = new Node<>(otherCursor._next._item, cursor, null);
            cursor = cursor._next;
            otherCursor = otherCursor._next;
        }
    }

    public static <T> LinkedListDeque<T> copyDeque(LinkedListDeque<T> other) {
        LinkedListDeque<T> lld = new LinkedListDeque<>();
        lld._size = other.size();
        lld.sentF = copyNode(other.sentF);
        Node<T> next = lld.sentF._next;
        while (next != null) {
            next = next._next;
        }
        lld.sentL = next;
        return lld;
    }

    /**
     * A deep copy method for Node.
     *
     * The method would not only return a new node for the item included in the node,
     *     but also generate new nodes for every node it references.
     *
     * This method is a helper method for the copyDeque method,
     *     and will set the Deque object sentF reference and sentL reference to the proper nodes.
     *
     * @param node the original node awaiting to be copied
     * @return a new node from an entire new deque with each node copied from the node's original deque.
     */
    private static <T> Node<T> copyNode(Node<T> node) {
        Node<T> cNode = new Node<>(node._item, null, null);
        if (node._prev != null) {
            cNode._prev = copyNode(node._prev);
        }
        if (node._next != null) {
            cNode._next = copyNode(node._next);
        }
        return cNode;
    }

    public void addFirst(T item) {
        Node<T> newNode = new Node<>(item, sentF, sentF._next);
        newNode._next._prev = newNode;
        newNode._prev._next = newNode;
        _size++;
    }

    public void addLast(T item) {
        Node<T> newNode = new Node<>(item, sentL._prev, sentL);
        newNode._next._prev = newNode;
        newNode._prev._next = newNode;
        _size++;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public int size() {
        return _size;
    }

    public void printDeque() {
        Node<T> cursor = sentF;
        for (int i = 0; i < size(); i++) {
            cursor = cursor._next;
            System.out.print(String.valueOf(cursor._item) + " ");
        }
        System.out.println("");
    }

    public T removeFirst() {
        Node<T> first = sentF._next;
        sentF._next = first._next;
        first._next._prev = sentF;
        _size--;
        return first._item;
    }

    public T removeLast() {
        Node<T> last = sentL._prev;
        sentL._prev = last._prev;
        last._prev._next = sentL;
        _size--;
        return last._item;
    }

    public T get(int index) {
        if (index >= size()) {
            System.err.println("index out of bound!");
            return null;
        }
        Node<T> cursor = sentF;
        for (int i = 0; i <= index; i++) {
            cursor = cursor._next;
        }
        return cursor._item;
    }

    public T getRecursive(int index) {
        if (index >= size()) {
            System.err.println("index out of bound!");
            return null;
        }
        return getHelper(sentF._next, index);
    }

    private T getHelper(Node<T> cursor, int index) {
        if (index == 0) {
            return cursor._item;
        }
        return getHelper(cursor._next, index - 1);
    }
}

