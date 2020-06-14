package es.datastructur.synthesizer;


//Note: This file will not compile until you complete task 1 (BoundedQueue).
public class GuitarString {
    /** Constants. Do not change. In case you're curious, the keyword final
     * means the values cannot be changed at runtime. */
    private static final int SR = 44100;      // Sampling Rate
    private static final double DECAY = .996; // energy decay factor

    /* Buffer for storing sound data. */
    private BoundedQueue<Double> buffer;

    /* Create a guitar string of the given frequency.  */
    public GuitarString(double frequency) {
        // TODO: Create a buffer with capacity = SR / frequency. You'll need to
        //       cast the result of this divsion operation into an int. For better
        //       accuracy, use the Math.round() function before casting.
        //       Your buffer should be initially filled with zeros.
        buffer = new ArrayRingBuffer<Double>((int) Math.round(SR / frequency));
        while (buffer.capacity() != buffer.fillCount()) {
            buffer.enqueue(0d);
        }
    }


    /* Pluck the guitar string by replacing the buffer with white noise. */
    public void pluck() {
        // TODO: Dequeue everything in the buffer, and replace it with random numbers
        //       between -0.5 and 0.5. You can get such a number by using:
        //       double r = Math.random() - 0.5;
        //
        //       Make sure that your random numbers are different from each other.
        while (buffer.fillCount() != 0) {
            buffer.dequeue();
        }

        while (buffer.fillCount() != buffer.capacity()) {
            buffer.enqueue(Math.random() - 0.5);
        }
    }

    /* Advance the simulation one time step by performing one iteration of
     * the Karplus-Strong algorithm.
     */
    public void tic() {
        // TODO: Dequeue the front sample and enqueue a new sample that is
        //       the average of the two multiplied by the DECAY factor.
        //       Do not call StdAudio.play().
        double oldDouble = buffer.dequeue();
        double newDouble = (buffer.peek() + oldDouble) / 2 * DECAY;
        buffer.enqueue(newDouble);
    }

    /* Return the double at the front of the buffer. */
    public double sample() {
        // TODO: Return the correct thing.
        return buffer.peek();
    }

    public static void main(String[] args) {
        GuitarString gs = new GuitarString(11000);

//        Test the pluck method
        gs.pluck();
        System.out.println(gs.buffer.peek());
        gs.tic();

//        while (gs.buffer.fillCount() != 0) {
//            System.out.println(gs.buffer.dequeue());
//        }
    }

}

