package kr.co.programmers.shortcut.dto;

import kr.co.programmers.shortcut.domain.ShortCut;

public record ShortCutResponse(
	String originalURL,
	String newURL
) {

	public static ShortCutResponse from(ShortCut shortCut, String shortCutURL) {
		return new ShortCutResponse(
			shortCut.getOriginalURL(),
			shortCutURL
		);
	}
}
