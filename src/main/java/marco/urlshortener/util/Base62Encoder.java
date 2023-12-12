package marco.urlshortener.util;

public class Base62Encoder {
    private static final String BASE62_CHAR = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final int BASE = 62;

    private Base62Encoder() {
    }

    public static String toBase62(long longUrlId) {
        String result = "";

        while (longUrlId > 0) {
            int remind = (int) (longUrlId % BASE);
            longUrlId /= BASE;
            result += BASE62_CHAR.charAt(remind);
        }

        return result;
    }
}
