package prgrms.project.shorturl.global.exception;

import java.util.Collections;
import java.util.List;

import org.springframework.validation.BindingResult;

public record CustomFieldError(String field, Object value, String cause) {

	public static List<CustomFieldError> from(BindingResult bindingResult) {
		var fieldErrors = bindingResult.getFieldErrors();

		if (fieldErrors.isEmpty()) {
			return Collections.emptyList();
		}

		return fieldErrors.stream()
			.map(e -> new CustomFieldError(e.getField(), e.getRejectedValue(), e.getDefaultMessage()))
			.toList();
	}
}
