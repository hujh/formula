package im.hujh.formula.func;

import im.hujh.formula.EvaluateException;
import java.math.BigDecimal;

/**
 * @author hujh
 */
public class FuncAsserts {

	public static void unexpectedArgc(String func) throws EvaluateException {
		throw new EvaluateException(func + "(): function argument count error");
	}

	public static void expectArgc(String func, int expect, int count) throws EvaluateException {
		if (count != expect) {
			throw new EvaluateException(func + "(): function argument count error, expect " + expect + " but " + count);
		}
	}

	public static void expectArgcAtLeast(String func, int least, int count) throws EvaluateException {
		if (count < least) {
			throw new EvaluateException(func + "(): not enough arguments, expect at least " + least + " but " + count);
		}
	}

	public static void argNotNull(String func, int index, BigDecimal arg) throws EvaluateException {
		if (arg == null) {
			throw new EvaluateException(func + "(): the " + (index + 1) + "th argument is null");
		}
	}
}
