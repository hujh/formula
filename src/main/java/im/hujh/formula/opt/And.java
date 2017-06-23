package im.hujh.formula.opt;

import im.hujh.formula.EvaluateException;
import java.math.BigDecimal;

import java.util.LinkedList;
import java.util.Map;

/**
 * @author hujh
 */
public class And extends Operator {

    public And() {
        super("&&", false, 11, Associative.left);
    }

    @Override
    public void evaluate(LinkedList<Object> stack, Map<String, ?> variables) throws EvaluateException {
        if (stack.size() < 2) {
            throw new EvaluateException("stack error for the \"" + symbol + "\" operator: not enough operands");
        }

        BigDecimal d0 = pop(stack, BigDecimal.class);
        BigDecimal d1 = pop(stack, BigDecimal.class);

        if (d0.compareTo(BigDecimal.ZERO) != 0
                && d1.compareTo(BigDecimal.ZERO) != 0) {
            stack.push(BigDecimal.ONE);
        } else {
            stack.push(BigDecimal.ZERO);
        }
    }
}
