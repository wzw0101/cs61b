package es.datastructur.synthesizer;
import org.junit.Test;
import static org.junit.Assert.*;

/** Tests the ArrayRingBuffer class.
 *  @author Josh Hug
 */

public class TestArrayRingBuffer {
    @Test
    public void someTest() {
        BoundedQueue<Integer> bq = new ArrayRingBuffer<>(5);
        assertEquals(5, bq.capacity());
        assertEquals(0, bq.fillCount());

        for (int i = 0; i < 5; i++) {
            bq.enqueue(i);
        }
        assertEquals(5, bq.fillCount());
        for (int i = 0; i < 5; i++) {
            assertEquals(Integer.valueOf(i), bq.peek());
            assertEquals(Integer.valueOf(i), bq.dequeue());
        }
        assertEquals(0, bq.fillCount());
    }

    @Test
    public void throwExceptionTest() {
        BoundedQueue<Integer> bq = new ArrayRingBuffer<>();
        bq.dequeue();
    }

    @Test
    public void arrayRingBufferIterableTest() {
        BoundedQueue<Integer> bq = new ArrayRingBuffer<>(5);
        for (int i = 0; i < 5; i++) {
            bq.enqueue(i);
        }
        int expect = 0;
        for (Integer i : bq) {
            assertEquals(expect, i.longValue());
            expect++;
        }
    }

    /** Calls tests for ArrayRingBuffer. */
/*    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestArrayRingBuffer.class);
    }*/
} 
