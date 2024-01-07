# springboot-url-shortener
SprintBoot URL Shortener 구현 미션 Repository 입니다.

## 📌 과제 설명
- [x] URL 입력폼 제공 및 결과 출력
- [x] URL Shortening Key는 8 Character 이내로 생성
- [x] 단축된 URL 요청시 원래 URL로 리다이렉트
- [x] 단축된 URL에 대한 요청 수 정보저장 (optional)
- [ ] Shortening Key를 생성하는 알고리즘 2개 이상 제공하며 애플리케이션 실행중 동적으로 변경 가능 (optional)


## 👩‍💻 고려한 점

* 단축 url 생성 알고리즘에서 고려한 점
    * 단축된 key 값은 유일해야 함
    * 숫자, 알파벳 대소문자를 사용할 것 (0-9, a-z, A-Z, 총 62자)
    * 매일 1억개의 단축 url 을 생성하여야 한다고 가정하면 초당 1160번의 쓰기 연산이 이루어짐
        * 1억 / 24 / 3600 = 1160
        * 10년간 운영한다고 가정하면 1억 * 365 * 10 = 3560억개의 레코드 보관
        * 62^7 = 약 3.5조이므로, 단축된 key의 길이는 7자리로 지정
    * 랜덤으로 7자리를 생성한 후 매번 db에 이미 존재하는 값인지 확인하여 충돌은 해소할 수 있으나, 데이터베이스 오버헤드 발생 가능

* 301 Permanently Moved vs 302 Found
    * 301 : 요청 처리 책임이 단축 URL로 영구적으로 이전되었다는 의미로, 브라우저 캐시에 저장된다. 따라서 첫번째 요청만 단축 URL 서버로 전송되므로, 방문자 수 추적이 어렵다
    * 302 : 클라이언트 요청은 언제나 단축 URL 서버에 먼저 보내진 후 원래 URL 로 리디렉션된다. 트래픽 분석이 중요하다면 302를 사용하는 것이 클릭 발생률이나 발생 위치를 추적하는 데 더 유리하다
    * 이 프로젝트에서는 302를 사용하였음

* 같은 원본 URL을 각각 다른 단축 URL로 단축할 수 있는 이유?
    * 실제로 서비스 중인 단축 URL 사이트를 살펴보면, 같은 원본 URL을 입력해도 각각 다른 단축 URL을 생성할 수 있음을 알 수 있다
    * 동일한 원본 URL이라도, 다른 단축 URL을 생성하여 각각의 링크가 어디에서 사용되고, 얼마나 클릭되는지 추적할 수 있음
    * 예를 들어, 같은 웹 페이지 링크를 소셜 미디어, 이메일 캠페인, 광고 등 다양한 채널에 사용할 때 각각 다른 단축 URL을 생성하여 각 채널의 효과를 별도로 분석할 수 있다


참고도서 : 가상 면접 사례로 배우는 대규모 시스템 설계 기초, 알렉스 쉬 


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
단축 URL서비스는 간편하지만, 단점(위험성)이 있습니다. 
링크를 클릭하는 사용자는 단축된 URL만 보고 클릭하기 때문에 어떤 곳으로 이동할지 알 수 없습니다.

- Short URL 서비스는 주로 요청을 Redirect 시킵니다. (Redirect와 Forward의 차이점에 대해 검색해보세요.)
- 긴 URL을 짧은 URL로 압축할 수 있다.
- short url만으로는 어디에 연결되어있는 지 알 수 없다. 때문에 피싱 사이트 등의 보안에 취약하다.
- 광고를 본 뒤에 원본url로 넘겨주기도 한다. 이 과정에서 악성 광고가 나올 수 있다.
- 당연하지만 이미 존재하는 키를 입력하여 들어오는 사람이 존재할 수 있다.
- 기존의 원본 URL 변경되었더라도 단축 URL을 유지하여, 혼란을 방지할 수 있다.

### 예시 사이트
[https://url.kr/](https://url.kr/)
