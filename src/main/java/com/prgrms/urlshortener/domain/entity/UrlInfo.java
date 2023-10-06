package com.prgrms.urlshortener.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "url_info")
@SequenceGenerator(
	name = "url_info_seq_generator",
	sequenceName = "url_info_seq",
	initialValue = 10000
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UrlInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "url_info_seq_generator")
	@Column(name = "id")
	Long id;

	@Column(name = "original_url", nullable = false)
	private String originalUrl;

	public UrlInfo(String originalUrl) {
		this.originalUrl = originalUrl;
	}
}
