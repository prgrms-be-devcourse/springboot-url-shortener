package prgrms.project.shorturl.global.exception;

import java.util.Collections;
import java.util.List;

import org.springframework.validation.BindingResult;

import io.swagger.v3.oas.annotations.media.Schema;

public record CustomFieldError(
	@Schema(description = "필드 이름")
	String field,
	@Schema(description = "입력된 값")
	Object value,
	@Schema(description = "원인")
	String cause
) {

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
