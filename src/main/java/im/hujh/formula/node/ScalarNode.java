package im.hujh.formula.node;

import im.hujh.formula.Pos;
import java.math.BigDecimal;

/**
 * @author hujh
 */
public class ScalarNode extends Node {

	private final BigDecimal scalar;

	public ScalarNode(Pos pos, BigDecimal scalar) {
		super(NodeType.scalar, pos);
		this.scalar = scalar;
	}

	public ScalarNode(Pos pos, String scalar) {
		this(pos, new BigDecimal(scalar));
	}

	@Override
	public void accept(NodeVisitor visitor) throws Exception {
		visitor.visitScalarNode(this);
	}

	public BigDecimal getScalar() {
		return scalar;
	}

	@Override
	public String toString() {
		return "{" + getType() + ":" + scalar + "}";
	}

}
