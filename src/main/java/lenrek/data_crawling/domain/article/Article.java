package lenrek.data_crawling.domain.article;

import jakarta.persistence.*;
import lenrek.data_crawling.domain.Timestamped;
import lenrek.data_crawling.domain.company.Company;

import java.time.LocalDateTime;

@Entity
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
    @JoinColumn(name = "source")
    private Company companyId;
}
