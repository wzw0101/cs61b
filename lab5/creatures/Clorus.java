package creatures;

import huglife.Action;
import huglife.Creature;
import huglife.Direction;
import huglife.Occupant;

import java.awt.*;

import java.util.*;


public class Clorus extends Creature {

    private static double ENERGY_LOWER_BOUND = 0;
    private static double ENERGY_LOSS_ON_MOVING = 0.03;
    private static double ENERGY_LOSS_ON_STAYING = 0.01;
    private static double ENERGY_GIVEN_TO_OFFSPRING = .5;
    private static double ENERGY_RETAINED_ON_REPLICATION = .5;
    private static int REPLICATION_ENERGY = 1;

    private int r = 34;
    private int g = 0;
    private int b = 231;

    public Clorus(double e) {
        super("clorus");
        energy = validateEnergy(e);
    }

    public Clorus() {
        this(2);
    }

    private double validateEnergy(double e) {
        if (e < ENERGY_LOWER_BOUND) {
            e = ENERGY_LOWER_BOUND;
        }
        return e;
    }

    @Override
    public void move() {
        energy -= ENERGY_LOSS_ON_MOVING;
        energy = validateEnergy(energy);
    }

    @Override
    public void attack(Creature c) {
        energy += c.energy();
    }

    @Override
    public Clorus replicate() {
        Clorus offspring = new Clorus(ENERGY_GIVEN_TO_OFFSPRING * energy);
        energy *= ENERGY_RETAINED_ON_REPLICATION;
        return offspring;
    }

    @Override
    public void stay() {
        energy -= ENERGY_LOSS_ON_STAYING;
        energy = validateEnergy(energy);
    }

    @Override
    public Action chooseAction(Map<Direction, Occupant> neighbors) {
        boolean noEmpty = true;
        Deque<Direction> plipsAround = new ArrayDeque<>();
        Deque<Direction> emptiesAround = new ArrayDeque<>();
        for (Direction direction : neighbors.keySet()) {
            Occupant occupant = neighbors.get(direction);
            if (occupant.name() == "empty") {
                noEmpty = false;
                emptiesAround.addLast(direction);
            } else if (occupant.name() == "plip") {
                plipsAround.addLast(direction);
            }
        }
        // If no empty squares, the Clours will STAY.
        if (noEmpty) {
            return new Action(Action.ActionType.STAY);
        }
        // If any plips around, the Clorus will attack one of them randomly.
        if (plipsAround.size() > 0) {
            Direction direction = randomDirectionChoice(plipsAround);
            return new Action(Action.ActionType.ATTACK, direction);
        }
        // If the Clorus has energy greater or equal to one, it will REPLICATE to a random empty square.
        if (energy >= REPLICATION_ENERGY) {
            Direction direction = randomDirectionChoice(emptiesAround);
            return new Action(Action.ActionType.REPLICATE, direction);
        }
        // Otherwise, the Clorus will MOVE to a random empty square.
        Direction direction = randomDirectionChoice(emptiesAround);
        return new Action(Action.ActionType.MOVE, direction);
    }

    @Override
    public Color color() {
        return new Color(r, g, b);
    }

    private Direction randomDirectionChoice(Deque<Direction> directions) {
        int choice = new Random().nextInt(directions.size());
        Direction d = directions.removeFirst();
        for (int i = 0; i < choice; i++) {
            d = directions.removeFirst();
        }
        return d;
    }
}
