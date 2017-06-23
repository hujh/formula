package im.hujh.formula.func;

import im.hujh.formula.EvaluateException;
import java.math.BigDecimal;

import java.util.List;

/**
 * @author hujh
 */
public class AvgFunc implements Func {

	@Override
	public BigDecimal apply(String func, List<BigDecimal> args) throws EvaluateException {
		FuncAsserts.expectArgcAtLeast(func, 1, args.size());

		BigDecimal sum = BigDecimal.ZERO;
		for (int i = 0; i < args.size(); i++) {
			BigDecimal d = args.get(i);
			sum = sum.add(d);
		}

		return sum.divide(BigDecimal.valueOf(args.size()));
	}
}
