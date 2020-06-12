import edu.princeton.cs.algs4.StdAudio;
import es.datastructur.synthesizer.GuitarString;

public class GuitarHero {
    public static final String KEYBOARD = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";
    private static GuitarString[] gstrings = new GuitarString[37];

    static {
        for (int i = 0; i < gstrings.length; i++) {
            gstrings[i] = new GuitarString(440.0 * Math.pow(2, (i - 24) / 12.0));
        }
    }

    public static void main(String[] args) {
        while (true) {

            /* check if the user has typed a key; if so, process it */
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                int index = GuitarHero.KEYBOARD.indexOf(key);
                if (index != -1) {
                    GuitarHero.gstrings[index].pluck();
                }
            }

            /* compute the superposition of samples */
            double sample = 0;
            for (GuitarString gs : gstrings) {
                sample += gs.sample();
            }

            /* play the sample on standard audio */
            StdAudio.play(sample);

            /* advance the simulation of each guitar string by one step */
            for (GuitarString gs : gstrings) {
                gs.tic();
            }
        }
    }
}
