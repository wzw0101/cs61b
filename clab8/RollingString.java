import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * A String-like class that allows users to add and remove characters in the String
 * in constant time and have a constant-time hash function. Used for the Rabin-Karp
 * string-matching algorithm.
 */
class RollingString{

    /**
     * Number of total possible int values a character can take on.
     * DO NOT CHANGE THIS.
     */
    static final int UNIQUECHARS = 128;

    /**
     * The prime base that we are using as our mod space. Happens to be 61B. :)
     * DO NOT CHANGE THIS.
     */
    static final int PRIMEBASE = 6113;

    private char[] chars;
    private int first;

    /**
     * Initializes a RollingString with a current value of String s.
     * s must be the same length as the maximum length.
     */
    public RollingString(String s, int length) {
        assert(s.length() == length);
        /* FIX ME */
        chars = s.toCharArray();
        first = 0;
        hash = 0;
        for (char c : chars) {
            hash = ((hash * UNIQUECHARS) % PRIMEBASE + Character.hashCode(c)) % PRIMEBASE;
        }
    }

    /**
     * Adds a character to the back of the stored "string" and 
     * removes the first character of the "string". 
     * Should be a constant-time operation.
     */
    public void addChar(char c) {
        /* FIX ME */
        int hlc = Character.hashCode(chars[first]);
        for (int i = 0; i < chars.length - 1; i++) {
            hlc = hlc * UNIQUECHARS % PRIMEBASE;
        }
        hash = ((hash + PRIMEBASE - hlc) * UNIQUECHARS + Character.hashCode(c)) % PRIMEBASE;
        chars[first] = c;
        first = (first + 1) % chars.length;
    }


    /**
     * Returns the "string" stored in this RollingString, i.e. materializes
     * the String. Should take linear time in the number of characters in
     * the string.
     */
    public String toString() {
        StringBuilder strb = new StringBuilder();
        /* FIX ME */
        for (int cf = first; cf != first + chars.length; cf++){
            strb.append(chars[cf % chars.length]);
        }
        return strb.toString();
    }

    /**
     * Returns the fixed length of the stored "string".
     * Should be a constant-time operation.
     */
    public int length() {
        /* FIX ME */
        return chars.length;
    }


    /**
     * Checks if two RollingStrings are equal.
     * Two RollingStrings are equal if they have the same characters in the same
     * order, i.e. their materialized strings are the same.
     */
    @Override
    public boolean equals(Object o) {
        /* FIX ME */
        if (o == null) return false;
        if (o == this) return true;
        if (o.getClass() != getClass()) return false;
        RollingString ors = (RollingString) o;
        return Arrays.equals(chars, ors.chars);
    }

    private int hash;

    /**
     * Returns the hashcode of the stored "string".
     * Should take constant time.
     */
    @Override
    public int hashCode() {
        /* FIX ME */
        return hash;
    }

    public static void main(String[] args) {
        System.out.println(128 * 6113);
        System.out.println(Integer.MAX_VALUE);
    }
}
