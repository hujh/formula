package im.hujh.formula.node;

/**
 *
 * @author hujh
 */
public interface NodeVisitor {

    public void visitListNode(ListNode node) throws Exception;

    public void visitScalarNode(ScalarNode node) throws Exception;

    public void visitVariableNode(VariableNode node) throws Exception;

    public void visitFuncNode(FuncNode node) throws Exception;

    public void visitOperatorNode(OperatorNode node) throws Exception;
}
