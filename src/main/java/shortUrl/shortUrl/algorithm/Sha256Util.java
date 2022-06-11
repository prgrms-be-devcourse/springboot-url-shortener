package shortUrl.shortUrl.algorithm;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Sha256Util {

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
            stringBuilder.append(String.format("%02x", b));
        }
        return stringBuilder.toString();
    }
}
