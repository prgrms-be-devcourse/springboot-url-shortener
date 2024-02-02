package org.prgrms.urlshortener.application;

import org.prgrms.urlshortener.domain.Algorithm;
import org.prgrms.urlshortener.util.encoder.Base62Encode;
import org.prgrms.urlshortener.util.encoder.EncodePolicy;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class EncodePolicyFactory {

	private final ApplicationContext applicationContext;

	public EncodePolicy createEncodePolicy(Algorithm algorithm) {
		switch (algorithm) {
			case BASE_62 -> {
				return applicationContext.getBean(Base62Encode.class);
			}
		}

		throw new RuntimeException("존재하지 않는 알고리즘입니다.");
	}
}
