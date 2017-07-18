package im.hujh.formula.node;

import im.hujh.formula.Pos;

/**
 * @author hujh
 */
public abstract class Node {

    private final NodeType type;
    private final Pos pos;

    public Node(NodeType type, Pos pos) {
        this.type = type;
        this.pos = pos;
    }

    public abstract void accept(NodeVisitor visitor) throws Exception;

    public NodeType getType() {
        return type;
    }

    public Pos getPos() {
        return pos;
    }
}
