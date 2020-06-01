public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        Deque<Character> deque = new ArrayDeque<>();
        char[] chars = word.toCharArray();
        for (char c : chars) {
            deque.addLast(c);
        }
        return deque;
    }

    public boolean isPalindrome(String word) {
        return isPalindromeHelper(wordToDeque(word));
    }

    private boolean isPalindromeHelper(Deque<Character> deque) {
        if (deque.size() <= 1) {
            return true;
        }
        return deque.removeFirst().equals(deque.removeLast()) && isPalindromeHelper(deque);
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        return isPalindromeHelper(wordToDeque(word), cc);
    }

    private boolean isPalindromeHelper(Deque<Character> deque, CharacterComparator cc) {
        if (deque.size() <= 1) {
            return true;
        }
        return cc.equalChars(deque.removeFirst(), deque.removeLast()) && isPalindromeHelper(deque, cc);
    }
}
