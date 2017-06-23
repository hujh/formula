package im.hujh.formula.func;

import im.hujh.formula.EvaluateException;
import java.math.BigDecimal;

import java.util.List;

/**
 * @author hujh
 */
public class StepFunc implements Func {

	@Override
	public BigDecimal apply(String func, List<BigDecimal> args) throws EvaluateException {
		FuncAsserts.expectArgcAtLeast(func, 1, args.size());
		if (args.size() % 2 == 1) {
			throw new EvaluateException(func + "(): illegal argument count, expect even but odd");
		}

		BigDecimal d = args.get(0);

		if (d.compareTo(BigDecimal.ZERO) == 0) {
			return d;
		}

		BigDecimal p = BigDecimal.ZERO;
		for (int i = 1; i < args.size() - 1; i += 2) {
			BigDecimal r = args.get(i);
			BigDecimal c = args.get(i + 1);

			if (d.compareTo(p) >= 0 && d.compareTo(r) < 0) {
				return c;
			}
		}

		return args.get(args.size() - 1);
	}
}
