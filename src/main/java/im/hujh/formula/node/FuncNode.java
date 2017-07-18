package im.hujh.formula.node;

import im.hujh.formula.Pos;

/**
 * @author hujh
 */
public class FuncNode extends Node {

	private final String name;

	public FuncNode(Pos pos, String func) {
		super(NodeType.func, pos);
		this.name = func;
	}

	@Override
	public void accept(NodeVisitor visitor) throws Exception {
		visitor.visitFuncNode(this);
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return "{" + getType() + ":" + name + "}";
	}
}
