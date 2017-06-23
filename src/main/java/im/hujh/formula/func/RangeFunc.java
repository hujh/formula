package im.hujh.formula.func;

import im.hujh.formula.EvaluateException;
import java.math.BigDecimal;

import java.util.List;

/**
 * @author hujh
 */
public class RangeFunc implements Func {

	@Override
	public BigDecimal apply(String func, List<BigDecimal> args) throws EvaluateException {
		if (args.size() != 2 && args.size() != 3) {
			FuncAsserts.unexpectedArgc(func);
		}

		BigDecimal n = args.get(0);
		BigDecimal f = args.get(1);

		if (n.compareTo(f) <= 0) {
			return BigDecimal.ZERO;
		}

		if (args.size() < 3) {
			return n.subtract(f);
		}

		BigDecimal t = args.get(2);
		if (n.compareTo(t) < 0) {
			return n.subtract(f);
		} else {
			return t.subtract(f);
		}
	}
}
