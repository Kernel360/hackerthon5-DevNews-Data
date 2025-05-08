package lenrek.data_crawling.service.summary;

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

    public String summarize(String plainText) {
        Map<String, Object> systemMsg = Map.of(
                "role", "system",
                "content", """
        다음 블로그 글을 한국어로 **3문장 이내**로, **원본의 20% 분량 이하**가 되도록 과감히 압축하여 요약해 주세요.
        핵심 키워드만 포함하고, 상세 설명은 생략하십시오.
        """
        );
        Map<String, Object> userMsg = Map.of(
            "role", "user",
            "content", plainText
        );

        Map<String, Object> payload = Map.of(
            "model", "solar-pro",
            "messages", List.of(systemMsg, userMsg),
            "max_tokens", 500,
            "temperature", 0.3
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
