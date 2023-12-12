package kdt.shorturl.grobal.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class UrlValidator implements ConstraintValidator<UrlValid, String> {
    private static final String HTTPS_PROTOCOL = "https://";
    private static final String HTTP_PROTOCOL = "http://";

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        try {
            URL url = normalizeUrl(value);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("HEAD");
            int responseCode = connection.getResponseCode();
            return responseCode == HttpURLConnection.HTTP_OK;
        } catch (Exception e) {
            return false;
        }
    }

    private static URL normalizeUrl(String urlString) throws MalformedURLException {
        if (hasNotProtocol(urlString)) {
            urlString = HTTPS_PROTOCOL + urlString;
        }
        return new URL(urlString);
    }

    private static boolean hasNotProtocol(String originUrl) {
        return !(originUrl.startsWith(HTTPS_PROTOCOL) || originUrl.startsWith(HTTP_PROTOCOL));
    }
}
