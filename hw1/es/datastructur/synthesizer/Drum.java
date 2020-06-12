package es.datastructur.synthesizer;

import edu.princeton.cs.algs4.StdAudio;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdRandom;

public class Drum {
    /** Constants. Do not change. In case you're curious, the keyword final means
     * the values cannot be changed at runtime. We'll discuss this and other topics
     * in lecture on Friday. */
    private static final int SR = 44100;      // Sampling Rate
    private static final double DECAY = 1; // energy decay factor

    /* Buffer for storing sound data. */
    private BoundedQueue<Double> buffer;

    public Drum(double frequency) {
        buffer = new ArrayRingBuffer<Double>((int) Math.round(SR / frequency) * 2);
        while (buffer.capacity() != buffer.fillCount()) {
            buffer.enqueue(0d);
        }
    }

    /* Pluck the harp string by replacing the buffer with white noise. */
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
        // Flipping the sign of the new value before enqueueing it in tic()
        //     will change the sound from guitar-like to harp-like.
        if (StdRandom.bernoulli()) {
            buffer.enqueue(-newDouble);
            return;
        }
        buffer.enqueue(newDouble);
    }

    /* Return the double at the front of the buffer. */
    public double sample() {
        // TODO: Return the correct thing.
        return buffer.peek();
    }
}

class DrumHero {
    public static final String KEYBOARD = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";
    private static final Drum[] drums = new Drum[37];

    static {
        for (int i = 0; i < drums.length; i++) {
            drums[i] = new Drum(440.0 * Math.pow(2, (i - 24) / 12.0));
        }
    }

    public static void main(String[] args) {
        while (true) {

            /* check if the user has typed a key; if so, process it */
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                int index = HarpHero.KEYBOARD.indexOf(key);
                if (index != -1) {
                    DrumHero.drums[index].pluck();
                }
            }

            /* compute the superposition of samples */
            double sample = 0;
            for (Drum d : drums) {
                sample += d.sample();
            }

            /* play the sample on standard audio */
            StdAudio.play(sample);

            /* advance the simulation of each guitar string by one step */
            for (Drum d : drums) {
                d.tic();
            }
        }
    }
}
