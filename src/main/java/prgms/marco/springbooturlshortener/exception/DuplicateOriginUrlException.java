package prgms.marco.springbooturlshortener.exception;

public class DuplicateOriginUrlException extends ClientException {

    private final Message message;

    public DuplicateOriginUrlException(Message message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message.getMessage();
    }
}
