package im.hujh.formula.opt;

import im.hujh.formula.EvaluateException;
import im.hujh.formula.Options;

import java.util.LinkedList;
import java.util.Map;

/**
 * @author hujh
 */
public abstract class Operator {

	public static final int PRECEDENCE_HIGHEST = 1;
	public static final int PRECEDENCE_LOWEST = 15;

	protected final String symbol;
	protected final boolean unary;
	protected final int precedence;
	protected final Associative associative;

	protected Operator(String symbol, boolean unary, int precedence, Associative associative) {
		this.symbol = symbol;
		this.unary = unary;
		this.precedence = precedence;
		this.associative = associative;
	}

	public final String getSymbol() {
		return symbol;
	}

	public final boolean isUnary() {
		return unary;
	}

	public final int getPrecedence() {
		return precedence;
	}

	public final Associative getAssociative() {
		return associative;
	}

	public final int compareTo(Operator o) {
		if (this.precedence == o.getPrecedence()) {
			switch (this.associative) {
				case left:
					return -1;
				default:
					return 1;
			}
		} else {
			return o.getPrecedence() - this.precedence;
		}
	}

	@Override
	public String toString() {
		return symbol;
	}

	public abstract void evaluate(LinkedList<Object> stack, Map<String, ?> variables, Options options) throws EvaluateException;

    @SuppressWarnings("unchecked")
	protected <T> T pop(LinkedList<Object> stack, Class<T> cls) throws EvaluateException {
		Object frame = null;
		try {
			frame = stack.pop();
			return (T) frame;
		} catch (ClassCastException e) {
			if (null != frame)
				throw new EvaluateException("stack frame error for the \"" + symbol + "\" operator, expect " + cls + " but " + frame.getClass());
			else
				throw new EvaluateException("stack frame error for the \"" + symbol + "\" operator, expect " + cls + " but NULL pointer.");
		}
	}

}
