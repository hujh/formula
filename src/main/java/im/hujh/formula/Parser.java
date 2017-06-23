package im.hujh.formula;

import im.hujh.formula.node.FuncNode;
import im.hujh.formula.node.ListNode;
import im.hujh.formula.node.Node;
import im.hujh.formula.node.OperatorNode;
import im.hujh.formula.node.ScalarNode;
import im.hujh.formula.node.VariableNode;
import im.hujh.formula.opt.Operator;
import im.hujh.formula.opt.Operators;

import java.util.LinkedList;

/**
 * expr ::= factor | factor operator expr
 * factor ::= number | variable | unaryExpr | funcExpr | leftParen expr rightParen
 * unaryExpr ::= unaryOperator expr
 * funcExpr ::= identifier leftParen arguments rightParen
 * arguments ::= ??| expr { comma arguments }
 * variable ::= identifier
 * identifier ::= alpha { alpha | digit | "_" }
 * number ::= [ sign ] { digit } [ dot { digit } ]
 * digit ::= "0" | "1" | "2" | "3" | "4" | "5" | "6" | "7" | "8" | "9"
 * alpha ::= "a" ... "z" | "A" ... "Z"
 * operator ::= "+" | "-" | "*" | "/"
 * unaryOperator ::= "!"
 * not ::= "!"
 * sign ::= "+" | "-"
 * dot ::= "."
 * comma ::= ","
 * leftParen ::= "("
 * rightParen ::= ")"
 *
 * @author hujh
 */
public class Parser {

    private final String input;
    private final ListNode output;
    private final Lexer lexer;
    private final LinkedList<Operator> stack;

    public Parser(String input) {
        this.input = input;
        this.output = new ListNode(Pos.of(0, 1, 1));
        this.lexer = new Lexer(input);
        this.stack = new LinkedList<Operator>();
    }

    public ListNode parse() throws ParseException {
        ignoreBlanks();
        parseExpression();

        ignoreBlanks();
        Token token = lexer.next();
        if (token.getType() != TokenType.eof) {
            unexpected(token);
        }

        processEOF(token.getPos());
        return output;
    }

    /**
     * expr ::= factor | factor operator expr
     *
     * @throws ParseException
     */
    private void parseExpression() throws ParseException {
        Token token = lexer.peek();

        switch (token.getType()) {
            case number:
            case identifier:
            case operator:
            case unaryOperator:
            case leftParen:
                ignoreBlanks();
                parseFactor();
                if (lookAhead(TokenType.operator)) {
                    parseOperator();
                    ignoreBlanks();
                    parseExpression();
                }
                return;
            default:
                unexpected(token);
        }
    }

    /**
     * factor ::= number | variable | unaryExpr | funcExpr | leftParen expr rightParen
     *
     * @throws ParseException
     */
    private void parseFactor() throws ParseException {
        ignoreBlanks();
        Token token = lexer.peek();

        switch (token.getType()) {
            case number:
                parseNumber();
                return;
            case identifier:
                if (lookAhead(1, TokenType.leftParen)) {
                    parseFunction();
                } else {
                    parseVariable();
                }
                return;
            case operator:
                parseSign();
                parseFactor();
                return;
            case unaryOperator:
                parseUnaryOperator();
                ignoreBlanks();
                parseExpression();
                return;
            case leftParen:
                parseLeftParen();
                unexpect(TokenType.rightParen);
                ignoreBlanks();
                parseExpression();
                ignoreBlanks();
                parseRightParen();
                return;
            default:
                unexpected(token);
        }
    }

    /**
     * number ::= [ sign ] { digit } [ dot { digit } ]
     *
     * @throws ParseException
     */
    private void parseNumber() throws ParseException {
        Token token = expect(TokenType.number);
        output.offer(new ScalarNode(token.getPos(), token.getValue()));
    }

    /**
     * sign ::= "+" | "-"
     *
     * @throws ParseException
     */
    private void parseSign() throws ParseException {
        Token token = expect(TokenType.operator);
        Operator op = Operators.from(token.getValue());
        if (isSign(op)) {
            output.offer(new ScalarNode(token.getPos(), "0"));
            processOperator(op, token.getPos());
        } else {
            unexpected(token);
        }
    }

    /**
     * variable ::= identifier
     *
     * @throws ParseException
     */
    private void parseVariable() throws ParseException {
        Token token = expect(TokenType.identifier);
        output.offer(new VariableNode(token.getPos(), token.getValue()));
    }

    /**
     * funcExpr ::= identifier leftParen arguments rightParen
     *
     * @throws ParseException
     */
    private void parseFunction() throws ParseException {
        Token token = expect(TokenType.identifier);
        output.offer(new FuncNode(token.getPos(), token.getValue()));
        stack.push(Operators.call);

        ignoreBlanks();
        parseLeftParen();
        ignoreBlanks();
        if (!lookAhead(TokenType.rightParen)) {
            parseArguments();
        }
        ignoreBlanks();
        parseRightParen();
    }

    /**
     * arguments ::= ??| expr { comma arguments }
     *
     * @throws ParseException
     */
    private void parseArguments() throws ParseException {
        for (int arg = 0;; arg++) {
            if (arg > 0) {
                ignoreBlanks();
                if (lookAhead(TokenType.comma)) {
                    parseComma();
                } else {
                    return;
                }
            }

            ignoreBlanks();
            parseExpression();
        }
    }

    /**
     * operator ::= "+" | "-" | "*" | "/"
     *
     * @throws ParseException
     */
    private void parseOperator() throws ParseException {
        Token token = expect(TokenType.operator);
        Operator op = Operators.from(token.getValue());
        processOperator(op, token.getPos());
    }

    /**
     * unaryOperator ::= "!"
     *
     * @throws ParseException
     */
    private void parseUnaryOperator() throws ParseException {
        Token token = expect(TokenType.unaryOperator);
        Operator op = Operators.from(token.getValue());
        processOperator(op, token.getPos());
    }

    private void parseComma() throws ParseException {
        Token token = expect(TokenType.comma);
        Operator op = Operators.from(token.getValue());
        processOperator(op, token.getPos());
    }

    private void parseLeftParen() throws ParseException {
        Token token = expect(TokenType.leftParen);
        Operator op = Operators.from(token.getValue());
        processLeftParen(op, token.getPos());
    }

    private void parseRightParen() throws ParseException {
        Token token = expect(TokenType.rightParen);
        Operator op = Operators.from(token.getValue());
        processRightParen(op, token.getPos());
    }

    private void processLeftParen(Operator op, Pos pos) {
        stack.push(op);
    }

    private void processRightParen(Operator op, Pos pos) {
        for (;;) {
            Operator top = stack.pop();
            if (top == Operators.leftParen) {
                break;
            } else {
                output.offer(new OperatorNode(pos, top));
            }
        }
    }

    private void processOperator(Operator op, Pos pos) {
        while (!stack.isEmpty()) {
            Operator top = stack.peek();
            if (op.compareTo(top) > 0 || top == Operators.leftParen) {
                break;
            } else {
                output.offer(new OperatorNode(pos, top));
                stack.pop();
            }
        }
        stack.push(op);
    }

    private void processEOF(Pos pos) {
        while (!stack.isEmpty()) {
            output.offer(new OperatorNode(pos, stack.pop()));
        }
    }

    private boolean isSign(Operator op) {
        return op == Operators.add || op == Operators.sub;
    }

    private boolean lookAhead(TokenType... types) throws ParseException {
        return lookAhead(0, types);
    }

    private boolean lookAhead(int offset, TokenType... types) throws ParseException {
        for (int i = 0, j = 0; j < types.length; i++) {
            Token token = lexer.peek(offset + i);
            if (token == null) {
                return false;
            }
            if (token.getType() == TokenType.blank) {
                continue;
            }
            if (token.getType() == types[j]) {
                j++;
                continue;
            } else {
                return false;
            }
        }
        return true;
    }

    private Token expect(TokenType type) throws ParseException {
        ignoreBlanks();
        Token token = lexer.peek();
        if (token.getType() != type) {
            unexpected(token);
        }
        return lexer.next();
    }

    private void unexpect(TokenType type) throws ParseException {
        ignoreBlanks();
        Token token = lexer.peek();
        if (token.getType() == type) {
            unexpected(token);
        }
    }

    private void unexpected(Token token) throws ParseException {
        switch (token.getType()) {
            case eof:
                throw new ParseException("syntax error: unexpected EOF in " + printPosition(token));
            default:
                throw new ParseException("syntax error: unexpected input characters \"" + token.getValue() + "\" in " + printPosition(token));
        }
    }

    private void ignoreBlanks() throws ParseException {
        for (;;) {
            Token token = lexer.peek();
            if (token.getType() == TokenType.blank) {
                lexer.next();
            } else {
                break;
            }
        }
    }

    private String printPosition(Token token) {
        return token.getPos().print(input);
    }

    public static void main(String[] args) throws ParseException {
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
        String input = "a + b * c(2, 3) + (d * e + f) * g >= 5";

        Parser parser = new Parser(input);

        ListNode list = parser.parse();

        for (;;) {
            Node node = list.next();
            if (node == null) {
                break;
            } else {
                System.out.println(node);
            }
        }
    }
}
