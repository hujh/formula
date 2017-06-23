/*
 */
package im.hujh.formula.func;

import im.hujh.formula.EvaluateException;
import java.math.BigDecimal;

import java.util.List;

/**
 * @author hujh
 */
public class MaxFunc implements Func {

	@Override
	public BigDecimal apply(String func, List<BigDecimal> args) throws EvaluateException {
		FuncAsserts.expectArgcAtLeast(func, 1, args.size());

		BigDecimal ret = args.get(0);
		for (int i = 1; i < args.size(); i++) {
			BigDecimal d = args.get(i);
			int cmp = ret.compareTo(d);
			if (cmp < 0) {
				ret = d;
			}
		}

		return ret;
	}

}
