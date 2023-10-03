package kr.co.programmers.shortcut.application;

import org.springframework.stereotype.Component;

@Component
public class Base62 {

	private final static int LENGTH = 62;

	private int[] lookup;

	private static final byte[] charSet = {
		 '0',  '1',  '2',  '3',  '4',  '5',  '6',  '7',
		 '8',  '9',  'A',  'B',  'C',  'D',  'E',  'F',
		 'G',  'H',  'I',  'J',  'K',  'L',  'M',  'N',
		 'O',  'P',  'Q',  'R',  'S',  'T',  'U',  'V',
		 'W',  'X',  'Y',  'Z',  'a',  'b',  'c',  'd',
		 'e',  'f',  'g',  'h',  'i',  'j',  'k',  'l',
		 'm',  'n',  'o',  'p',  'q',  'r',  's',  't',
		 'u',  'v',  'w',  'x',  'y',  'z'
	};

	public Base62() {
		createLookupTable();
	}

	public String encodeNumber(long originalNumber) {
		StringBuffer encodedNumberBuffer = new StringBuffer();
		while (originalNumber > 0) {
			encodedNumberBuffer.append(charSet[(int)originalNumber%LENGTH]);
			originalNumber = originalNumber/LENGTH;
		}
		return encodedNumberBuffer.toString();
	}

	public long decodeKey(String targetKey) {
		long originalNumber = 0;
		long power = 1;

		int beforeIdx, idx;
		for (int i=0; i < targetKey.length(); i++) {
			beforeIdx = targetKey.charAt(i);
			idx = lookup[beforeIdx];
			originalNumber = originalNumber + idx * power;
			power = power * LENGTH;
		}

		return originalNumber;
	}

	private void createLookupTable() {
		lookup = new int[256];

		for (int i = 0; i < charSet.length; i++) {
			int idx = charSet[i] - '0';
			lookup[idx] = i;
		}
	}
}
