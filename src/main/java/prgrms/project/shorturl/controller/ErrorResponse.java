package prgrms.project.shorturl.controller;

public record ErrorResponse(
    String message,
    String cause
) {
}
