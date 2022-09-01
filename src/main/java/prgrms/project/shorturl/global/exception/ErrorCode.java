package prgrms.project.shorturl.global.exception;

public enum ErrorCode {

	ENTITY_NOT_FOUND("Entity not found", "엔티티를 찾을 수 없습니다."),
	INVALID_METHOD_ARGUMENT("Invalid method argument", "입력된 값이 잘 못 되었습니다."),
	UNKNOWN_SERVER_ERROR("Unknown server error", "알 수 없는 서버 에러가 발생하였습니다.");

	private final String code;
	private final String message;

	ErrorCode(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String code() {
		return this.code;
	}

	public String message() {
		return this.message;
	}
}
