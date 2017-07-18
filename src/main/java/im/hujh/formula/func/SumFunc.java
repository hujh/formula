package im.hujh.formula.func;

import im.hujh.formula.EvaluateException;
import java.math.BigDecimal;

import java.util.List;

/**
 * @author hujh
 */
public class SumFunc implements Func {

    @Override
    public BigDecimal apply(String func, List<BigDecimal> args) throws EvaluateException {
        FuncAsserts.expectArgcAtLeast(func, 1, args.size());

        BigDecimal ret = BigDecimal.ZERO;
        for (BigDecimal d : args) {
            ret = ret.add(d);
        }

        return ret;
    }
}
