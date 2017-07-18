/*
 */
package im.hujh.formula;

import im.hujh.formula.node.ListNode;
import im.hujh.formula.node.Node;
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
public class ParserTest {

    public ParserTest() {
    }

    @Test
    public void test() throws Exception {
        //		String input = "a + b * c + (d * e + f)*g";
//		String input = "1-2-3";
//		String input = "4^3^2";
//		String input = "sin(a, b, c + d)";
//		String input = "2 * (0-sin(3))";
//		String input = "x + sin(1)";
//		String input = "- 1 + 2";
//		String input = "0.5 * (-max(10, 5))";
//		String input = "1 + max(4 * min(5 + 6))*7";
//		String input = "3 + f(1, 2) * 5";
//		String input = "1 - (!)";
//        String input = "a + b * c(2, 3) + (d * e + f) * g >= 5";
//        String input = "1 + 2 * 3";
        String input = "+1-2";

        Parser parser = new Parser(input);

        ListNode list = parser.parse();

        for (Node node : list) {
            if (node == null) {
                break;
            } else {
                System.out.println(node);
            }
        }
    }
}
