import java.security.Key;
import java.util.*;

public class BSTMap<K extends Comparable<K>, V extends Comparable<V>> implements Map61B<K, V> {

    private Node _root;
    private int _size;

    private class Node {
        private K _key;
        private V _value;
        private Node _left;
        private Node _right;

        public Node() {}

        public Node(K key, V value) {
            _key = key;
            _value = value;
        }

        public Node(K key, V value, Node left, Node right) {
            _key = key;
            _value = value;
            _left = left;
            _right = right;
        }

        private List<Node> toList() {
            List<Node> list = new ArrayList<>();
            if (_left != null) {
                list.addAll(_left.toList());
            }
            list.add(this);
            if (_right != null) {
                list.addAll(_right.toList());
            }
            return list;
        }
    }

    @Override
    public void clear() {
        _size = 0;
        _root = null;
    }

    private Node searchNodeHelper (Node root, K key) {
        if (root == null) {
            return null;
        }
        int compare = root._key.compareTo(key);
        if (compare ==0) {
            return root;
        } else if (compare < 0) {
            return searchNodeHelper(root._right, key);
        } else {
            return searchNodeHelper(root._left, key);
        }
    }

    @Override
    public boolean containsKey(K key) {
        return searchNodeHelper(_root, key) != null;
    }

    @Override
    public V get(K key) {
        Node node = searchNodeHelper(_root, key);
        if (node == null) {
            return null;
        } else {
            return node._value;
        }
    }

    @Override
    public int size() {
        return _size;
    }

    @Override
    public void put(K key, V value) {
        _root = putHelper(_root, key, value);
    }

    private Node putHelper(Node root, K key, V value) {
        if (root == null) {
            _size += 1;
            return new Node(key, value);
        }
        int compare = root._key.compareTo(key);
        if (compare == 0) {
            root._value = value;
            return root;
        } else if (compare < 0) {
            root._right = putHelper(root._right, key, value);
            return root;
        } else {
            root._left = putHelper(root._left, key, value);
            return root;
        }
    }

    @Override
    public Set<K> keySet() {
        Set<K> set = new TreeSet<>();
        return keySetHelper(_root, set);
    }

    private Set<K> keySetHelper(Node node, Set<K> set) {
        if (node == null) {
            return set;
        }
        set.addAll(keySetHelper(node._left, set));
        set.add(node._key);
        set.addAll(keySetHelper(node._right, set));
        return set;
    }

    @Override
    public V remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Argument key should not be set null to");
        }
        Node result = new Node();
        _root = removeHelper(key, _root, result);
        return result._value;
    }

    private Node removeHelper(K key, Node crntNode, Node result) {
        if (result == null) {
            throw new IllegalArgumentException("Result should not be set null to");
        }
        if (crntNode == null) {
            return null;
        }
        int cmpr = crntNode._key.compareTo(key);
        if (cmpr < 0) {
            crntNode._right = removeHelper(key, crntNode._right, result);
        } else if ( cmpr > 0) {
            crntNode._left = removeHelper(key, crntNode._left, result);
        } else {
            result._key = crntNode._key;
            result._value = crntNode._value;
            _size--;
            if (crntNode._left == null) {
                return crntNode._right;
            }
            if (crntNode._right == null) {
                return crntNode._left;
            }
            Node nearestSmall = max(crntNode._left);
            crntNode._left = removeHelper(nearestSmall._key, crntNode._left, new Node());
            crntNode._key = nearestSmall._key;
            crntNode._value = nearestSmall._value;
        }
        return crntNode;
    }

    private Node max(Node root) {
        if (root == null) {
            throw new IllegalArgumentException("root should not be set null to");
        }
        if (root._right == null) {
            return root;
        }
        return max(root._right);
    }

    @Override
    public V remove(K key, V value) {
        if (key == null) {
            return null;
        }
        Node result = new Node();
        _root = remove(key, value, _root, result);
        return result._value;
    }

    private Node remove(K key, V value, Node crntNode, Node result) {
        if (result == null) {
            throw new IllegalArgumentException("Result should not be set null to");
        }
        if (crntNode == null) {
            return null;
        }
        int cmpr = crntNode._key.compareTo(key);
        if (cmpr < 0) {
            crntNode._right = removeHelper(key, crntNode._right, result);
        } else if (cmpr > 0) {
            crntNode._left = removeHelper(key, crntNode._left, result);
        } else if(value.compareTo(crntNode._value) != 0){
            result._key = crntNode._key;
            result._value = crntNode._value;
            return crntNode;
        } else {
            _size--;
            result._key = crntNode._key;
            result._value = crntNode._value;
            if (crntNode._left == null) {
                return crntNode._right;
            }
            if (crntNode._right == null) {
                return crntNode._left;
            }
            Node nearestSmall = max(crntNode._left);
            crntNode._left = removeHelper(nearestSmall._key, crntNode._left, new Node());
            crntNode._key = nearestSmall._key;
            crntNode._value = nearestSmall._value;
        }
        return crntNode;
    }

    @Override
    public Iterator<K> iterator() {
        return keySet().iterator();
    }

}
