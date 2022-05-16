# springboot-url-shortener
SprintBoot URL Shortener 구현 미션 Repository 입니다.

## Short URL Service

### 요구사항
각 요구사항을 모두 충족할 수 있도록 노력해봅시다.
- [x] URL 입력폼 제공 및 결과 출력
- [x] URL Shortening Key는 8 Character 이내로 생성
- [x] 단축된 URL 요청시 원래 URL로 리다이렉트
- [x] 단축된 URL에 대한 요청 수 정보저장 (optional)
- [ ] Shortening Key를 생성하는 알고리즘 2개 이상 제공하며 애플리케이션 실행중 동적으로 변경 가능 (optional) 

### [Short URL REST API 사용 명세서](https://github.com/y005/springboot-url-shortener/blob/main/src/doc/index.pdf)

### Short URL 웹서비스의 동작 과정
1. 원본 URL을 입력하고 Shorten 버튼을 클릭합니다.
   ![](https://velog.velcdn.com/images/y005/post/9f5d4795-7d9a-4e68-8924-376e961f6672/image.png)
2. Unique Key를 8문자 생성하여 Unique Key와 원본 URL을 DB에 저장합니다.
   ![](https://velog.velcdn.com/images/y005/post/fb3b4a56-9b21-4922-9e00-db4556d8cbd7/image.png)
3. localhost:8080/{Unique Key} 로 접근하면, DB를 조회하여 원본 URL로 redirect합니다.
   ![](https://velog.velcdn.com/images/y005/post/70d0fe5e-8695-4e45-8d32-ed9c6b66987e/image.png)
4. DashBoard에서 short url의 사용 횟수가 증가합니다.
    ![](https://velog.velcdn.com/images/y005/post/4b7631e1-23cf-4f12-82d3-e8a4bbecfe42/image.png)
### Short URL의 특징
단축 URL서비스는 간편하지만, 단점(위험성)이 있습니다. 
링크를 클릭하는 사용자는 단축된 URL만 보고 클릭하기 때문에 어떤 곳으로 이동할지 알 수 없습니다.

- Short URL 서비스는 주로 요청을 Redirect 시킵니다. (Redirect와 Forward의 차이점에 대해 검색해보세요.)
- 긴 URL을 짧은 URL로 압축할 수 있다.
- short url만으로는 어디에 연결되어있는 지 알 수 없다. 때문에 피싱 사이트 등의 보안에 취약하다.
- 광고를 본 뒤에 원본url로 넘겨주기도 한다. 이 과정에서 악성 광고가 나올 수 있다.
- 당연하지만 이미 존재하는 키를 입력하여 들어오는 사람이 존재할 수 있다.
- 기존의 원본 URL 변경되었더라도 단축 URL을 유지하여, 혼란을 방지할 수 있다.
---
### 읽으면 좋은 레퍼런스
- [Naver 단축 URL API](https://developers.naver.com/docs/utils/shortenurl/)
- [짧게 줄인 URL의 실제 URL 확인 원리 및 방법](https://metalkin.tistory.com/50)
- [짧게 줄인 URL 알고리즘 고찰](https://metalkin.tistory.com/53)
- [단축 URL 원리 및 개발](https://blog.siyeol.com/26)

### 예시 사이트
[https://url.kr/](https://url.kr/)
