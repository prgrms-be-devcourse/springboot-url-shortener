package com.dev.urlshortner.domain;

import java.util.function.Supplier;

import com.dev.urlshortner.util.Base62RandomKeyGenerator;
import com.dev.urlshortner.util.ShortKeyGenerator;

public enum KeyEncodingType {
	BASE62(Base62RandomKeyGenerator::new);

	private final Supplier<ShortKeyGenerator> encoderSupplier;

	KeyEncodingType(Supplier<ShortKeyGenerator> encoderSupplier) {
		this.encoderSupplier = encoderSupplier;
	}

	public ShortKeyGenerator getEncoder() {
		return encoderSupplier.get();
	}
}
