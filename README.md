# springboot-urlMapping-shortener

SprintBoot URL Shortener 구현 미션 Repository 입니다.

# 요구사항

- [x]  URL 입력폼 제공 및 결과 출력
- [x]  URL Shortening Key는 8 Character 이내로 생성
- [x]  단축된 URL 요청 시 원래 URL로 리다이렉션
- [x]  `optional` 단축된 URL에 대한 요청 수 정보 저장
- [x]  `optional` Shortening Key를 생성하는 알고리즘 2개 이상 제공하며 애플리케이션 실행중 동적으로 변경 가능

# API 명세

### URL 입력 폼

| Method | URI |
|--------|-----|
| GET    | /   |

### 원본 URL을 단축 URL로 매핑

| Method | URI        |
|--------|------------|
| POST   | /shortener |

| Request Parameter | Type   | Description |
|-------------------|--------|-------------|
| originUrl         | String | 원본 URL      |

| Response Parameter | Type               | Description |
|--------------------|--------------------|-------------|
| mapping            | UrlMappingResponse | 매핑 결과       |

### 단축 URL에 대한 원본 URL로 리다이렉션

| Method | URI         |
|--------|-------------|
| GET    | /{shortUrl} |

| Path Variable | Type   | Description |
|---------------|--------|-------------|
| shortUrl      | String | 단축 URL      |

### URL 매핑 정보 조회

| Method | URI       |
|--------|-----------|
| GET    | /mappings |

| Response Parameter | Type                | Description |
|--------------------|---------------------|-------------|
| mappings           | UrlMappingResponses | 모든 매핑 정보    |
| pageInfo           | PageInfo            | 페이징 정보      | 

# DataBase 테이블

# 동작 화면
