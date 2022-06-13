package com.spring.shorturl.domain.controller;

import com.spring.shorturl.domain.PostDto;
import com.spring.shorturl.domain.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping("/{id}")
    public ApiResponse<PostDto.Response> findById(
            @PathVariable Long id
    ) {
        PostDto.Response findOne = postService.findById(id);
        return ApiResponse.ok(findOne);
    }
}
