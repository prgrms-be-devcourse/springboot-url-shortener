# springboot-url-shortener
SprintBoot URL Shortener 구현 미션 Repository 입니다.

### 프로젝트 개요

---
- URL을 짧은 URL로 변환시켜주는 웹 서비스 클론 프로젝트입니다.
- URL을 입력하면 7자의 단축된 URL을 만들어서 반환합니다.
- 변환한 적이 있는 URL이라면 변환되었던 URL을 반환합니다.

<br/>

### 주요 내용

---

- POST "/api/v1"로 변환시켜주고 싶은 URL을 입력합니다.
- BASE58을 이용하여 고유의 7자의 URL을 반환해 줍니다.
- GET "/api/v1/{변환한 url}"로 요청을 보내면 변환했던 원본 URL로 리다이렉트 합니다. 

<br/>

### 자료 화면

---
- 초기 화면

![image](https://user-images.githubusercontent.com/58356031/187134050-e1a72229-08d8-41bc-8770-10429cbb3021.png)

ex) `www.naver.com` , `https://www.google.com` 등 http가 앞에 생략되어도 자동으로 붙여줌


- 만들고 싶은 url을 넣고 shorturl로 변환

![image](https://user-images.githubusercontent.com/58356031/187133991-4883f51d-b8e5-412d-bdad-39ce89132600.png)

<br/><br/>

- shortUrl은 Base58을 사용하여 랜덤으로 생성

- 이미 변환한적이 있는 url이 입력되면 변환했던 url로 반환

