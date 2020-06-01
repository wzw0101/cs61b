import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByN {

    @Test
    public void testEqualCharacterReturnsTrue() {
        OffByN offByN = new OffByN(3);
        assertTrue(offByN.equalChars('a', 'd'));
        assertTrue(offByN.equalChars('w', 'z'));
    }

    @Test
    public void testEqualCharacterReturnsFalse() {
        OffByN offByN = new OffByN(2);
        assertFalse(offByN.equalChars('a', 'z'));
        assertFalse(offByN.equalChars('b', 'y'));
    }
}
