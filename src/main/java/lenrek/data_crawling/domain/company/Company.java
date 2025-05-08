package lenrek.data_crawling.domain.company;
import jakarta.persistence.*;
import lenrek.data_crawling.domain.support.BaseEntity;
import lombok.Getter;

@Entity
@Getter
public class Company extends BaseEntity {

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
