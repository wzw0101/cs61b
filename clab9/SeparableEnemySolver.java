import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class SeparableEnemySolver {

    Graph g;

    final int UNGROUPED = -1;
    final int GROUP0 = 0;
    final int GROUP1 = 1;
    final int GROUP_SIZE = 2;

    /**
     * Creates a SeparableEnemySolver for a file with name filename. Enemy
     * relationships are biderectional (if A is an enemy of B, B is an enemy of A).
     */
    SeparableEnemySolver(String filename) throws java.io.FileNotFoundException {
        this.g = graphFromFile(filename);
    }

    /** Alterntive constructor that requires a Graph object. */
    SeparableEnemySolver(Graph g) {
        this.g = g;
    }

    /**
     * Returns true if input is separable, false otherwise.
     */
    public boolean isSeparable() {


        Set<String> labels = g.labels();
        // Record the group ids for labels.
        // Divide all labels into two groups with id 0 and 1, -1 if the label has not been grouped.
        Map<String, Integer> labelToGroupId = new HashMap<>();
        // Index whether a label/vertex has been visited through BFS.
        Map<String, Boolean> marked = new HashMap<>();
        for (String label : labels) {
            labelToGroupId.put(label, UNGROUPED);
            marked.put(label, false);
        }
        // Fringe queue used for DFS operation.
        Queue<String> queue = new LinkedList<>();

        // Iterate through the labels/vertices in case some disconnected vertices are not visited
        // by previous BFS.
        for (String label : labels) {
            // BFS operation on a vertex ensures complete inclusion of vertices in a connected graph.
            // Therefore, for each time of the for iteration, an ungrouped vertex represent a new sub-graph
            // that has not been searched.
            if (labelToGroupId.get(label).equals(UNGROUPED)) {
                labelToGroupId.put(label, GROUP0);
                if (!visit(label, labelToGroupId, marked, queue)) {
                    return false;
                }

                while (queue.peek() != null) {
                    String visiting = queue.remove();
                    if (!visit(visiting, labelToGroupId, marked, queue)) {
                        return false;
                    }
                }
            }

        }

        return true;
    }

    private boolean visit(String visiting, Map<String, Integer> labelToGroupId,
                          Map<String, Boolean> marked, Queue<String> queue) {
        marked.put(visiting, true);
        Set<String> neighbors = g.neighbors(visiting);
        int groupId = labelToGroupId.get(visiting);
        // every vertex directly connected with this visiting vertex should be put in the opposite group.
        int oppositeGroupId = (groupId + 1) % GROUP_SIZE;

        for (String neighbor : neighbors) {
            int neighborGroupId = labelToGroupId.get(neighbor);
            if (neighborGroupId == UNGROUPED) {
                labelToGroupId.put(neighbor, oppositeGroupId);
            } else if (neighborGroupId != oppositeGroupId) {
                // If the neighbor's group id has been set and is not in the opposite group,
                // a conflict shows up in the grouping process, meaning the graph is not separable.
                return false;
            }
            if (marked.get(neighbor).equals(false)) {
                queue.add(neighbor);
            }
        }
        return true;
    }

    /* HELPERS FOR READING IN CSV FILES. */

    /**
     * Creates graph from filename. File should be comma-separated. The first line
     * contains comma-separated names of all people. Subsequent lines each have two
     * comma-separated names of enemy pairs.
     */
    private Graph graphFromFile(String filename) throws FileNotFoundException {
        List<List<String>> lines = readCSV(filename);
        Graph input = new Graph();
        for (int i = 0; i < lines.size(); i++) {
            if (i == 0) {
                for (String name : lines.get(i)) {
                    input.addNode(name);
                }
                continue;
            }
            assert(lines.get(i).size() == 2);
            input.connect(lines.get(i).get(0), lines.get(i).get(1));
        }
        return input;
    }

    /**
     * Reads an entire CSV and returns a List of Lists. Each inner
     * List represents a line of the CSV with each comma-seperated
     * value as an entry. Assumes CSV file does not contain commas
     * except as separators.
     * Returns null if invalid filename.
     *
     * @source https://www.baeldung.com/java-csv-file-array
     */
    private List<List<String>> readCSV(String filename) throws java.io.FileNotFoundException {
        List<List<String>> records = new ArrayList<>();
        Scanner scanner = new Scanner(new File(filename));
        while (scanner.hasNextLine()) {
            records.add(getRecordFromLine(scanner.nextLine()));
        }
        return records;
    }

    /**
     * Reads one line of a CSV.
     *
     * @source https://www.baeldung.com/java-csv-file-array
     */
    private List<String> getRecordFromLine(String line) {
        List<String> values = new ArrayList<String>();
        Scanner rowScanner = new Scanner(line);
        rowScanner.useDelimiter(",");
        while (rowScanner.hasNext()) {
            values.add(rowScanner.next().trim());
        }
        return values;
    }

    /* END HELPERS  FOR READING IN CSV FILES. */

}
