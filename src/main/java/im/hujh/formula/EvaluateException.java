package im.hujh.formula;

/**
 * @author hujh
 */
public class EvaluateException extends Exception {

    private static final long serialVersionUID = 1L;

	public EvaluateException() {
	}

	public EvaluateException(String message) {
		super(message);
	}

	public EvaluateException(String message, Throwable cause) {
		super(message, cause);
	}

	public EvaluateException(Throwable cause) {
		super(cause);
	}

}
