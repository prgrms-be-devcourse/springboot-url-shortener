package shortUrl.shortUrl.controller;

import org.springframework.web.bind.annotation.*;
import shortUrl.shortUrl.domain.dto.CreateShortUrlDto;
import shortUrl.shortUrl.domain.dto.ShortUrlDto;
import shortUrl.shortUrl.domain.service.UrlService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class UrlController {

    private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @PostMapping("/create")
    public ShortUrlDto createShortUrl(@RequestBody CreateShortUrlDto createShortUrlDto) {
        return urlService.createShortUrl(createShortUrlDto);
    }

    @PostMapping("/link")
    public void linkOriginalUrl(@RequestBody ShortUrlDto shortUrlDto,
                                HttpServletResponse response) throws IOException {
        String shortUrl = shortUrlDto.getShortUrl();
        String originalUrl = urlService.findOriginUrlByShortUrl(shortUrl);

        response.sendRedirect(originalUrl);
    }

    @PostMapping("/{shortUrl}")
    public void redirectUrl(@PathVariable("shortUrl") String shortUrl, HttpServletResponse response) throws IOException {
        String originalUrl = urlService.findOriginUrlByShortUrl(shortUrl);
        response.sendRedirect(originalUrl);
    }

    @GetMapping("/{shortUrl}/info")
    public ShortUrlDto getInfoShortUrl(@PathVariable("shortUrl") String shortUrl) throws IOException {
        return urlService.getUrlInfo(shortUrl);
    }
}
