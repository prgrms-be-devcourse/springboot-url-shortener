package prgms.marco.springbooturlshortener.exception;

public class InvalidShortUrlException extends ClientException {

    private final Message message;

    public InvalidShortUrlException(Message message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message.getMessage();
    }
}
