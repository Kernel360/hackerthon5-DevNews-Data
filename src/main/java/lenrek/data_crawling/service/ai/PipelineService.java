package lenrek.data_crawling.service.ai;

import lenrek.data_crawling.domain.category.Category;
import lenrek.data_crawling.repository.Category.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PipelineService {
    private final TextPreprocessingService preprocessor;
    private final SummarizationService summarizer;
    private final ClassificationService classifier;
    private final CategoryRepository categoryRepository;

    @Transactional
    public SummaryAndClassify summarizeAndClassify(String html, String title) {
        String text = preprocessor.extractPlainText(html);
        String summary = summarizer.summarize(title, text);
        String catName = classifier.classify(text);
        Category category = categoryRepository.findByName(catName);
        return new SummaryAndClassify(summary, category);
    }
}
