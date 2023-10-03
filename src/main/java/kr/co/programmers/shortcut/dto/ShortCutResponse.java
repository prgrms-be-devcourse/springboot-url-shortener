package kr.co.programmers.shortcut.dto;

import kr.co.programmers.shortcut.domain.ShortCut;

public record ShortCutResponse (
	String originalURL,
	String newURL
) {

	public static ShortCutResponse from(ShortCut shortCut) {
		return new ShortCutResponse(
			shortCut.getOriginalURL(),
			shortCut.getNewURL()
		);
	}
}
