package lenrek.data_crawling.service.article;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import lenrek.data_crawling.domain.article.Article;
import lenrek.data_crawling.domain.article.Category;
import lenrek.data_crawling.domain.company.Company;
import lenrek.data_crawling.repository.article.ArticleRepository;
import lenrek.data_crawling.repository.company.CompanyRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class RssCrawlerService {
    private ArticleRepository articleRepository;
    private CompanyRepository companyRepository;

    @Scheduled(cron = "0 50 1 * * *")
    public void crawlAllFeeds(){
        List<Company> rssFeeds = companyRepository.findAll();

        rssFeeds.forEach(company -> {
            String feedUrl = company.getUrl();

            try {
                SyndFeedInput input = new SyndFeedInput();
                SyndFeed feed = input.build(new XmlReader(new URL(feedUrl)));

                int savedCount = 0;
                for (SyndEntry entry : feed.getEntries()) {
                    String title = entry.getTitle();
                    String link = entry.getLink();
                    LocalDateTime pubDate = entry.getPublishedDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

                    if (savedCount >= 20) break;

                    if (!articleRepository.existsByCompanyAndUrl(company, link)) {

                        // AI 요약 및 category 로직
                        Category cat = Category.BACKEND;
                        String summary = "";

                        Article article = new Article(title, link, pubDate, company, cat, summary);
                        articleRepository.save(article);
                        savedCount++;
                    } else break;
                }
            } catch (Exception e) {
                log.error("Error reading feed from {} : {}", feedUrl, e.getMessage(), e);

            }
        });
    }

}
