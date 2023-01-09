## 💻 프로젝트 환경

- Java 17
- SpringBoot 2.7.7
- H2
- Spring Data JPA
- Gradle

---

## 👩‍ 구현 내용

### 기능

- [x] URL 입력폼 제공
- [x] 상세 결과 페이지(원래 URL, 단축된 URL, 요청 수) 제공
- [x] 잘못된 URL 을 단축 요청 시 사용자에게 오류 표시
- [x] 단축된 URL 은 7 Character 로 생성
- [x] 단축된 URL 요청시 원래 URL 로 리다이렉트

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
id | bigint | x | pk | Auto increment 로 생성한 ID
algorithm | varchar(20) | x | uk| URL 단축에 사용한 알고리즘
origin_url | varchar(2000) | x | uk | 프로토콜 정보를 제외한 원래 URL
short_url | varchar(7) | o | - | 단축된 URL
request_count | bigint | x | - | 요청 수

---

### 동작 화면

#### URL 입력폼 화면

![image](https://user-images.githubusercontent.com/68289543/210596993-25fbb2b4-a38b-4087-b5f1-415c6a27cde9.png)

#### URL 입력폼 화면 - 단축된 URL 생성 요청

![image](https://user-images.githubusercontent.com/68289543/210597113-3699b954-aacf-462f-a0c8-2142e7b50a5c.png)

#### 단축된 URL 생성 요청 후 상세 결과 페이지

![image](https://user-images.githubusercontent.com/68289543/210597413-b539d311-b3ca-4b4c-b58e-60850e78bf13.png)

#### 상세 결과 페이지 - 단축된 URL 클릭 시 원본 URL 로 이동

![gif-1](https://user-images.githubusercontent.com/68289543/210597475-b834e773-a70b-4e09-8223-6dab364fed13.gif)

#### 이미 DB에 존재하는 URL 인 경우 요청 수 증가

![git-2](https://user-images.githubusercontent.com/68289543/210597554-3629dc02-82dd-4671-9f61-7579abe84338.gif)

#### 잘못된 URL 을 입력하는 경우 사용자에게 오류 표시

![GIF-3](https://user-images.githubusercontent.com/68289543/210597608-88e5503d-7054-4205-b488-5294c24675c3.gif)

#### DB 에 없는 단축된 URL 요청을 한 경우 에러 페이지 이동

![gif-4](https://user-images.githubusercontent.com/68289543/210597617-666ca514-20e2-4d31-b0ec-798750d9aad8.gif)
---

## ✅ 고민, 이슈

## ✅ 개선점
