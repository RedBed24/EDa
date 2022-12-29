package B2_03_trabajo;

@SuppressWarnings("serial")
public class ProblemInTrainException extends RuntimeException {

	public ProblemInTrainException() {
	}

	public ProblemInTrainException(String message) {
		super(message);
	}

	public ProblemInTrainException(Throwable cause) {
		super(cause);
	}

	public ProblemInTrainException(String message, Throwable cause) {
		super(message, cause);
	}

	public ProblemInTrainException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}