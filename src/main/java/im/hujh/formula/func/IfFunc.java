/*
 */
package im.hujh.formula.func;

import im.hujh.formula.EvaluateException;
import java.math.BigDecimal;

import java.util.List;

/**
 * @author hujh
 */
public class IfFunc implements Func {

	@Override
	public BigDecimal apply(String func, List<BigDecimal> args) throws EvaluateException {
		FuncAsserts.expectArgc(func, 3, args.size());
		BigDecimal d0 = args.get(0);
		BigDecimal d1 = args.get(1);
		BigDecimal d2 = args.get(2);
		if (d0 != null && d0.compareTo(BigDecimal.ZERO) != 0) {
			return d1;
		} else {
			return d2;
		}
	}

}
