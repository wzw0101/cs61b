package creatures;

import huglife.Creature;
import huglife.Direction;
import huglife.Action;
import huglife.Occupant;

import java.awt.Color;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;
import java.util.Random;

/**
 * An implementation of a motile pacifist photosynthesizer.
 *
 * @author Josh Hug
 */
public class Plip extends Creature {

    /**
     * red color.
     */
    private int r;
    /**
     * green color.
     */
    private int g;
    /**
     * blue color.
     */
    private int b;

    private static final double ENERGY_UPPER_BOUND = 2;
    private static final double ENERGY_LOWER_BOUND = 0;
    private static final double ENERGY_LOSE_WHEN_MOVING = 0.15;
    private static final double ENERGY_GAIN_WHEN_STAYING = 0.2;
    private static final double REP_ENERGY_RETAIN = 0.5;
    private static final double REP_ENERGY_GIVEN = 0.5;

    private double validateEnergy(double e) {
        if (e > ENERGY_UPPER_BOUND) {
            e = ENERGY_UPPER_BOUND;
        } else if  (e < ENERGY_LOWER_BOUND) {
            e = ENERGY_LOWER_BOUND;
        }
        return e;
    }

    /**
     * creates plip with energy equal to E.
     */
    public Plip(double e) {
        super("plip");
        r = 99;
        g = getR();
        b = 76;
        energy = validateEnergy(e);
    }

    /**
     * creates a plip with energy equal to 1.
     */
    public Plip() {
        this(1);
    }

    public int getG() {
        return (int) Math.round(96 * energy) + 63;
    }

    public int getR() {
        return r;
    }

    public int getB() {
        return b;
    }

    /**
     * Should return a color with red = 99, blue = 76, and green that varies
     * linearly based on the energy of the Plip. If the plip has zero energy,
     * it should have a green value of 63. If it has max energy, it should
     * have a green value of 255. The green value should vary with energy
     * linearly in between these two extremes. It's not absolutely vital
     * that you get this exactly correct.
     */
    public Color color() {
        return color(getR(), getG(), getB());
    }

    /**
     * Do nothing with C, Plips are pacifists.
     */
    public void attack(Creature c) {
        // do nothing.
    }

    /**
     * Plips should lose 0.15 units of energy when moving. If you want to
     * to avoid the magic number warning, you'll need to make a
     * private static final variable. This is not required for this lab.
     */
    public void move() {
        energy = validateEnergy(energy - ENERGY_LOSE_WHEN_MOVING);
    }


    /**
     * Plips gain 0.2 energy when staying due to photosynthesis.
     */
    public void stay() {
        energy = validateEnergy(energy + ENERGY_GAIN_WHEN_STAYING);
    }

    /**
     * Plips and their offspring each get 50% of the energy, with none
     * lost to the process. Now that's efficiency! Returns a baby
     * Plip.
     */
    public Plip replicate() {
        Plip p = new Plip(energy * REP_ENERGY_GIVEN);
        energy *= REP_ENERGY_RETAIN;
        return p;
    }

    /**
     * Plips take exactly the following actions based on NEIGHBORS:
     * 1. If no empty adjacent spaces, STAY.
     * 2. Otherwise, if energy >= 1, REPLICATE towards an empty direction
     * chosen at random.
     * 3. Otherwise, if any Cloruses, MOVE with 50% probability,
     * towards an empty direction chosen at random.
     * 4. Otherwise, if nothing else, STAY
     * <p>
     * Returns an object of type Action. See Action.java for the
     * scoop on how Actions work. See SampleCreature.chooseAction()
     * for an example to follow.
     */
    public Action chooseAction(Map<Direction, Occupant> neighbors) {
        // Rule 1
        Deque<Direction> emptyNeighbors = new ArrayDeque<>();
        boolean anyClorus = false;
        // (Google: Enhanced for-loop over keys of NEIGHBORS?)
        for (Direction d : neighbors.keySet()) {
            Occupant occupant = neighbors.get(d);
            if (occupant.name() == "empty") {
                emptyNeighbors.add(d);
            } else if (occupant.name() == "clorus") {
                anyClorus = true;
            }
        }

        if (emptyNeighbors.size() == 0) {
            return new Action(Action.ActionType.STAY);
        }

        // Rule 2
        // HINT: randomEntry(emptyNeighbors)
        if (energy() >= 1) {
            Direction direction = randomDirectionChoice(emptyNeighbors);
            return new Action(Action.ActionType.REPLICATE, direction);
        }

        // Rule 3
        if (anyClorus && new Random().nextBoolean()) {
            Direction direction = randomDirectionChoice(emptyNeighbors);
            return new Action(Action.ActionType.MOVE, direction);
        }

        // Rule 4
        return new Action(Action.ActionType.STAY);
    }

    private Direction randomDirectionChoice(Deque<Direction> emptyNeighbors) {
        int choice = new Random().nextInt(emptyNeighbors.size());
        Direction d = emptyNeighbors.removeFirst();
        for (int i = 0; i < choice; i++) {
            d = emptyNeighbors.removeFirst();
        }
        return d;
    }

}
