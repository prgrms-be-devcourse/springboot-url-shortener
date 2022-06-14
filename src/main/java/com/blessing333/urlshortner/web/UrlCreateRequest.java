package com.blessing333.urlshortner.web;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UrlCreateRequest {
    @URL(message = "Invalid url")
    @NotNull
    private String originalUrl;
    private String customDomain;
    @NotBlank
    private String option;
}
