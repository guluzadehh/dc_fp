package dc_fp;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class NodeCombiner {
    private String _root;
    private Map<String, Node> _elements;

    public NodeCombiner(String root) {
        _root = root;
        _elements = new TreeMap<>();
    }

    public void combine() throws IOException {
        combineIterate(_root);
    }

    private void combineIterate(String path) throws IOException {
        File file = new File(path);

        if (!file.exists()) {
            throw new IllegalArgumentException(path + " doesn't exist");
        }

        if (file.isFile()) {
            Node node = new Node(file.getPath(), Files.readString(file.toPath()));
            _elements.put(node.getPath(), node);
            return;
        }

        for (File f : file.listFiles()) {
            combineIterate(f.getPath());
        }
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
            String fullDirPath = Paths.get(_root, dirName).toString();
            Node dirNode = _elements.get(fullDirPath);
            if (dirNode == null) {
                throw new Exception(String.format("%s: directive %s doesn't exist", n.getPath(), fullDirPath));
            }

            makeNode(dirNode, res, visited, visiting);
        }

        visiting.remove(n.getPath());
        visited.add(n.getPath());
        res.add(n);
    }
}
