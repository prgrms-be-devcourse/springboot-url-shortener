package com.youngurl.shortenerurl.infrastructures;

import com.youngurl.shortenerurl.model.Url;

public interface UrlRepository {
    Url save(Url url);
}
