import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByOne {

    // You must use this CharacterComparator and not instantiate
    // new ones, or the autograder might be upset.
    static CharacterComparator offByOne = new OffByOne();

    // Your tests go here.
    @Test
    public void testEqualCharsReturnsTrue () {
        assertTrue(offByOne.equalChars('a', 'b'));
        assertTrue(offByOne.equalChars('m', 'n'));
        assertTrue(offByOne.equalChars('&', '%'));
    }

    @Test
    public void testEqualCharsReturnFalse () {
        assertFalse(offByOne.equalChars('d', 'g'));
        assertFalse(offByOne.equalChars('k', 'w'));
    }
}
