package prgrms.project.shorturl.domain;

import static javax.persistence.GenerationType.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
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

	protected ShortUrl() {
	}

	private ShortUrl(String originUrl, String shortenUrl) {
		this.originUrl = originUrl;
		this.shortenUrl = shortenUrl;
	}

	public static ShortUrl createShortUrl(String originUrl, String shortenUrl) {
		return new ShortUrl(originUrl, shortenUrl);
	}

	public void increaseNumberOfRequests() {
		this.numberOfRequests++;
	}

	public Long getId() {
		return id;
	}

	public String getOriginUrl() {
		return originUrl;
	}

	public String getShortenUrl() {
		return shortenUrl;
	}

	public int getNumberOfRequests() {
		return numberOfRequests;
	}
}
