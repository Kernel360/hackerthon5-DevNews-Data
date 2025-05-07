package lenrek.data_crawling.domain.company;
import jakarta.persistence.*;
import lenrek.data_crawling.domain.Timestamped;

@Entity
public class Company extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String logo;
}
