package im.hujh.formula.opt;

import im.hujh.formula.EvaluateException;
import im.hujh.formula.Options;
import java.math.BigDecimal;

import java.util.LinkedList;
import java.util.Map;

/**
 * @author hujh
 */
public class Ge extends Operator {

	public Ge() {
		super(">=", false, 6, Associative.left);
	}

	@Override
	public void evaluate(LinkedList<Object> stack, Map<String, ?> variables, Options options) throws EvaluateException {
		if (stack.size() < 2) {
			throw new EvaluateException("stack error for the \"" + symbol + "\" operator: not enough operands");
		}

		BigDecimal d0 = pop(stack, BigDecimal.class);
		BigDecimal d1 = pop(stack, BigDecimal.class);

		int cmp = d1.compareTo(d0);
		if (cmp >= 0) {
			stack.push(BigDecimal.ONE);
		} else {
			stack.push(BigDecimal.ZERO);
		}
	}
}
