package im.hujh.formula.func;

import im.hujh.formula.EvaluateException;
import java.math.BigDecimal;

import java.util.List;

/**
 * @author hujh
 */
public class SignFunc implements Func {

	@Override
	public BigDecimal apply(String func, List<BigDecimal> args) throws EvaluateException {
		FuncAsserts.expectArgc(func, 1, args.size());
		BigDecimal d = args.get(0);
		return BigDecimal.valueOf(d.signum());
	}

}
