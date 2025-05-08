package lenrek.data_crawling.global.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import java.util.*;

@Configuration
public class RestClientConfig {
    @Value("${upstage.api-key}")
    private String apiKey;

    @Value("${upstage.api-url}")
    private String apiUrl;

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate rest = new RestTemplate();

        ClientHttpRequestInterceptor bearerAuth = (request, body, execution) -> {
            request.getHeaders().add("Authorization", "Bearer " + apiKey);
            request.getHeaders().add("Content-Type", "application/json");
            return execution.execute(request, body);
        };
        rest.setInterceptors(List.of(bearerAuth));
        return rest;
    }
}

