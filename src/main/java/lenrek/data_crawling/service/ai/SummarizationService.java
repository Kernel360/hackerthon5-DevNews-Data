package lenrek.data_crawling.service.ai;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import java.util.*;

@Service
@RequiredArgsConstructor
public class SummarizationService {
    private final RestTemplate restTemplate;

    @Value("${upstage.api-url}")
    private String apiUrl;

    public String summarize(String title, String plainText) {
        Map<String, Object> systemMsg = Map.of(
                "role", "system",
                "content", """
                You are an expert summarizer for Korean-language technical blog posts.  
        Use the **title** below to focus your summary, then read the full article and generate a concise summary in Korean that meets all of the following:
        
        📌 **Title**: %s
        
        - No more than **3 sentences**.  
        - Do **not** add any new information; include only facts explicitly stated in the source.  
        - Each sentence must convey a **distinct** key point (no repetition).  
        - Omit any unnecessary introductions or conclusions.  
        - Include **only** core keywords; skip detailed explanations.  
        - **Respond with the summary only**—no extra commentary or labels.
        """.formatted(title)
        );
        Map<String, Object> userMsg = Map.of(
            "role", "user",
            "content", plainText
        );

        Map<String, Object> payload = Map.of(
            "model", "solar-pro",
            "messages", List.of(systemMsg, userMsg),
            "max_tokens", 200,
            "temperature", 0.0
        );

        HttpEntity<Map<String,Object>> request = new HttpEntity<>(payload);
        ResponseEntity<Map> response = restTemplate.postForEntity(apiUrl, request, Map.class);

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            List<?> choices = (List<?>) response.getBody().get("choices");
            Map<?,?> choice0 = (Map<?,?>) choices.get(0);
            Map<?,?> message = (Map<?,?>) choice0.get("message");
            return (String) message.get("content");
        } else {
            throw new RuntimeException("요약 API 호출 실패: HTTP " + response.getStatusCode());
        }
    }
}
