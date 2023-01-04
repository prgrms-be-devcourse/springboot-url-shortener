# springboot-url-shortener

## 요구사항
- URL 입력폼 제공 및 결과 출력
- URL Shortening Key는 8 Character 이내로 생성
- 단축된 URL 요청시 원래 URL로 리다이렉트
- 단축된 URL에 대한 요청 수 정보저장 (optional)

### API 문서
#### 1. URL 입력폼 화면 제공
| 메서드 | URL
:---: | :---: |
GET | /
---
#### 2. 단축된 URL 생성
| 메서드 | URL
:---: | :---:
POST | /shortener

| Response Parameter | 타입 | 설명
:---: | :---: | :---:
originUrl | String | 원래 URL
shortUrl | String | 단축된 URL
requestCount | Number | 요청 수
---
#### 3. 생성된 상세 결과 페이지 제공 (원래 URL, 단축된 URL, 요청 수)
| 메서드 | URL
:---: | :---:
GET | /shortener

| Request Parameter | 타입 | 설명
:---: | :---: | :---:
originUrl | String | 원래 URL
shortUrl | String | 단축된 URL
requestCount | Number | 요청 수
---
#### 4. 단축된 URL 로 요청 시 원래 URL 로 리다이렉트
| 메서드 | URL
:---: | :---:
GET | /{shortUrl}

| Path Parameter | 타입 | 설명
:---: | :---: | :---:
shortUrl | String | 단축된 URL
---
### DB 테이블
| 컬럼 명 | 타입 | NULL 가능 | 키 | 설명
:---: | :---: | :---: | :---: | :---:
id | bigint | x | pk | ID 값
algorithm | varchar(20) | x | uk| 알고리즘
origin_url | varchar(2000) | x | uk | 원래 URL
short_url | varchar(7) | o | - |단축된 URL
request_count | bigint | x | - | 요청 수
---

<br>

## 동작 화면

### 메인화면

![image](https://user-images.githubusercontent.com/68289543/210596993-25fbb2b4-a38b-4087-b5f1-415c6a27cde9.png)

### Short URL 생성

![image](https://user-images.githubusercontent.com/68289543/210597113-3699b954-aacf-462f-a0c8-2142e7b50a5c.png)

### Short URL 생성 - 세부 정보 화면

![image](https://user-images.githubusercontent.com/68289543/210597413-b539d311-b3ca-4b4c-b58e-60850e78bf13.png)

### Short URL 생성 - 클릭 시 원본 URL로 이동

![gif-1](https://user-images.githubusercontent.com/68289543/210597475-b834e773-a70b-4e09-8223-6dab364fed13.gif)

### Short URL 생성 - 이미 DB에 존재하는 URL인 경우 DB에서 조회 후 count 값 증가

![git-2](https://user-images.githubusercontent.com/68289543/210597554-3629dc02-82dd-4671-9f61-7579abe84338.gif)

### Short URL 생성 실패 - 잘못된 URL을 입력하는 경우 예외 처리

![GIF-3](https://user-images.githubusercontent.com/68289543/210597608-88e5503d-7054-4205-b488-5294c24675c3.gif)

### 잘못된 입력 요청을 한 경우

![gif-4](https://user-images.githubusercontent.com/68289543/210597617-666ca514-20e2-4d31-b0ec-798750d9aad8.gif)

<br>

## 이슈, 고민
1. 컨트롤러에서 사용자 입력 파싱할 때 어떤 방식을 사용할지
    1. 사용자 입력으로 원본 URL을 받을 때 해당 URL의 프로토콜(http://, https://)을 제거한 후 서비스 레이어로 보내고 싶었다(DB에 저장할 때 http://, https:// 유무에 따라 같은 도메인인데도 다르게 저장되는 것을 방지하기 위해). 하지만 컨트롤러에서 사용자로부터 값을 받는 DTO가 record였기 때문에 값을 수정할 수 없었다. 모든 DTO를 record로 만들었는데 해당 DTO만 class로 바꿔야 하는지 아니면 Service 전용 DTO를 하나 더 만들어야 하는지 고민이 되었다. 결론은 Service용 DTO를 하나 더 만드는 방식을 사용하기로 했다. Service 전용 DTO를 하나 더 만드는 것이 서비스 메소드가 컨트롤러 메소드에 종속되지 않도록하는 이점도 있다고 생각했기 때문이다.
2. 문자열 압축 방식 Base64 vs Base62
    1. 처음에는 두 문자열 압축 방식을 사용자에게 정하도록 하려했으나 Base64에서 특수문자를 제거한 Bsae62 방식이 더 URL-Safe한 방식이라고 판단하여 Base62 인코딩 방식을 사용하기로 결정하였다.

<br>

## 개선점
- 앞서 기록한 1번 고민을 컨버터(Converter)를 사용하여 개선해보기
- 적절한 로그 처리하기
    - 프로젝트를 진행하다가 오류가 발생했을 때 어느 시점에서 발생했는지 정확하게 파악하기 어려웠다. 적절한 로그를 작성해서 디버깅을 더 효율적으로 할 수 있도록 개선해야겠다.
