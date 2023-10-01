package org.prgrms.urlshortener.util.encoder;

import org.springframework.stereotype.Component;

@Component
public interface EncodePolicy {

	String encode(String originUrl);
}
