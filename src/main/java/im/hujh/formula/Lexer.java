package im.hujh.formula;

import java.io.IOException;
import java.util.LinkedList;

/**
 * @author hujh
 */
public class Lexer {

	private static final int EOF = -1;

	private final String input;
	private Pos pos;
	private int width;
	private int index;
	private int line;
	private int column;
	private boolean eof;
	private LinkedList<Token> peeks;

	public Lexer(String input) {
		this.input = input;
		this.index = 0;
		this.line = 1;
		this.column = 1;
		this.pos = new Pos(index, line, column);
		this.peeks = new LinkedList<Token>();
	}

	public Token peek() throws ParseException {
		if (!peeks.isEmpty()) {
			return peeks.getFirst();
		}

		Token token = scan();
		peeks.offer(token);
		return token;
	}

	public Token peek(int offset) throws ParseException {
		if (peeks.size() > offset) {
			return peeks.get(offset);
		}

		if (eof) {
			return null;
		}

		int n = peeks.size();
		while (n <= offset) {
			n++;
			Token token = scan();
			peeks.offer(token);
			if (token.getType() == TokenType.eof) {
				break;
			}
		}

		if (offset < peeks.size()) {
			return peeks.get(offset);
		} else {
			return null;
		}
	}

	public Token next() throws ParseException {
		if (!peeks.isEmpty()) {
			return peeks.removeFirst();
		}

		return scan();
	}

	private Token scan() throws ParseException {
		int c = peekChar();

		if (isAlpha(c)) {
			return lexIdentifier();
		} else if (isDigit(c)) {
			return lexNumber();
		} else if (isComma(c)) {
			return lexComma();
		} else if (isBlank(c)) {
			return lexBlank();
		} else if (isParen(c)) {
			return lexParen();
		} else if (isEOF(c)) {
			eof = true;
			return emit(TokenType.eof);
		} else {
			Token ot = lexOperator();
			if (ot != null) {
				return ot;
			}
		}

		throw error();
	}

	private Token lexIdentifier() throws ParseException {
		for (; ; ) {
			int c = peekChar();
			if ((width == 0 && isAlphaNumeric(c))
				|| (width > 0 && (isAlphaNumeric(c) || c == '_'))) {
				forward(1);
			} else {
				break;
			}
		}
		if (width > 0) {
			return emit(TokenType.identifier);
		} else {
			throw error();
		}
	}

	private Token lexNumber() throws ParseException {
		boolean sawDot = false;
		for (; ; ) {
			int c = peekChar();

			if (isDigit(c)) {
				forward(1);
			} else if (c == '.') {
				if (sawDot) {
					throw error();
				}
				forward(1);
				sawDot = true;
				if (!isDigit(peekChar())) {
					throw error();
				}
			} else {
				break;
			}
		}

		if (width > 0) {
			return emit(TokenType.number);
		} else {
			throw error();
		}
	}

	private Token lexOperator() throws ParseException {
		int c = peekChar();
		switch (c) {
			case '+':
			case '-':
			case '*':
			case '/':
				forward(1);
				return emit(TokenType.operator);
			case '>':
			case '<':
				if (peekChar(1) == '=') {
					forward(2);
					return emit(TokenType.operator);
				} else {
					forward(1);
					return emit(TokenType.operator);
				}
			case '=':
				if (peekChar(1) == '=') {
					forward(2);
					return emit(TokenType.operator);
				}
				return null;
			case '&':
				if (peekChar(1) == '&') {
					forward(2);
					return emit(TokenType.operator);
				}
				return null;
			case '|':
				if (peekChar(1) == '&') {
					forward(2);
					return emit(TokenType.operator);
				}
				return null;
			case '!':
				if (peekChar(1) == '=') {
					forward(2);
					return emit(TokenType.operator);
				} else {
					forward(1);
					return emit(TokenType.unaryOperator);
				}
			default:
				return null;
		}
	}

	private Token lexComma() throws ParseException {
		int c = peekChar();
		if (isComma(c)) {
			forward(1);
		}

		if (width > 0) {
			return emit(TokenType.comma);
		} else {
			throw error();
		}
	}

	private Token lexParen() throws ParseException {
		int c = peekChar();

		if (isParen(c)) {
			forward(1);
		}

		switch (c) {
			case '(':
				return emit(TokenType.leftParen);
			case ')':
				return emit(TokenType.rightParen);
			default:
				throw error();
		}
	}

	private Token lexBlank() throws ParseException {
		for (; ; ) {
			int c = peekChar();
			if (isBlank(c)) {
				forward(1);
			} else {
				break;
			}
		}

		if (width > 0) {
			return emit(TokenType.blank);
		} else {
			throw error();
		}
	}

	private Token emit(TokenType type) {
		Token token = Token.of(type, input.substring(index - width, index), pos);
		pos = Pos.of(index - width, line, column);
		width = 0;
		return token;
	}

	private void forward(int n) {
		for (int i = 0; i < n; i++) {
			nextChar();
		}
		width += n;
	}

	private int peekChar() {
		if (index == input.length()) {
			return EOF;
		}
		return input.charAt(index);
	}

	private int peekChar(int offset) {
		int i = index + offset;
		if (i >= input.length()) {
			return EOF;
		}
		return input.charAt(i);
	}

	private int nextChar() {
		int c = peekChar();
		if (c == EOF) {
			return EOF;
		}

		index++;
		switch (c) {
			case '\r':
				newLine();
				break;
			case '\n':
				if (index == 1 || input.charAt(index - 2) != '\r') {
					newLine();
				}
				break;
			default:
				column++;
		}

		return c;
	}

	private void newLine() {
		line++;
		column = 1;
	}

	private ParseException error() {
		return new ParseException("syntax error: illegal input characters at " + printPosition());
	}

	private String printPosition() {
		return Pos.print(input, index, line, column);
	}

	private boolean isBlank(int c) {
		switch (c) {
			case ' ':
			case '\r':
			case '\n':
			case '\t':
			case '\f':
			case 0x0b:
				return true;
			default:
				return false;
		}
	}

	private boolean isAlphaNumeric(int c) {
		return isAlpha(c) || isDigit(c);
	}

	private boolean isAlpha(int c) {
		return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
	}

	private boolean isDigit(int c) {
		return c >= '0' && c <= '9';
	}

	private boolean isSign(int c) {
		return c == '+' || c == '-';
	}

	private boolean isComma(int c) {
		return c == ',';
	}

	private boolean isParen(int c) {
		return c == '(' || c == ')';
	}

	private boolean isEOF(int c) {
		return c == EOF;
	}

	public static void main(String[] args) throws IOException, ParseException {
		String input = "a + b * c + (d * e + f) * g >= 5";
//		String input = "amt * 0.5 11";

		Lexer lexer = new Lexer(input);

		for (; ; ) {
			Token t = lexer.next();
			System.out.println(t);
			System.out.println(t.getPos());
			if (t.getType() == TokenType.eof) {
				return;
			}
		}

	}
}
