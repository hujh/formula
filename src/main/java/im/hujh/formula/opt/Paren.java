package im.hujh.formula.opt;

import java.util.LinkedList;
import java.util.Map;

/**
 * @author hujh
 */
public final class Paren extends Operator {

	public Paren(String text) {
		super(text, false, Operator.PRECEDENCE_HIGHEST, Associative.left);
	}

	@Override
	public void evaluate(LinkedList<Object> stack, Map<String, ?> variables) {
	}

}