package org.programmers.springbooturlshortener.encoding;

abstract class Encoder {

    Charset charset;

    public Encoder(char[] charsetData) {
        this.charset = new Charset(charsetData);
    }

    abstract String encode(long id);

    static class Charset {
        final char[] charsetData;

        Charset(char[] charsetData) {
            this.charsetData = charsetData;
        }

        boolean contains(char code) {
            for (char charsetDatum : charsetData) {
                if (code == charsetDatum) {
                    return true;
                }
            }
            return false;
        }

        boolean contains(String checked) {
            char[] charsToCheck = checked.toCharArray();
            for (char charToCheck : charsToCheck) {
                if (!contains(charToCheck)) {
                    return false;
                }
            }
            return true;
        }
    }
}
