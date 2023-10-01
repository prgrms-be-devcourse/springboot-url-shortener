package org.prgrms.urlshortener.util.decoder;

import org.springframework.stereotype.Component;

@Component
public interface DecodePolicy {

	String decode(String encodedurl);

}
