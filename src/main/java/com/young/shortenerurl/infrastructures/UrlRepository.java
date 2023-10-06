package com.young.shortenerurl.infrastructures;

import com.young.shortenerurl.model.Url;
import jakarta.persistence.EntityNotFoundException;

import java.util.Optional;

public interface UrlRepository {
    Url save(Url url);

    Optional<Url> findByEncodedUrl(String encodedUrl);

    default Url getByEncodedUrl(String encodedUrl){
        return findByEncodedUrl(encodedUrl)
                .orElseThrow(() -> new EntityNotFoundException("해당 encodedUrl를 가진 url을 찾을 수 없습니다."));
    }
}
