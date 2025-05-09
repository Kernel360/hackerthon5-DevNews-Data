# DevNews Data Crawling Service

## 프로젝트 소개
DevNews Data Crawling Service는 기술 블로그의 RSS 피드를 수집하고, AI를 활용하여 콘텐츠를 분석하고 분류하는 서비스입니다. 이 서비스는 다음과 같은 주요 기능을 제공합니다:

- RSS 피드 자동 수집
- AI 기반 콘텐츠 요약
- AI 기반 콘텐츠 분류
- 데이터베이스 저장 및 관리

## 기술 스택
- Java 17
- Spring Boot 3.4.5
- Spring Data JPA
- MySQL
- ROME (RSS 피드 파싱)
- JSoup (HTML 파싱)
- Upstage AI API (Solar-Pro 모델)

## 주요 기능

### 1. RSS 피드 크롤링
- 매일 자동으로 설정된 시간에 RSS 피드를 수집
- 최대 20개의 최신 게시물 수집
- 중복 게시물 방지

### 2. AI 기반 콘텐츠 분석
- 텍스트 전처리
- AI 기반 요약 생성 (최대 3문장)
- AI 기반 카테고리 분류

### 3. 데이터 관리
- 회사 정보 관리
- 카테고리 관리
- 게시물 정보 저장

## 프로젝트 구조

### 패키지 구조
```
src/main/java/lenrek/data_crawling/
├── DataCrawlingApplication.java    # 애플리케이션 진입점
│
├── domain/                         # 도메인 모델
│   ├── article/                   # 게시물 관련 엔티티
│   ├── category/                  # 카테고리 관련 엔티티
│   ├── company/                   # 회사 정보 관련 엔티티
│   └── support/                   # 공통 엔티티 (BaseEntity 등)
│
├── repository/                     # 데이터 접근 계층
│   ├── article/                   # 게시물 관련 레포지토리
│   ├── category/                  # 카테고리 관련 레포지토리
│   └── company/                   # 회사 정보 관련 레포지토리
│
├── service/                        # 비즈니스 로직
│   ├── ai/                        # AI 관련 서비스
│   │   ├── ClassificationService.java
│   │   ├── PipelineService.java
│   │   ├── SummarizationService.java
│   │   └── TextPreprocessingService.java
│   └── article/                   # 게시물 관련 서비스
│       └── RssCrawlerService.java
│
└── global/                         # 전역 설정
    └── config/                    # 설정 클래스
        └── RestClientConfig.java  # REST 클라이언트 설정
```

### 주요 클래스 설명

#### 도메인 (Domain)
- `Article`: 게시물 정보를 담는 엔티티
- `Category`: 콘텐츠 카테고리 정보
- `Company`: 회사 정보 (RSS 피드 URL 포함)
- `BaseEntity`: 공통 엔티티 필드 (ID, 생성/수정 시간)

#### 서비스 (Service)
- `RssCrawlerService`: RSS 피드 수집 및 처리
- `PipelineService`: AI 처리 파이프라인 관리
- `ClassificationService`: AI 기반 콘텐츠 분류
- `SummarizationService`: AI 기반 콘텐츠 요약
- `TextPreprocessingService`: 텍스트 전처리

#### 설정 (Configuration)
- `RestClientConfig`: Upstage AI API 클라이언트 설정

## 설정
프로젝트를 실행하기 위해서는 다음 설정이 필요합니다:

1. `application.yaml` 파일에 다음 설정이 필요합니다:
   - MySQL 데이터베이스 연결 정보
   - Upstage AI API 키 및 URL

2. 필요한 환경 변수:
   - `upstage.api-key`: Upstage AI API 키
   - `upstage.api-url`: Upstage AI API URL
