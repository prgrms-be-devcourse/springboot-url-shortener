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

	@Column(name = "original_url", nullable = false)
	String originalURL;
	@Column(name = "encoded_id")
	String encodedId;
	@Id
	@GeneratedValue()
	@Column(name = "id")
	private Long id;

	public ShortCut(String originalURL) {

		if (originalURL == null) {
			throw new RuntimeException("originalURL 값이 null 입니다.");
		}

		this.originalURL = originalURL;
	}

	public void updateEncodedId(String encodedId) {

		if (encodedId == null) {
			throw new RuntimeException("encodedId 값이 null 입니다.");
		}

		this.encodedId = encodedId;
	}
}
