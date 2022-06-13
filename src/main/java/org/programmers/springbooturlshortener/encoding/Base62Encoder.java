package org.programmers.springbooturlshortener.encoding;

final class Base62Encoder extends Encoder {

    public Base62Encoder() {
        super(charsetData);
    }

    static final char[] charsetData = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
            'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
            'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd',
            'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
            'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x',
            'y', 'z'
    };

    private static final int CHARSET_LENGTH = charsetData.length;

    @Override
    String encode(long id) {
        StringBuilder sb = new StringBuilder();
        while (id > 0) {
            long maxValue = Long.MAX_VALUE;
            int i = (int) (id % CHARSET_LENGTH);
            sb.append(charsetData[i]);
            id = id / CHARSET_LENGTH;
        }
        return sb.toString();
    }
}