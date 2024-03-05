package shortener.domain;

import org.springframework.data.redis.core.index.Indexed;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.extern.slf4j.Slf4j;
import shortener.global.error.ErrorCode;
import shortener.global.error.exception.BusinessException;

@Slf4j
@Entity
@Table(name = "short_urls")
public class ShortUrl {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	@Indexed
	@Column(name = "encoded_url", unique = true)
	String encodedUrl;
	@Column(name = "original_url", nullable = false, length = 2000)
	String originalUrl;
	@Column(name = "clicks", nullable = false)
	long clicks = 0L;

	protected ShortUrl() {
	}

	public ShortUrl(String originalUrl) {
		log.info("Create ShortUrl Entity...");
		this.originalUrl = originalUrl;
	}

	public void updateEncodedUrl(String encodedUrl) {
		log.info("Update shortUrl({}) to Entity(id({}))", encodedUrl, this.id);
		this.encodedUrl = encodedUrl;
	}

	public Long getId() {
		return id;
	}

	public String getEncodedUrl() {
		return encodedUrl;
	}

	public String getOriginalUrl() {
		return originalUrl;
	}

	public long getClicks() {
		return clicks;
	}
}
