package im.hujh.formula.func;

import im.hujh.formula.EvaluateException;
import java.math.BigDecimal;
import java.math.RoundingMode;

import java.util.List;

/**
 * @author hujh
 */
public class RoundFunc implements Func {

    @Override
    public BigDecimal apply(String func, List<BigDecimal> args) throws EvaluateException {
        FuncAsserts.expectArgc(func, 2, args.size());
        BigDecimal operand = args.get(0);
        BigDecimal digits = args.get(1);

        int d = digits.intValue();
        if (d > 0) {
            return operand.setScale(d, RoundingMode.HALF_EVEN);
        } else if (d < 0) {
            BigDecimal x = BigDecimal.valueOf(Math.pow(10, -d));
            return operand.divide(x, 0, RoundingMode.HALF_EVEN).multiply(x).setScale(0, RoundingMode.FLOOR);
        } else {
            return operand.setScale(0, RoundingMode.HALF_EVEN);
        }
    }
}
