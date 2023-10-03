package kr.co.programmers.shortcut.dto;

import jakarta.validation.constraints.Size;

public record ShortCutCreateRequest(

	@Size(max = 1000, message = "입력 받을 수 있는 URL은 최대 {max}글자 입니다.")
	String originalURL
){
}
