package org.prgrms.url.shortner.springbooturlshortner.domain.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.prgrms.url.shortner.springbooturlshortner.domain.Url;
import org.prgrms.url.shortner.springbooturlshortner.domain.algorithm.Sha256Algorithm;
import org.prgrms.url.shortner.springbooturlshortner.domain.dto.CreateRequest;
import org.prgrms.url.shortner.springbooturlshortner.domain.dto.CreateResponse;
import org.prgrms.url.shortner.springbooturlshortner.domain.dto.SearchResponse;
import org.prgrms.url.shortner.springbooturlshortner.domain.repository.UrlJpaRepository;
import org.prgrms.url.shortner.springbooturlshortner.domain.service.converter.UrlConverter;

@ExtendWith(MockitoExtension.class)
class UrlServiceTest {

	@Mock
	private UrlJpaRepository urlJpaRepository;

	@Mock
	private UrlConverter urlConverter;

	@Mock
	private Sha256Algorithm sha256Algorithm;

	@InjectMocks
	private UrlService urlService;

	@Test
	void createTest() {
		//given
		Url url = new Url("geonwoo.com", sha256Algorithm);
		CreateResponse createResponse = new CreateResponse("12345678");
		CreateRequest createRequest = new CreateRequest("geonwoo.com", "sha256Algorithm");

		when(urlConverter.getUrl(createRequest)).thenReturn(url);
		when(urlConverter.getCreateResponse(createResponse.getShortenUrl())).thenReturn(createResponse);
		when(urlJpaRepository.save(url)).thenReturn(url);
		when(urlJpaRepository.findUrlByShortenUrlEquals(createResponse.getShortenUrl())).thenReturn(Optional.empty());
		when(sha256Algorithm.execute(createRequest.getOriginUrl())).thenReturn(createResponse.getShortenUrl());

		//when
		CreateResponse response = urlService.create(createRequest);

		//then
		verify(urlConverter).getUrl(createRequest);
		verify(urlConverter).getCreateResponse(createResponse.getShortenUrl());
		verify(urlJpaRepository).save(url);
		verify(urlJpaRepository).findUrlByShortenUrlEquals(createResponse.getShortenUrl());
		verify(sha256Algorithm).execute(createRequest.getOriginUrl());

		assertThat(response.getShortenUrl()).isEqualTo(createResponse.getShortenUrl());

	}

	@Test
	void findByShortenUrlTest() {
		//given
		String shortenUrl = "12345678";
		Url url = new Url("geonwoo.com", sha256Algorithm);
		SearchResponse searchResponse = new SearchResponse(url.getOriginUrl());

		when(urlJpaRepository.findUrlByShortenUrlEquals(shortenUrl)).thenReturn(Optional.of(url));
		when(urlConverter.getSearchResponse(url.getOriginUrl())).thenReturn(searchResponse);

		//when
		SearchResponse response = urlService.findByShortenUrl(shortenUrl);

		//then
		verify(urlJpaRepository).findUrlByShortenUrlEquals(shortenUrl);
		verify(urlConverter).getSearchResponse(url.getOriginUrl());

		assertThat(response.getOriginUrl()).isEqualTo(url.getOriginUrl());
	}

}