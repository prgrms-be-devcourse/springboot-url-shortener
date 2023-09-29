package com.prgrms.urlshortener.ratelimiter;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Duration;
import java.util.Map;

@Component
public class IPBasedRateLimitingFilter extends OncePerRequestFilter {

    private final Map<String, Bucket> buckets;

    public IPBasedRateLimitingFilter(Map<String, Bucket> buckets) {
        this.buckets = buckets;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String ip = IpGenerator.getClientIP(request);
        Bucket bucket = getBucketForIp(ip);

        if (bucket.tryConsume(1)) {
            filterChain.doFilter(request, response);
        } else {
            final String errorMessage =
                    """
                        {
                            "message": "너무 많은 요청입니다. 1분 후에 다시 시도해 주세요."
                        }
                    """;
            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            response.addHeader("Content-Type", "application/json; charset=utf-8");// 60 seconds
            response.getWriter().write(errorMessage);
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();
        return !path.startsWith("/v1/urls");
    }

    private Bucket getBucketForIp(String ip) {
        return buckets.computeIfAbsent(ip, k -> createNewBucket());
    }

    private Bucket createNewBucket() {
        return Bucket4j.builder()
                .addLimit(Bandwidth.classic(10, Refill.intervally(1, Duration.ofMinutes(1))))
                .build();
    }
}