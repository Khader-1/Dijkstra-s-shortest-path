package model;

/**
 *
 * @author Khader
 */
public class Graph {

    private final double[][] graph;
    private final Node[] nodes;
    private int size;

    public Graph(int length) {
        graph = new double[length][length];
        nodes = new Node[length];
    }

    public Node addNode(String name) {
        Node node = new Node(name, size);
        nodes[size++] = node;
        return node;
    }

    public Node getNode(int i) {
        return nodes[i];
    }

    public double getVal(int i, int j) {
        return graph[i][j];
    }

    public double getVal(Node node1, Node node2) {
        return graph[node1.index][node2.index];
    }

    public Node addNode(Node node) {
        nodes[size++] = node;
        return node;
    }

    public void addEdge(String node1, String node2, double val) {
        int num1 = 0;
        int num2 = 0;
        for (int i = 0; i < nodes.length; i++) {
            if (nodes[i].name.equals(node1)) {
                num1 = i;
            }
            if (nodes[i].name.equals(node2)) {
                num2 = i;
            }
        }
        graph[num1][num2] = val;
        graph[num2][num1] = val;

    }

    public void addEdge(Node node1, Node node2, double val) {
        graph[node1.index][node2.index] = val;
        graph[node2.index][node1.index] = val;

    }

    public static class Node {

        private final String name;
        private final int index;

        public Node(String name, int index) {
            this.name = name;
            this.index = index;
        }

        public String getName() {
            return name;
        }

        public int getIndex() {
            return index;
        }
    }

    @Override
    public String toString() {
        String line = "   ";
        for (Node node : nodes) {
            line += (node.getName() + "   ");
        }
        line += "\n";
        int count = 0;
        for (double[] row : graph) {
            line += (nodes[count++].getName() + " ");
            for (double item : row) {
                line += item + " ";
            }
            line += "\n";
        }
        return line;
    }

    public int getSize() {
        return size;
    }

    public Node[] getNodes() {
        return nodes;
    }
}
