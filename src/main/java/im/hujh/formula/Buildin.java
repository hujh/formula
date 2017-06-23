package im.hujh.formula;

import im.hujh.formula.func.IfFunc;
import im.hujh.formula.func.SumFunc;
import im.hujh.formula.func.AvgFunc;
import im.hujh.formula.func.StepFunc;
import im.hujh.formula.func.AbsFunc;
import im.hujh.formula.func.FloorFunc;
import im.hujh.formula.func.RangeFunc;
import im.hujh.formula.func.RoundFunc;
import im.hujh.formula.func.MaxFunc;
import im.hujh.formula.func.MinFunc;
import im.hujh.formula.func.PowFunc;
import im.hujh.formula.func.SignFunc;

import java.util.HashMap;
import java.util.Map;

/**
 * @author hujh
 */
public class Buildin {

	private static final Map<String, Object> standard = new HashMap<String, Object>();

	static {
		standard.put("min", new MinFunc());
		standard.put("max", new MaxFunc());
		standard.put("avg", new AvgFunc());
		standard.put("sum", new SumFunc());
		standard.put("floor", new FloorFunc());
		standard.put("round", new RoundFunc());
		standard.put("abs", new AbsFunc());
		standard.put("pow", new PowFunc());
		standard.put("sign", new SignFunc());
		standard.put("range", new RangeFunc());
		standard.put("step", new StepFunc());
		standard.put("if", new IfFunc());
	}

	public static Map<String, Object> standard() {
		Map<String, Object> vars = new HashMap<String, Object>();
		vars.putAll(standard);
		return vars;
	}
}
