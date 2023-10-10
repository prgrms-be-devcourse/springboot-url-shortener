package shortener.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebMvc
@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
			.allowedOrigins("http://url-shortener-react:3000", "http://localhost:3000",
				"http://ec2-3-35-240-254.ap-northeast-2.compute.amazonaws.com:3000")
			.allowedMethods("GET", "POST", "PUT", "DELETE")
			.allowedHeaders("*");
	}
}
