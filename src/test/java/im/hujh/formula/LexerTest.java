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
public class LexerTest {

    public LexerTest() {
    }

    @Test
    public void test() throws Exception {
//        String input = "a + b * c + (d * e + f) * g >= 5";
//		String input = "amt * 0.5 11";
        String input = "+1-2";

        Lexer lexer = new Lexer(input);

        for (;;) {
            Token t = lexer.next();
            System.out.println(t+","+t.getType());
//            System.out.println(t.getPos());
            if (t.getType() == TokenType.eof) {
                return;
            }
        }
    }
}
