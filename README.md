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




## ✅ 고민, 이슈

## ✅ 개선점

## ✅ 느낀점
### 배운 것
### 페어 프로그래밍하며 느낀점
### 개인적으로 느낀점
