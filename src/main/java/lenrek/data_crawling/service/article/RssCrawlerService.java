package lenrek.data_crawling.service.article;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import lenrek.data_crawling.domain.article.Article;
import lenrek.data_crawling.domain.category.CategoryEnum;
import lenrek.data_crawling.domain.company.Company;
import lenrek.data_crawling.repository.article.ArticleRepository;
import lenrek.data_crawling.repository.company.CompanyRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.StringReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class RssCrawlerService {
    private ArticleRepository articleRepository;
    private CompanyRepository companyRepository;

    @Scheduled(cron = "0 18 11 * * *")
    public void crawlAllFeeds(){
        List<Company> rssFeeds = companyRepository.findAll();

        rssFeeds.forEach(company -> {
            try {
                URL feedUrl = new URL(company.getUrl());

                String rawXml = new String(feedUrl.openStream().readAllBytes(), StandardCharsets.UTF_8);
                String sanitizedXml = sanitizeXml(rawXml);

                SyndFeedInput input = new SyndFeedInput();
                SyndFeed feed = input.build(new StringReader(sanitizedXml));

                int savedCount = 0;
                for (SyndEntry entry : feed.getEntries()) {
                    String title = entry.getTitle();
                    String link = entry.getLink();
                    LocalDateTime pubDate;

                    if (entry.getPublishedDate() != null){
                        pubDate = entry.getPublishedDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                    } else if (entry.getUpdatedDate() != null) {
                        pubDate = entry.getUpdatedDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                    } else continue;

                    if (savedCount >= 20) break;

                    if (!articleRepository.existsByCompanyAndUrl(company, link)) {

                        // AI 요약 및 category 로직
                        CategoryEnum cat = CategoryEnum.BACKEND;
                        String summary = "";

                        Article article = new Article(title, link, pubDate, company, cat, summary);
                        articleRepository.save(article);
                        savedCount++;
                    } else break;
                }
            } catch (Exception e) {
                log.error("Error reading feed from {} : {}", company.getUrl(), e.getMessage(), e);

            }
        });
    }

    // XML 파서 사용 전 원본 스트림 정제
    // 허용되지 않는 ASCII 제어 문자 제거
    public static String sanitizeXml(String xml) {
        return xml.replaceAll("[\\x00-\\x1F&&[^\\x09\\x0A\\x0D]]", "");
    }


}
