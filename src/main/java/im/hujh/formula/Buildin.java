package im.hujh.formula;

import im.hujh.formula.func.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author hujh
 */
public class Buildin {

    public static final Func MIN = new MinFunc();
    public static final Func MAX = new MaxFunc();
    public static final Func AVG = new AvgFunc();
    public static final Func SUM = new SumFunc();
    public static final Func FLOOR = new FloorFunc();
    public static final Func ROUND = new RoundFunc();
    public static final Func ABS = new AbsFunc();
    public static final Func POW = new PowFunc();
    public static final Func SIGNUM = new SignumFunc();
    public static final Func RANGE = new RangeFunc();
    public static final Func STEP = new StepFunc();
    public static final Func IF = new IfFunc();

    private static final Map<String, Object> standard = new HashMap<String, Object>();

    static {
        standard.put("min", MIN);
        standard.put("max", MAX);
        standard.put("avg", AVG);
        standard.put("sum", SUM);
        standard.put("floor", FLOOR);
        standard.put("round", ROUND);
        standard.put("abs", ABS);
        standard.put("pow", POW);
        standard.put("signum", SIGNUM);
        standard.put("range", RANGE);
        standard.put("step", STEP);
        standard.put("if", IF);
    }

    public static Map<String, Object> standard() {
        Map<String, Object> vars = new HashMap<>();
        vars.putAll(standard);
        return vars;
    }
}
