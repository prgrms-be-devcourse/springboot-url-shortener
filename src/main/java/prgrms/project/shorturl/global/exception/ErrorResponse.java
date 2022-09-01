package prgrms.project.shorturl.global.exception;

public record ErrorResponse(
    String message,
    String cause
) {
}
