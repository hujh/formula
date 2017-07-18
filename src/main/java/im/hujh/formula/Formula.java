package im.hujh.formula;

import im.hujh.formula.node.FuncNode;
import im.hujh.formula.node.ListNode;
import im.hujh.formula.node.Node;
import im.hujh.formula.node.NodeVisitor;
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
    private final ListNode list;

    public Formula(String formula) throws ParseException {
        if (formula == null) {
            throw new IllegalArgumentException();
        }

        Parser parser = new Parser(formula);

        this.formula = formula;
        this.list = parser.parse();
    }

    private Formula(String formula, ListNode list) {
        this.formula = formula;
        this.list = list;
    }

    public BigDecimal evaluate(Map<String, ?> variables) throws EvaluateException {
        return evaluate(variables, Options.DEFAULT);
    }

    public BigDecimal evaluate(Map<String, ?> variables, Options options) throws EvaluateException {
        if (options == null) {
            options = Options.DEFAULT;
        }

        Evaluator evaluator = new Evaluator(list, variables, options);

        return evaluator.evaluate();
    }

    public static BigDecimal evaluate(String formula) throws EvaluateException, ParseException {
        return evaluate(formula, Collections.emptyMap());
    }

    public static BigDecimal evaluate(String formula, Map<String, Object> variables) throws EvaluateException, ParseException {
        return compile(formula).evaluate(variables, Options.DEFAULT);
    }

    public static BigDecimal evaluate(String formula, Map<String, Object> variables, Options options) throws EvaluateException, ParseException {
        return compile(formula).evaluate(variables, options);
    }

    public static Formula compile(String formula) throws ParseException {
        return new Formula(formula);
    }

    private class Evaluator implements NodeVisitor {

        private final ListNode list;
        private final Map<String, ?> variables;
        private final Options options;
        private final LinkedList<Object> stack;

        public Evaluator(ListNode list, Map<String, ?> variables, Options options) {
            this.list = list;
            this.variables = variables;
            this.options = options;
            this.stack = new LinkedList<>();
        }

        public BigDecimal evaluate() throws EvaluateException {
            try {
                list.accept(this);

                if (stack.size() != 1) {
                    throw new EvaluateException("stack error");
                }

                Object frame = stack.pop();
                if (frame instanceof BigDecimal) {
                    BigDecimal result = (BigDecimal) frame;

                    if (options.getScale() > 0 && result.scale() != options.getScale()) {
                        result = result.setScale(options.getScale(), options.getRoundingMode());
                    }

                    return result;
                } else {
                    throw new EvaluateException("stack error");
                }
            } catch (EvaluateException e) {
                throw e;
            } catch (Exception e) {
                throw new EvaluateException("evaluate error", e);
            }
        }

        @Override
        public void visitListNode(ListNode node) throws Exception {
            for (Node item : node) {
                item.accept(this);
            }
        }

        @Override
        public void visitScalarNode(ScalarNode node) throws Exception {
            BigDecimal scalar = node.getScalar();
            stack.push(scalar);
        }

        @Override
        public void visitVariableNode(VariableNode node) throws Exception {
            Object variable = variables.get(node.getName());
            if (variable == null) {
                throw new EvaluateException("variable \"" + node.getName() + "\" is undefined in " + node.getPos().print(formula));
            } else if (!(variable instanceof BigDecimal)) {
                throw new EvaluateException("variable \"" + node.getName() + "\" is unexpected type: expect \"BigDecimal\" but \"" + variable.getClass() + "\"" + " in " + node.getPos().print(formula));
            }
            stack.push(variable);
        }

        @Override
        public void visitFuncNode(FuncNode node) throws Exception {
            stack.push(node.getName());
        }

        @Override
        public void visitOperatorNode(OperatorNode node) throws Exception {
            try {
                Operator op = node.getOperator();
                op.evaluate(stack, variables, options);
            } catch (EvaluateException e) {
                throw new EvaluateException(e.getMessage() + " in " + node.getPos().print(formula), e);
            }
        }

    }

}
