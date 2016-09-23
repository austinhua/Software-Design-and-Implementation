package exceptions;

public class SerializerException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public SerializerException() {}

	public SerializerException(String message) {
		super(message);
	}

	public SerializerException(Throwable cause) {
		super(cause);
	}

	public SerializerException(String message, Throwable cause) {
		super(message, cause);
	}

	public SerializerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
