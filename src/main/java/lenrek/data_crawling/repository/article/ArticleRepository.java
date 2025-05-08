package lenrek.data_crawling.repository.article;

import lenrek.data_crawling.domain.article.Article;
import lenrek.data_crawling.domain.company.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
    boolean existsByCompanyAndUrl(Company company, String url);
}
