package prgrms.project.shorturl.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenAPIConfig {

	@Bean
	public OpenAPI openAPI() {
		var info = new Info()
			.title("ShortUrl API")
			.version("0.0.1")
			.description("ShortUrl API 입니다.");

		return new OpenAPI().info(info);
	}
}
