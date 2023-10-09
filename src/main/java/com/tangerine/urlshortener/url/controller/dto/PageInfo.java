package com.tangerine.urlshortener.url.controller.dto;

import org.springframework.data.domain.Pageable;

public record PageInfo(
        boolean hasPreviousPage,
        boolean hasNextPage,
        int pageNumber
) {

    public static PageInfo from(Pageable pageable, int totalPageSize) {
        return new PageInfo(
                pageable.hasPrevious(),
                pageable.getPageNumber() < totalPageSize,
                pageable.getPageNumber()
        );
    }

}
