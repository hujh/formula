package im.hujh.formula;

/**
 * @author hujh
 */
public class Token {

	private final TokenType type;
	private final String value;
	private final Pos pos;

	private Token(TokenType type, String value, Pos pos) {
		this.type = type;
		this.value = value;
		this.pos = pos;
	}

	public TokenType getType() {
		return type;
	}

	public String getValue() {
		return value;
	}

	public Pos getPos() {
		return pos;
	}

	public static Token of(TokenType type, String value, Pos pos) {
		return new Token(type, value, pos);
	}

	@Override
	public String toString() {
		StringBuilder buf = new StringBuilder();
		buf.append('<');
		buf.append(type.toString());
		if (value != null && !value.isEmpty()) {
			buf.append(':');
			buf.append(value);
		}
		buf.append('>');
		return buf.toString();
	}

}
