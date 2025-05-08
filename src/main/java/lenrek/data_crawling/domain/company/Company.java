package lenrek.data_crawling.domain.company;
import jakarta.persistence.*;
import lenrek.data_crawling.domain.Timestamped;
import lombok.Getter;

@Entity
@Getter
public class Company extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String url;

    @Column(nullable = false)
    private String logo;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private type type;
}
