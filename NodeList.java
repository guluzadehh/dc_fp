package dc_fp;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import dc_fp.NodeList;

public class NodeList {
    private Map<String, Node> _elements;

    public NodeList() {
        _elements = new TreeMap<>();
    }

    public void add(Node node) {
        _elements.put(node.getPath(), node);
    }

    public List<Node> make() throws Exception {
        List<Node> res = new ArrayList<>();
        Set<String> visited = new HashSet<>();
        Set<String> visiting = new HashSet<>();

        for (Node node : _elements.values()) {
            makeNode(node, res, visited, visiting);
        }

        return res;
    }

    private void makeNode(Node n, List<Node> res, Set<String> visited, Set<String> visiting) throws Exception {
        if (visiting.contains(n.getPath())) {
            throw new IllegalArgumentException("circular dependency: " + String.join(" ", visiting));
        }

        if (visited.contains(n.getPath()))
            return;

        for (String dirName : n.getDirectives()) {
            Node dirNode = _elements.get(dirName);
            if (dirNode == null) {
                throw new Exception(String.format("%s: directive %s doesn't exist", n.getPath(), dirName));
            }

            makeNode(dirNode, res, visited, visiting);
        }

        visiting.remove(n.getPath());
        visited.add(n.getPath());
        res.add(n);
    }

    public void print() {
        for (Node node : _elements.values()) {
            System.out.println(node.getPath());
        }
    }
}
