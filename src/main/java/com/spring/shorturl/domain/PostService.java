package com.spring.shorturl.domain;

import com.spring.shorturl.global.exception.NotFoundEntityByIdException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {
    private final PostRepository postRepository;
    private final Converter converter;

    @Transactional
    public Long save(PostDto.SaveRequest dto) {
        Post post = converter.convertPost(dto);
        Post entity = postRepository.save(post);
        return entity.getId();
    }

    public PostDto.Response findById(Long id) {
        return postRepository.findById(id)
                .map(converter::convertPostDto)
                .orElseThrow(() -> new NotFoundEntityByIdException("Post Entity 를 찾을 수 없습니다. postID : " + id));
    }
}
