package prgrms.project.shorturl.domain;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import prgrms.project.shorturl.domain.ShortUrlDto.CreateDto;
import prgrms.project.shorturl.domain.ShortUrlDto.ResponseDto;
import prgrms.project.shorturl.global.algorithm.ShortUrlGenerator;

@Service
public class ShortUrlService {

	private final ShortUrlRepository shortUrlRepository;
	private final Map<String, ShortUrlGenerator> shortUrlGenerators;

	public ShortUrlService(
		ShortUrlRepository shortUrlRepository,
		Map<String, ShortUrlGenerator> shortUrlGenerators
	) {
		this.shortUrlRepository = shortUrlRepository;
		this.shortUrlGenerators = shortUrlGenerators;
	}

	@Transactional
	public ResponseDto createShortUrl(CreateDto createDto) {
		var shortUrlGenerator = shortUrlGenerators.get(createDto.method());
		var shortUrl = ShortUrl.createShortUrl(createDto.originUrl(), shortUrlGenerator.generate());

		return ShortUrlConverter.toResponseDto(shortUrlRepository.save(shortUrl));
	}
}
