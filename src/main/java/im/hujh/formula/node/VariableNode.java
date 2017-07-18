package im.hujh.formula.node;

import im.hujh.formula.Pos;

/**
 * @author hujh
 */
public class VariableNode extends Node {

	private final String name;

	public VariableNode(Pos pos, String name) {
		super(NodeType.variable, pos);
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Override
	public void accept(NodeVisitor visitor) throws Exception {
		visitor.visitVariableNode(this);
	}

	@Override
	public String toString() {
		return "{" + getType() + ":" + name + "}";
	}

}
