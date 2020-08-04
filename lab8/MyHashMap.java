import java.util.*;

public class MyHashMap<K, V> implements Map61B<K, V> {

    private static final int DEFAULT_INITIAL_SIZE = 16;
    private static final double DEFAULT_LOAD_FACTOR = 0.75;

    public static class Entry<K, V> {
        private K key;
        private V value;

        public Entry() {}

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private int size;
    private int capacity;
    private final double LOAD_FACTOR;

    private List<Entry<K, V>>[] listsOfEntries;

    public MyHashMap() {
        this(DEFAULT_INITIAL_SIZE, DEFAULT_LOAD_FACTOR);
    }

    public MyHashMap(int initialSize) {
        this(initialSize, DEFAULT_LOAD_FACTOR);
    }

    public MyHashMap(int initialSize, double loadFactor) {
        size = 0;
        capacity = initialSize;
        LOAD_FACTOR = loadFactor;

        listsOfEntries = new LinkedList[capacity];
    }

    @Override
    public void clear() {
        size = 0;
        Arrays.fill(listsOfEntries, null);
    }

    private int mapKey2Index(K key) {
        return (key.hashCode() & 0x7fffffff) % capacity;
    }

    @Override
    public boolean containsKey(K key) {
        List<Entry<K, V>> listOfEntries = listsOfEntries[mapKey2Index(key)];
        if (listOfEntries == null) {
            return false;
        }
        for (Entry<K, V> entry : listOfEntries) {
            if (entry.key.equals(key)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public V get(K key) {
        List<Entry<K, V>> listOfEntries = listsOfEntries[mapKey2Index(key)];
        if (listOfEntries == null) {
            return null;
        }
        for (Entry<K, V> entry : listOfEntries) {
            if (entry.key.equals(key)) {
                return entry.value;
            }
        }
        return null;
    }

    @Override
    public int size() {
        return size;
    }

    private void resize() {
        capacity *= 2;
        List<Entry<K, V>>[] anotherListsOfEntries = new LinkedList[capacity];
        for (List<Entry<K, V>> listOfEntries : listsOfEntries) {
            if (listOfEntries != null) {
                for (Entry<K, V> entry : listOfEntries) {
                    int index = mapKey2Index(entry.key);
                    if (anotherListsOfEntries[index] == null) {
                        anotherListsOfEntries[index] = new LinkedList<>();
                    }
                    anotherListsOfEntries[index].add(entry);
                }
            }
        }
        listsOfEntries = anotherListsOfEntries;
    }

    @Override
    public void put(K key, V value) {
        if((((double) size) / capacity) >= LOAD_FACTOR) {
            resize();
        }

        int index = mapKey2Index(key);
        List<Entry<K, V>> listOfEntries = listsOfEntries[index];
        if (listOfEntries == null) {
            listsOfEntries[index] = new LinkedList<>();
            listsOfEntries[index].add(new Entry<>(key, value));
            size++;
            return;
        }
        for (Entry<K, V> entry : listOfEntries) {
            if (entry.key.equals(key)) {
                entry.value = value;
                return;
            }
        }
        size++;
        listOfEntries.add(new Entry<>(key, value));
    }

    @Override
    public Set<K> keySet() {
        Set<K> set = new HashSet<>();
        for (List<Entry<K, V>> listOfEntries : listsOfEntries) {
            if (listOfEntries != null) {
                for (Entry<K, V> entry : listOfEntries) {
                    set.add(entry.key);
                }
            }
        }
        return set;
    }

    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException("Remove entries in MyHashMap is not supported!");
    }

    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException("Remove entries in MyHashMap is not supported!");
    }

    @Override
    public Iterator<K> iterator() {
        return keySet().iterator();
    }

    public static void main(String[] args) {
        List list = null;
        for (Object o : list) {
            System.out.println("Hello, Java!");
        }
    }
}
