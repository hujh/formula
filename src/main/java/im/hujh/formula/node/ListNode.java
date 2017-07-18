package im.hujh.formula.node;

import im.hujh.formula.Pos;
import java.util.Iterator;

import java.util.LinkedList;

/**
 * @author hujh
 */
public class ListNode extends Node implements Iterable<Node> {

    private final LinkedList<Node> list;

    public ListNode(Pos pos) {
        super(NodeType.list, pos);
        this.list = new LinkedList<>();
    }

    @Override
    public void accept(NodeVisitor visitor) throws Exception {
        visitor.visitListNode(this);
    }

    public void offer(Node node) {
        list.offer(node);
    }

    @Override
    public Iterator<Node> iterator() {
        return list.iterator();
    }

    public int size() {
        return list.size();
    }

    @Override
    public String toString() {
        return "{" + getType() + ":" + list + "}";
    }

}
