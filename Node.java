package dc_fp;

import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Node {
    private static Pattern _dirPattern = Pattern.compile("require '(.+)'");

    private String _path;
    private String _content;
    private Set<String> _directives;

    public Node(String path, String content) {
        _path = path;
        _content = content;
        _directives = new TreeSet<>();

        parseDirectives();
    }

    public String getPath() {
        return _path;
    }

    public String getContent() {
        return _content;
    }

    public Set<String> getDirectives() {
        return _directives;
    }

    public void addDirective(String dir) {
        _directives.add(dir);
    }

    public boolean hasDirective(String dir) {
        return _directives.contains(dir);
    }

    private void parseDirectives() {
        Matcher matcher = _dirPattern.matcher(_content);
        while (matcher.find()) {
            _directives.add(matcher.group(1));
        }
    }
}
