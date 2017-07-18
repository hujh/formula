package im.hujh.formula.opt;

import im.hujh.formula.EvaluateException;
import im.hujh.formula.Options;
import java.math.BigDecimal;

import java.util.LinkedList;
import java.util.Map;

/**
 * @author hujh
 */
public class Not extends Operator {
	public Not() {
		super("!", true, 2, Associative.right);
	}

	@Override
	public void evaluate(LinkedList<Object> stack, Map<String, ?> variables, Options options) throws EvaluateException {
		if (stack.size() < 1) {
			throw new EvaluateException("stack error for the \"" + symbol + "\" operator: not enough operands");
		}

		BigDecimal d = pop(stack, BigDecimal.class);
		if (d.equals(BigDecimal.ZERO)) {
			stack.push(BigDecimal.ONE);
		} else {
			stack.push(BigDecimal.ZERO);
		}
	}
}
