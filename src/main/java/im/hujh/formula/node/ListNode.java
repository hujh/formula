package im.hujh.formula.node;

import im.hujh.formula.Pos;

import java.util.LinkedList;

/**
 * @author hujh
 */
public class ListNode extends Node {

	private final LinkedList<Node> list;

	public ListNode(Pos pos) {
		super(NodeType.list, pos);
		this.list = new LinkedList<Node>();
	}

	@Override
	public void accept(NodeVisitor visitor) {
		visitor.visitListNode(this);
	}

	public void offer(Node node) {
		list.offer(node);
	}

	public Node next() {
		return list.poll();
	}

	public int size() {
		return list.size();
	}

	@Override
	public String toString() {
		return "{" + getType() + ":" + list + "}";
	}

}
