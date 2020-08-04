import java.util.ArrayList;
import java.util.PriorityQueue;

/**
 * Solver for the Flight problem (#9) from CS 61B Spring 2018 Midterm 2.
 * Assumes valid input, i.e. all Flight start times are >= end times.
 * If a flight starts at the same time as a flight's end time, they are
 * considered to be in the air at the same time.
 */
public class FlightSolver {

    private PriorityQueue<PassengerChange> pq = new PriorityQueue<PassengerChange>((o1, o2) -> {
        if (o1 == o2) return 0;
        if (o1.time < o2.time) return -1;
        else if (o1.time > o2.time) return 1;
        else if (o1.pdelta > 0 && o2.pdelta < 0) return -1;
        else if (o1.pdelta < 0 && o2.pdelta > 0) return 1;
        else return 0;
    });

    public FlightSolver(ArrayList<Flight> flights) {
        for (Flight flight : flights) {
            pq.add(new PassengerChange(flight.startTime, flight.passengers));
            pq.add(new PassengerChange(flight.endTime, -flight.passengers));
        }
    }

    public int solve() {
        int max = 0;
        int sum = 0;
        while (pq.size() > 0) {
            sum += pq.poll().pdelta;
            if (sum > max) max = sum;
        }
        return max;
    }

}

class PassengerChange {
    int time;
    int pdelta;

    public PassengerChange(int time, int pdelta) {
        this.time = time;
        this.pdelta = pdelta;
    }
}