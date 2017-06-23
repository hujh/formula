package im.hujh.formula.func;

import im.hujh.formula.EvaluateException;
import java.math.BigDecimal;

import java.util.List;

/**
 * @author hujh
 */
public class PowFunc implements Func {

	@Override
	public BigDecimal apply(String func, List<BigDecimal> args) throws EvaluateException {
		FuncAsserts.expectArgc(func, 2, args.size());
		BigDecimal operand = args.get(0);
		BigDecimal power = args.get(1);
		return operand.pow(power.intValue());
	}
}
