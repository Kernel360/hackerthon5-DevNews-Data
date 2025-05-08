package lenrek.data_crawling.domain.category;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CategoryEnum {
    BACKEND("백엔드"),
    WEB("웹 프론트엔드"),
    CLOUD_INFRA("클라우드 / 인프라"),
    DATA_ANALYSIS("데이터 분석"),
    AI("AI / 머신러닝"),
    DEVOPS("DevOps / 툴링"),
    MOBILE("모바일"),
    PROGRAMMING("언어 / 프로그래밍"),
    SECURITY("보안"),
    CULTURE("개발 문화 / 커리어");

    private final String value;
}
