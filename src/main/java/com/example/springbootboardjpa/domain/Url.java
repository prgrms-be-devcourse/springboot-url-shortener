package com.example.springbootboardjpa.domain;

import java.util.regex.Pattern;

public class Url {
    public static boolean checkUrl(String url) {
        return Pattern.matches("[(http(s)?):\\/\\/(www\\.)?a-zA-Z0-9@:%._\\+~#=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%_\\+.~#?&//=]*)", url);
    }
}
