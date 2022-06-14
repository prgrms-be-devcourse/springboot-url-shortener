package shortUrl.shortUrl.algorithm;

public class Base56Util {

    static final char[] BASE = "ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnpqrstuvwxyz23456789".toCharArray();

    public String encoding(Long value) {
        StringBuilder stringBuilder = new StringBuilder();
        while (value > 0 && stringBuilder.length() < 7) {
            int i = (int) (value % 56);
            stringBuilder.append(BASE[i]);
            value /= 56;
        }
        return stringBuilder.toString();
    }
}
