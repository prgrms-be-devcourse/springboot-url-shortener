package kr.co.programmers.shortcut.api;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import kr.co.programmers.shortcut.application.ShortCutService;
import kr.co.programmers.shortcut.dto.ShortCutCreateRequest;
import kr.co.programmers.shortcut.dto.ShortCutResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/shortcut")
public class ShortCutMainController {

	private final ShortCutService shortCutService;

	@GetMapping()
	public String showForm(Model model) {
		model.addAttribute("shortCutCreateRequest", new ShortCutCreateRequest("input url"));
		return "shortcut-form";
	}

	@PostMapping()
	public ModelAndView submitForm(@Validated @ModelAttribute ShortCutCreateRequest shortCutCreateRequest) {
		ShortCutResponse shortCutResponse = shortCutService.createShortCut(shortCutCreateRequest);
		return new ModelAndView("shortcut-result", "shortCut", shortCutResponse);
	}

	@GetMapping("/{encodedId}")
	public ModelAndView redirectToOriginalURL(
		@PathVariable String encodedId
	) {
		String originalURL = shortCutService.getOriginalURL(encodedId);
		return new ModelAndView("redirect:" + originalURL);
	}

}
