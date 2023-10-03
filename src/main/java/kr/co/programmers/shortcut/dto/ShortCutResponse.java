package kr.co.programmers.shortcut.dto;

public record ShortCutResponse (
	String originalURL,
	String newURL
) {
}
