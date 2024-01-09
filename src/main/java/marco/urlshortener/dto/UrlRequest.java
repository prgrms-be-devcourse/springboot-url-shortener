package marco.urlshortener.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;

public record UrlRequest(
        @URL
        @NotBlank
        @Size(max = 255)
        String longUrl) {

}
