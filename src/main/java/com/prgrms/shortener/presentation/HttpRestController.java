package com.prgrms.shortener.presentation;

import com.prgrms.shortener.domain.ShortenedUrlService;
import com.prgrms.shortener.domain.dto.UrlMetadata;
import com.prgrms.shortener.presentation.exception.InvalidUrlRequestException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HttpRestController {

  private final ShortenedUrlService shortenedUrlService;

  public HttpRestController(ShortenedUrlService shortenedUrlService) {
    this.shortenedUrlService = shortenedUrlService;
  }

  @PostMapping("/url")
  @ResponseBody
  public ShortenedUrlResponse shortenUrl(
      @RequestBody @Valid ShortenedUrlPayload payload, BindingResult bindingResult, HttpServletRequest httpServletRequest) {
    if (bindingResult.hasErrors()) {
      throw new InvalidUrlRequestException(bindingResult);
    }
    String key = shortenedUrlService.shorten(payload.getUrl());
    String urlWithoutPath = getUrlWithoutPath(httpServletRequest);
    return new ShortenedUrlResponse(urlWithoutPath, key);
  }

  private String getUrlWithoutPath(HttpServletRequest httpServletRequest) {
    String requestUrl = httpServletRequest.getRequestURL().toString();
    return requestUrl.substring(0, requestUrl.length() - 3);

  }

  @GetMapping("/url")
  @ResponseBody
  public ShortenedUrlResponse getUrlMetadata(@Param("key") String key, HttpServletRequest httpServletRequest) {
    UrlMetadata metadata = shortenedUrlService.getUrlMetadata(key);
    return ShortenedUrlResponse.from(metadata, getUrlWithoutPath(httpServletRequest));

  }
}
