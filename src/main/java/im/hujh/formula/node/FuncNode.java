package im.hujh.formula.node;

import im.hujh.formula.Pos;

/**
 * @author hujh
 */
public class FuncNode extends Node {

	private final String func;

	public FuncNode(Pos pos, String func) {
		super(NodeType.func, pos);
		this.func = func;
	}

	@Override
	public void accept(NodeVisitor visitor) {
		visitor.visitFuncNode(this);
	}

	public String getFunc() {
		return func;
	}

	@Override
	public String toString() {
		return "{" + getType() + ":" + func + "}";
	}
}
