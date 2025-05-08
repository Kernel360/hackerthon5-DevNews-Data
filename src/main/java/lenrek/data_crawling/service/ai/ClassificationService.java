package lenrek.data_crawling.service.ai;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lenrek.data_crawling.domain.category.Category;
import lenrek.data_crawling.repository.Category.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClassificationService {
    private final RestTemplate restTemplate;
    private final CategoryRepository categoryRepository;

    @Value("${upstage.api-url}")
    private String apiUrl;

    public String classify(String plainText) {

            String categories = categoryRepository.findAll().stream()
                    .map(Category::getName)
                    .collect(Collectors.joining(", "));

            String systemPrompt = """
            아래 블로그 글의 주제를 다음 카테고리 목록 중 하나로 분류하고,
            정확한 카테고리명만 답변하세요:
            %s
            """.formatted(categories);

        Map<String,Object> payload = Map.of(
                "model", "solar-pro",
                "messages", List.of(
                        Map.of("role", "system",  "content", systemPrompt),
                        Map.of("role", "user",    "content", plainText)
                ),
                "max_tokens", 50,
                "temperature", 0.0
        );

        var response = restTemplate.postForEntity(apiUrl, new HttpEntity<>(payload), Map.class);
        List<?> choices = (List<?>) response.getBody().get("choices");
        String catName = (String)((Map<?,?>)((Map<?,?>)choices.get(0)).get("message")).get("content");
        catName = catName.replaceAll("^\\[|]$", "").trim();
        log.debug("분류 결과: [{}]", catName);
        return catName.strip();
    }
}
