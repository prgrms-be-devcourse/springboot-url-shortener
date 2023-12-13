package marco.urlshortener.util;

public final class Base62 {

    private static final String BASE62_CHAR = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final int BASE = 62;

    private Base62() {
    }

    public static String encode(long longUrlId) {
        String result = "";

        while (longUrlId > 0) {
            int remind = (int) (longUrlId % BASE);
            longUrlId /= BASE;
            result += BASE62_CHAR.charAt(remind);
        }

        return result;
    }

    public static long decode(String shortUrl) {
        long result = 0L;
        long power = 1L;

        for (int i = 0; i < shortUrl.length(); i++) {
            result += power * BASE62_CHAR.indexOf(shortUrl.charAt(i));
            power *= BASE;
        }

        return result;
    }
}
