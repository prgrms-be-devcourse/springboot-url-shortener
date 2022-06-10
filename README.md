# springboot-url-shortener

SprintBoot URL Shortener 구현 미션 Repository 입니다.

## Short URL Service

### 읽으면 좋은 레퍼런스

- [Naver 단축 URL API](https://developers.naver.com/docs/utils/shortenurl/)
- [짧게 줄인 URL의 실제 URL 확인 원리 및 방법](https://metalkin.tistory.com/50)
- [짧게 줄인 URL 알고리즘 고찰](https://metalkin.tistory.com/53)
- [단축 URL 원리 및 개발](https://blog.siyeol.com/26)

### Short URL의 동작 과정

예시로 bitly를 봅시다
![image1](./image1.png)
![image2](./image2.png)

1. 원본 URL을 입력하고 Shorten 버튼을 클릭합니다.
2. Unique Key를 7문자 생성합니다.
3. Unique Key와 원본 URL을 DB에 저장합니다.
4. bitly.com/{Unique Key} 로 접근하면, DB를 조회하여 원본 URL로 redirect합니다.

### Short URL의 특징

단축 URL서비스는 간편하지만, 단점(위험성)이 있습니다. 링크를 클릭하는 사용자는 단축된 URL만 보고 클릭하기 때문에 어떤 곳으로 이동할지 알 수 없습니다.

- Short URL 서비스는 주로 요청을 Redirect 시킵니다. (Redirect와 Forward의 차이점에 대해 검색해보세요.)
- 긴 URL을 짧은 URL로 압축할 수 있다.
- short url만으로는 어디에 연결되어있는 지 알 수 없다. 때문에 피싱 사이트 등의 보안에 취약하다.
- 광고를 본 뒤에 원본url로 넘겨주기도 한다. 이 과정에서 악성 광고가 나올 수 있다.
- 당연하지만 이미 존재하는 키를 입력하여 들어오는 사람이 존재할 수 있다.
- 기존의 원본 URL 변경되었더라도 단축 URL을 유지하여, 혼란을 방지할 수 있다.

### 예시 사이트

[https://url.kr/](https://url.kr/)

## URL - Shortener 강태산, 이한주

### 요구사항

각 요구사항을 모두 충족할 수 있도록 노력해봅시다.

- [x] URL 입력폼 제공 및 결과 출력
- [x] URL Shortening Key는 8 Character 이내로 생성
- [x] 단축된 URL 요청시 원래 URL로 리다이렉트
- [x] 단축된 URL에 대한 요청 수 정보저장 (optional)
- [ ] Shortening Key를 생성하는 알고리즘 2개 이상 제공하며 애플리케이션 실행중 동적으로 변경 가능 (optional)

### 페어 프로그래밍 룰

- 드라이버 변경정책
    - commit 단위
- commit 정책
    - 기능 단위 Issue 등록
    - Issus 단위 commit 수행
    - Issue 단위 branch를 분기
        - {type}/{description}
        - ex) feat/url-short, feat/ui
- 공유 방법
    - Intellij Code with me
    - Gather 화면 공유

### 백엔드 개발 환경

- Java 11
    - Java 8 보다 더 나은 퍼포먼스를 위해
        - [https://okky.kr/article/784365](https://okky.kr/article/784365)
        - [https://steady-coding.tistory.com/598](https://steady-coding.tistory.com/598)
- Gradle
- Spring Data JPA
- h2
- Spring Boot Starter Validation
    - [https://velog.io/@_koiil/SpringBoot-Spring-Validation을-이용한-유효성-검증](https://velog.io/@_koiil/SpringBoot-Spring-Validation%EC%9D%84-%EC%9D%B4%EC%9A%A9%ED%95%9C-%EC%9C%A0%ED%9A%A8%EC%84%B1-%EA%B2%80%EC%A6%9D)

### 프론트 개발 환경
- Html + JQuery

### Domain Rule

- Url은 프로토콜(http)을 제거한 후 저장한다.
    - www는 포함할 수 있다.
- Shortner URL은 index를 기준으로 생성.
  - 동일한 URL로 여러개 생성가능

    
### Database Schema
![image](https://user-images.githubusercontent.com/43159295/173018873-05ec9162-ee68-4901-8f7d-d4095388bd0b.png)

### API
![image](https://user-images.githubusercontent.com/43159295/173019052-8e022fed-2f24-4ae9-be5a-0cd13d9f4fbf.png)

### 결과 확인
![image](https://user-images.githubusercontent.com/43159295/173019245-28392a50-9c61-4c13-9a27-74d0c0e3fdf7.png)


