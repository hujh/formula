package im.hujh.formula.node;

/**
 *
 * @author hujh
 */
public interface NodeVisitor {

	public void visitListNode(ListNode node);
	public void visitScalarNode(ScalarNode node);
	public void visitVariableNode(VariableNode node);
	public void visitFuncNode(FuncNode node);
	public void visitOperatorNode(OperatorNode node);
}
