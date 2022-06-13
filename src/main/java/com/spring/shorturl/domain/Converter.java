package com.spring.shorturl.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Converter {

    public Post convertPost(PostDto.SaveRequest postDto) {
        return Post.builder()
                .title(postDto.title())
                .build();
    }

    public PostDto.Response convertPostDto(Post post) {
        return new PostDto.Response(
                post.getId(),
                post.getTitle());
    }
}
