package kr.co.programmers.shortcut.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "shortcuts")
public class ShortCut {

	@Id
	@GeneratedValue()
	@Column(name = "id")
	private Long id;

	@Column(name = "original_url", nullable = false)
	String originalURL;

	@Column(name = "new_url", nullable = false)
	String newURL;

	public ShortCut(String originalURL, String newURL) {
		this.originalURL = originalURL;
		this.newURL = newURL;
	}
}
