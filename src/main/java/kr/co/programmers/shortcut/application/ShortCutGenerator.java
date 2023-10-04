package kr.co.programmers.shortcut.application;

import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ShortCutGenerator {

	private final HttpServletRequest request;

	public String generateShortCutURL(String encodeId) {
		StringBuffer shortCutURL = request.getRequestURL();
		shortCutURL.append("/" + encodeId);
		return shortCutURL.toString();
	}
}
