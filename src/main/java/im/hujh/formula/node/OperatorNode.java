package im.hujh.formula.node;

import im.hujh.formula.Pos;
import im.hujh.formula.opt.Operator;

/**
 * @author hujh
 */
public class OperatorNode extends Node {

    private final Operator operator;

    public OperatorNode(Pos pos, Operator operator) {
        super(NodeType.operator, pos);
        this.operator = operator;
    }

    @Override
    public void accept(NodeVisitor visitor) throws Exception {
        visitor.visitOperatorNode(this);
    }

    public Operator getOperator() {
        return operator;
    }

    @Override
    public String toString() {
        return "{" + getType() + ":" + operator + "}";
    }
}
