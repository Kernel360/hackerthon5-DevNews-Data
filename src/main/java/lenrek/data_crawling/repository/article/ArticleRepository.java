package lenrek.data_crawling.repository;

import lenrek.data_crawling.domain.article.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    boolean existsBySourceAndUrl(String source, String url);
}
