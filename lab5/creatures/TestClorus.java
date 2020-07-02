package creatures;

import huglife.*;
import org.junit.Test;
import static org.junit.Assert.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class TestClorus {
    @Test
    public void testClorusColor() {
        Clorus c = new Clorus();
        Color actual = c.color();
        Color expected = new Color(34, 0, 231);
        assertEquals(expected, actual);
    }

    @Test
    public void testClorusReplication() {
        Clorus c = new Clorus(2);
        Clorus cc = c.replicate();
        assertNotEquals(c, cc);
        assertEquals(c.color(), cc.color());
        assertEquals(1.0, c.energy(), 0.0001);
        assertEquals(1.0, cc.energy(), 0.0001);
    }

    @Test
    public void testActionChoice() {
        Action expected;
        Action actual;
        Clorus c = new Clorus(2);

        // no empty adjacent spaces even filled with Plips; STAY
        Map<Direction, Occupant> noEmpty = new HashMap<>();
        noEmpty.put(Direction.TOP, new Plip());
        noEmpty.put(Direction.BOTTOM, new Plip());
        noEmpty.put(Direction.LEFT, new Plip());
        noEmpty.put(Direction.RIGHT, new Plip());

        expected = new Action(Action.ActionType.STAY);
        actual = c.chooseAction(noEmpty);

        assertEquals(expected, actual);

        // any Plips around but still has empty spaces; ATTACK
        Map<Direction, Occupant> anyPlips = new HashMap<>();
        anyPlips.put(Direction.TOP, new Plip());
        anyPlips.put(Direction.BOTTOM, new Empty());
        anyPlips.put(Direction.LEFT, new Empty());
        anyPlips.put(Direction.RIGHT, new Empty());

        expected = new Action(Action.ActionType.ATTACK, Direction.TOP);
        actual = c.chooseAction(anyPlips);

        assertEquals(expected, actual);

        // energy >= 1; REPLICATE
        Map<Direction, Occupant> noPlips = new HashMap<>();
        noPlips.put(Direction.TOP, new Empty());
        noPlips.put(Direction.BOTTOM, new Impassible());
        noPlips.put(Direction.LEFT, new Empty());
        noPlips.put(Direction.RIGHT, new Empty());

        Action.ActionType expectedAt = Action.ActionType.REPLICATE;
        Action.ActionType actualAt = c.chooseAction(noPlips).type;
        assertEquals(expectedAt, actualAt);


        // energy < 1; MOVE
        c = new Clorus(.99);
        expectedAt = Action.ActionType.MOVE;
        actualAt = c.chooseAction(noPlips).type;
        assertEquals(expectedAt, actualAt);
    }
}
