package im.hujh.formula.opt;

import java.util.LinkedList;
import java.util.Map;

/**
 * @author hujh
 */
public final class Comma extends Operator {

	public Comma() {
		super(",", false, Operator.PRECEDENCE_LOWEST, Associative.left);
	}

	@Override
	public void evaluate(LinkedList<Object> stack, Map<String, ?> variables) {
	}
}