package prgrms.project.shorturl.domain;

import prgrms.project.shorturl.domain.ShortUrlDto.ResponseDto;

public class ShortUrlConverter {

	private ShortUrlConverter() {
	}

	public static ResponseDto toResponseDto(ShortUrl shortUrl) {
		return new ResponseDto(
			shortUrl.getId(), shortUrl.getOriginUrl(), shortUrl.getShortenUrl(), shortUrl.getNumberOfRequests());
	}
}
