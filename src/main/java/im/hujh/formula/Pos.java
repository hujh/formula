package im.hujh.formula;

/**
 * @author hujh
 */
public final class Pos {

    private final int index;
    private final int line;
    private final int column;

    public Pos(int index, int line, int column) {
        this.index = index;
        this.line = line;
        this.column = column;
    }

    public int getIndex() {
        return index;
    }

    public int getLine() {
        return line;
    }

    public int getColumn() {
        return column;
    }

    public static Pos of(int index, int line, int column) {
        return new Pos(index, line, column);
    }

    public String print(String input) {
        return print(input, index, line, column);
    }

    public static String print(String input, int index, int line, int column) {
        StringBuilder b = new StringBuilder();
        b.append("\"");
        b.append(input, Math.max(0, index - 5), Math.min(input.length(), index + 10));
        b.append('\"');
        b.append(" at line ");
        b.append(line);
        b.append(" column ");
        b.append(column);
        return b.toString();
    }

    @Override
    public String toString() {
        return "[" + index + "," + line + "," + column + ']';
    }
}
