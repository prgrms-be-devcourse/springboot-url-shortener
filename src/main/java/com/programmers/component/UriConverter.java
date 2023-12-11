package com.programmers.component;

import com.programmers.entity.UriEntity;
import com.programmers.model.CreateResponse;
import org.springframework.stereotype.Component;

@Component
public class UriConverter {
    public CreateResponse convertCreateResponseFrom(UriEntity uri) {
        return new CreateResponse(uri.getShortUri(), uri.getCount());
    }
}
