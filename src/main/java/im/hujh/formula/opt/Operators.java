package im.hujh.formula.opt;

import java.util.HashMap;
import java.util.Map;

/**
 * @author hujh
 */
public class Operators {

	private static final Map<String, Operator> operators = new HashMap<String, Operator>();

	public static final Operator add = new Add();
	public static final Operator subtract = new Subtract();
	public static final Operator multiply = new Multiply();
	public static final Operator divide = new Divide();
	public static final Operator lt = new Lt();
	public static final Operator le = new Le();
	public static final Operator gt = new Gt();
	public static final Operator ge = new Ge();
	public static final Operator eq = new Eq();
	public static final Operator neq = new Neq();
	public static final Operator and = new And();
	public static final Operator or = new Or();
	public static final Operator not = new Not();
	public static final Operator leftParen = new Paren("(");
	public static final Operator rightParen = new Paren(")");
	public static final Operator comma = new Comma();
	public static final Operator call = new Call();

	static {
		operators.put(add.getSymbol(), add);
		operators.put(subtract.getSymbol(), subtract);
		operators.put(multiply.getSymbol(), multiply);
		operators.put(divide.getSymbol(), divide);
		operators.put(lt.getSymbol(), lt);
		operators.put(le.getSymbol(), le);
		operators.put(gt.getSymbol(), gt);
		operators.put(ge.getSymbol(), ge);
		operators.put(eq.getSymbol(), eq);
		operators.put(neq.getSymbol(), neq);
		operators.put(and.getSymbol(), and);
		operators.put(or.getSymbol(), or);
		operators.put(not.getSymbol(), not);
		operators.put(leftParen.getSymbol(), leftParen);
		operators.put(rightParen.getSymbol(), rightParen);
		operators.put(comma.getSymbol(), comma);
	}

	public static Operator of(String symbol) {
		Operator op = operators.get(symbol);
		if (op == null) {
			throw new IllegalArgumentException("illegal operator: " + symbol);
		} else {
			return op;
		}
	}

}
