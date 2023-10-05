package shortener.global.error;

public enum ErrorCode {
	INVALID_REQUEST_NUMBERS("U001", "API의 총 요청 횟수가 잘 못 되었습니다."),
	NOT_FOUND_MAPPED_URL("U002", "요청받은 URL을 찾을 수 없습니다.");

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
