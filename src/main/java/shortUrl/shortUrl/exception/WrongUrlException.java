package shortUrl.shortUrl.exception;

public class WrongUrlException extends RuntimeException {
    public WrongUrlException() {
    }

    public WrongUrlException(String message) {
        super(message);
    }

    public WrongUrlException(String message, Throwable cause) {
        super(message, cause);
    }

    public WrongUrlException(Throwable cause) {
        super(cause);
    }

    public WrongUrlException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
