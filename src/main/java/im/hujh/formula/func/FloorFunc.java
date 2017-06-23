package im.hujh.formula.func;

import im.hujh.formula.EvaluateException;
import java.math.BigDecimal;
import java.math.RoundingMode;

import java.util.List;

/**
 * @author hujh
 */
public class FloorFunc implements Func {


	@Override
	public BigDecimal apply(String func, List<BigDecimal> args) throws EvaluateException {
		FuncAsserts.expectArgc(func, 1, args.size());
		BigDecimal d = args.get(0);
        return d.setScale(0, RoundingMode.FLOOR);
	}
}
