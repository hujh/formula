package im.hujh.formula.func;

import im.hujh.formula.EvaluateException;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author hujh
 */
public interface Func {

	public BigDecimal apply(String name, List<BigDecimal> args) throws EvaluateException;
}
