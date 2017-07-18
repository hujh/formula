package im.hujh.formula.opt;

import im.hujh.formula.EvaluateException;
import im.hujh.formula.Options;
import java.math.BigDecimal;

import java.util.LinkedList;
import java.util.Map;

/**
 * @author hujh
 */
public final class Add extends Operator {

	public Add() {
		super("+", false, 4, Associative.left);
	}

	@Override
	public void evaluate(LinkedList<Object> stack, Map<String, ?> variables, Options options) throws EvaluateException {
		if (stack.size() < 2) {
			throw new EvaluateException("stack error for the \"" + symbol + "\" operator: not enough operands");
		}

		BigDecimal d0 = pop(stack, BigDecimal.class);
		BigDecimal d1 = pop(stack, BigDecimal.class);
		BigDecimal d = d1.add(d0);
		stack.push(d);
	}

}
