package prgrms.project.shorturl.domain;

import static javax.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.apache.commons.lang3.math.NumberUtils;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class ShortUrl extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = SEQUENCE)
	@Column(name = "id", unique = true, nullable = false, updatable = false)
	private Long id;

	@Column(name = "origin_url", nullable = false, updatable = false, length = 500)
	private String originUrl;

	@Column(name = "shorten_url", length = 100)
	private String shortenUrl;

	@Column(name = "number_of_requests", nullable = false)
	private int numberOfRequests;

	public ShortUrl(String originUrl, String shortenUrl) {
		this.originUrl = originUrl;
		this.shortenUrl = shortenUrl;
		this.numberOfRequests = NumberUtils.INTEGER_ZERO;
	}

	public void increaseNumberOfRequests() {
		this.numberOfRequests++;
	}
}
