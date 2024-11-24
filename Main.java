package dc_fp;

public class Main {
    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            System.out.println("Enter root path");
            return;
        }

        NodeCombiner nc = new NodeCombiner();

        nc.run(args[0]);

        for (Node n : nc.getNodes().make()) {
            System.out.println("File: " + n.getPath());
            System.out.println("Content:\n" + n.getContent() + "\n");
        }
    }
}