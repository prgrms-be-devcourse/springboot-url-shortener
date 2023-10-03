package kr.co.programmers.shortcut.api;

import org.springframework.web.bind.annotation.GetMapping;

public class ShortCutMainController {

	@GetMapping("/index")
	public String getIndex() {
		return "index.html";
	}
}
