package shortener.global.error;

public enum ErrorCode {
	INVALID_REQUEST_NUMBERS("U001", "API의 총 요청 횟수가 잘 못 되었습니다."),
	NOT_FOUND_MAPPED_URL("U002", "요청받은 URL을 찾을 수 없습니다."),
	INVALID_ENCODING_ALGORITHM("U003", "올바르지 않은 인코딩 요청입니다."),
	INVALID_INPUT_VALUE("U004", "올바르지 않은 입력입니다.");

	private final String code;
	private final String message;

	ErrorCode(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
}
