package shortener.domain.urlencoder.algorithm;

public enum AlgorithmType {
	BASE62(1, "Base62"),
	SHORT_UUID(2, "ShortUuid"),
	ADLER(3, "Adler");

	private final int id;
	private final String name;

	AlgorithmType(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}
}
