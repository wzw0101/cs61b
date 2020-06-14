<<<<<<< HEAD
import org.junit.Test;
=======
/*import org.junit.Test;
>>>>>>> f81312d8d4213a7da5d32b68cf8ed5685caf27a7
import static org.junit.Assert.*;

public class TestPalindrome {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();
<<<<<<< HEAD
    static OffByOne offByOne = new OffByOne();
=======
>>>>>>> f81312d8d4213a7da5d32b68cf8ed5685caf27a7

    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }
<<<<<<< HEAD

    @Test
    public void testIsPalindromeReturnsTrue() {
        assertTrue(palindrome.isPalindrome("mom"));
        assertTrue(palindrome.isPalindrome("saippuakivikauppias"));
        assertTrue(palindrome.isPalindrome("aabbaa"));
        assertTrue(palindrome.isPalindrome("tattarrattat"));
        assertTrue(palindrome.isPalindrome(""));
        assertTrue(palindrome.isPalindrome("2"));
    }

    @Test
    public void  testIsPalindromeReturnsFalse() {
        assertFalse(palindrome.isPalindrome("cat"));
        assertFalse(palindrome.isPalindrome("element"));
        assertFalse(palindrome.isPalindrome("Hello world"));
        assertFalse(palindrome.isPalindrome("i do not know what to say"));
        assertFalse(palindrome.isPalindrome("you make my season start to change"));
    }

    @Test
    public void testIsPalindromeUsingCharacterComparatorReturnsTrue() {
        assertTrue(palindrome.isPalindrome("flake", offByOne));
        assertTrue(palindrome.isPalindrome("", offByOne));
        assertTrue(palindrome.isPalindrome("a", offByOne));
    }

    @Test
    public void testIsPalindromeUsingCharacterComparatorReturnsFalse() {
        assertFalse(palindrome.isPalindrome("kz", offByOne));
        assertFalse(palindrome.isPalindrome("weird", offByOne));
    }
}
=======
}     Uncomment this class once you've created your Palindrome class. */
>>>>>>> f81312d8d4213a7da5d32b68cf8ed5685caf27a7
