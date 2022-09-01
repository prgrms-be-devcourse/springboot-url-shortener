package prgrms.project.shorturl.global.algorithm;

import org.springframework.stereotype.Component;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;

@Component(value = "nanoId")
public class NanoIdGenerator implements ShortUrlGenerator {

	@Override
	public String generate() {
		return NanoIdUtils.randomNanoId();
	}
}
