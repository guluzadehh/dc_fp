package dc_fp;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class NodeCombiner {
    private NodeList nodes;

    public NodeCombiner() {
        nodes = new NodeList();
    }

    public NodeList getNodes() {
        return nodes;
    }

    public void run(String path) throws IOException {
        File file = new File(path);

        if (!file.exists()) {
            throw new IllegalArgumentException(path + " doesn't exist");
        }

        if (file.isFile()) {
            nodes.add(new Node(file.getPath(), Files.readString(file.toPath())));
            return;
        }

        for (File f : file.listFiles()) {
            run(f.getPath());
        }
    }
}
