package lenrek.data_crawling.domain.company;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum type {
    BLOG("테크 블로그"),
    NEWS("테크 뉴스");

    private final String value;
}
