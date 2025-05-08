package lenrek.data_crawling.domain.article;

import jakarta.persistence.*;
import lenrek.data_crawling.domain.category.CategoryEnum;
import lenrek.data_crawling.domain.company.Company;
import lenrek.data_crawling.domain.support.BaseEntity;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
public class Article extends BaseEntity {

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String url;

    @Column(nullable = false)
    private LocalDateTime publishedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CategoryEnum categoryEnum;

    @Column(nullable = false)
    private String summary;

    @Builder
    public Article(String title, String url, LocalDateTime publishedDate, Company company, CategoryEnum categoryEnum, String summary) {
        this.title = title;
        this.url = url;
        this.publishedDate = publishedDate;
        this.categoryEnum = categoryEnum;
        this.company = company;
        this.summary = summary;
    }
}
