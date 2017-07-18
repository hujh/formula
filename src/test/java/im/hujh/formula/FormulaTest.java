/*
 */
package im.hujh.formula;

import java.math.BigDecimal;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author hujh
 */
public class FormulaTest {

    public FormulaTest() {
    }

    @Test
    public void test() throws Exception {
        //		String input = "(1 + (-2 * 3.1 + (4.1 * (5 + 6))*7))";
//		String input = "1 + (4 * (5 + 6))*7";
//		String input = "0.5 * (-max(10, 5))";
//		String input = "1 + amount + max(4, min(5,6))*7";
//		String input = "steps(amt, 10, 1, 20, 2, 40, 3, 80, 4, 5)";
//        String input = "3 + max(1, 2) * 5";
//        String input = "2 * amt / (amt + amt + 5)";
        String input = "+ 1 - 2";

        Map<String, Object> vars = Buildin.standard();
        vars.put("amt", new BigDecimal("1000.0000000000"));

        Options options = new Options();
//        options.setScale(3);

        BigDecimal d = Formula.evaluate(input, vars, options);

        System.out.println(d.toPlainString());
    }
}
