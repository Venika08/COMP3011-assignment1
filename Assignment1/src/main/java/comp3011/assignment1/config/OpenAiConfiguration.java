package comp3011.assignment1.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestClient;

/**
 * This configures the HTTP client used for OpenAI
 */

@Configuration
public class OpenAiConfiguration {

	@Bean
	RestClient openAiRestClient(RestClient.Builder builder, @Value("${OPENAI_API_KEY:}") String apiKey, @Value("${OPENAI_BASE_URL:https://api.openai.com}") String baseUrl) {
		
	return builder
			.baseUrl(baseUrl)
			.defaultHeader(HttpHeaders.AUTHORIZATION,  "Bearer " + apiKey)
			.build();
	}
}
