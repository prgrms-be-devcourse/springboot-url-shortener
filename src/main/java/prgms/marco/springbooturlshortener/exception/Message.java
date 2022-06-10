package prgms.marco.springbooturlshortener.exception;

public enum Message {
    DUPLICATE_ORIGIN_URL_EXP_MSG("이미 존재하는 origin url 입니다."),
    INVALID_SHORT_URL_EXP_MSG("유효하지 않은 ShortUrl 입니다.");

    private final String message;

    Message(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
