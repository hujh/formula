package im.hujh.formula.opt;

import im.hujh.formula.EvaluateException;
import im.hujh.formula.Options;
import im.hujh.formula.func.Func;
import java.math.BigDecimal;

import java.util.LinkedList;
import java.util.Map;

/**
 * @author hujh
 */
public final class Call extends Operator {

    public Call() {
        super("()", false, Operator.PRECEDENCE_HIGHEST, Associative.left);
    }

    @Override
    public void evaluate(LinkedList<Object> stack, Map<String, ?> variables, Options options) throws EvaluateException {
        String name;
        LinkedList<BigDecimal> args = new LinkedList<>();

        for (;;) {
            Object obj = stack.pop();
            if (obj instanceof BigDecimal) {
                BigDecimal d = (BigDecimal) obj;
                args.addFirst(d);
            } else if (obj instanceof String) {
                name = (String) obj;
                break;
            } else {
                throw new EvaluateException("stack error");
            }
        }

        if (name == null) {
            throw new EvaluateException("stack error for the \" + symbol + \" operator: expect func name but not found");
        }

        Func func = (Func) variables.get(name);
        if (func == null) {
            throw new EvaluateException("func \"" + name + "\" is undefined");
        }

        BigDecimal ret = func.apply(name, args);
        stack.push(ret);
    }

}
