package lenrek.data_crawling.service.ai;

import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;

@Service
public class TextPreprocessingService {
    public String extractPlainText(String html) {
        return Jsoup.parse(html)
                    .text()
                    .replaceAll("\\s{2,}", " ")
                    .trim();
    }
}
