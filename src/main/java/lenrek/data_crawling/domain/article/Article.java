package lenrek.data_crawling.domain.article;

import jakarta.persistence.*;
import lenrek.data_crawling.domain.Timestamped;
import lenrek.data_crawling.domain.company.Company;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
public class Article extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
    private Category category;

    @Column(nullable = false)
    private String summary;

    @Builder
    public Article(String title, String url, LocalDateTime publishedDate, Company company, Category category, String summary) {
        this.title = title;
        this.url = url;
        this.publishedDate = publishedDate;
        this.category = category;
        this.company = company;
        this.summary = summary;
    }
}
