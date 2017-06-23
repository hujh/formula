package im.hujh.formula;

import im.hujh.formula.node.FuncNode;
import im.hujh.formula.node.ListNode;
import im.hujh.formula.node.Node;
import static im.hujh.formula.node.NodeType.func;
import static im.hujh.formula.node.NodeType.operator;
import static im.hujh.formula.node.NodeType.scalar;
import static im.hujh.formula.node.NodeType.variable;
import im.hujh.formula.node.OperatorNode;
import im.hujh.formula.node.ScalarNode;
import im.hujh.formula.node.VariableNode;
import im.hujh.formula.opt.Operator;
import java.math.BigDecimal;
import java.util.Collections;

import java.util.LinkedList;
import java.util.Map;

/**
 * @author hujh
 */
public class Formula {

    private final String formula;
    private final ListNode root;

    public Formula(String formula) throws ParseException {
        if (formula == null) {
            throw new IllegalArgumentException();
        }

        Parser parser = new Parser(formula);

        this.formula = formula;
        this.root = parser.parse();
    }

    private Formula(String source, ListNode root) {
        this.formula = source;
        this.root = root;
    }

    public BigDecimal evaluate(Map<String, ?> variables) throws EvaluateException {
        LinkedList<Object> stack = new LinkedList<Object>();
        for (;;) {
            Node node = root.next();
            if (node == null) {
                break;
            }
            switch (node.getType()) {
                case scalar:
                    ScalarNode sn = (ScalarNode) node;
                    BigDecimal scalar = sn.getScalar();
                    stack.push(scalar);
                    break;
                case variable:
                    VariableNode vn = (VariableNode) node;
                    Object variable = variables.get(vn.getName());
                    if (variable == null) {
                        throw new EvaluateException("variable \"" + vn.getName() + "\" is undefined in " + vn.getPos().print(formula));
                    } else if (!(variable instanceof BigDecimal)) {
                        throw new EvaluateException("variable \"" + vn.getName() + "\" is unexpected type: expect \"BigDecimal\" but \"" + variable.getClass() + "\"" + " in " + vn.getPos().print(formula));
                    }
                    stack.push(variable);
                    break;
                case func:
                    FuncNode fn = (FuncNode) node;
                    stack.push(fn.getFunc());
                    break;
                case operator:
                    OperatorNode opn = (OperatorNode) node;
                    try {
                        Operator op = opn.getOperator();
                        op.evaluate(stack, variables);
                    } catch (EvaluateException e) {
                        throw new EvaluateException(e.getMessage() + " in " + opn.getPos().print(formula), e);
                    }
                    break;
            }
        }

        if (stack.size() != 1) {
            throw new EvaluateException("stack error");
        }
        Object frame = stack.pop();
        if (frame instanceof BigDecimal) {
            return (BigDecimal) frame;
        } else {
            throw new EvaluateException("stack error");
        }
    }

    public static BigDecimal evaluate(String formula) throws EvaluateException, ParseException {
        return evaluate(formula, Collections.emptyMap());
    }

    public static BigDecimal evaluate(String formula, Map<String, Object> variables) throws EvaluateException, ParseException {
        return compile(formula).evaluate(variables);
    }

    public static Formula compile(String formula) throws ParseException {
        return new Formula(formula);
    }

    public static void main(String[] args) throws ParseException, EvaluateException {

		String input = "(1 + (-2 * 3 + (4 * (5 + 6))*7))";
//		String input = "1 + (4 * (5 + 6))*7";
//		String input = "0.5 * (-max(10, 5))";
//		String input = "1 + amount + max(4, min(5,6))*7";
//		String input = "steps(amt, 10, 1, 20, 2, 40, 3, 80, 4, 5)";
//        String input = "3 + max(1, 2) * 5";
//        String input = "2 / (amt - amt + 5)";

        Map<String, Object> vars = Buildin.standard();
        vars.put("amt", new BigDecimal("1000.0000000000"));

        BigDecimal d = Formula.evaluate(input, vars);

        System.out.println(d);
//		System.out.println(2.1 + (- (1 + 2)));
        System.out.println(1 + 100 + Math.max(4, Math.min(5, 6)) * 7);
    }

}
