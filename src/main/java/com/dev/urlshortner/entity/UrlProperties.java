package com.dev.urlshortner.entity;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@ConfigurationProperties(prefix = "app.url")
@Getter
@Setter
public class UrlProperties {
	private String host;
	private int keyLength;
}
