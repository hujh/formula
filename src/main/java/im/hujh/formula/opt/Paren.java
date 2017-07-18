package im.hujh.formula.opt;

import im.hujh.formula.Options;
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
	public void evaluate(LinkedList<Object> stack, Map<String, ?> variables, Options options) {
	}

}