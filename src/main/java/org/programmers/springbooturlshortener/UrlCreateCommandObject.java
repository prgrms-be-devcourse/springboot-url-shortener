package org.programmers.springbooturlshortener;

import lombok.Getter;
import lombok.Setter;
import org.programmers.springbooturlshortener.encoding.Encoding;

@Getter
@Setter
public class UrlCreateCommandObject {

    private String original;
    private Encoding encoding;
}