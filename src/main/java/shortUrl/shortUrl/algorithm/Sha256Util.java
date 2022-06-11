package shortUrl.shortUrl.algorithm;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class Sha256Util {

    private final List<String> PASS_STRING = new ArrayList<>(List.of("0", "O", "I", "l", "1", "o"));

    public String encoding(Long value){
        String msg = value.toString();
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        md.update(msg.getBytes());

        return bytesToHex(md.digest()).substring(0, 7);
    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder stringBuilder = new StringBuilder();
        for (byte b : bytes) {
            String string = String.format("%02x", b);
            if (PASS_STRING.contains(string)) {
                continue;
            }
            stringBuilder.append(string);
        }
        return stringBuilder.toString();
    }
}
