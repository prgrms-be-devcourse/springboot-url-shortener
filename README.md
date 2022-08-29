# springboot-url-shortener
SprintBoot URL Shortener 구현 미션 Repository 입니다.

- 초기 화면

![image](https://user-images.githubusercontent.com/58356031/187134050-e1a72229-08d8-41bc-8770-10429cbb3021.png)

ex) `www.naver.com` , `https://www.google.com` 등 http가 앞에 생략되어도 자동으로 붙여줌


- 만들고 싶은 url을 넣고 shorturl로 변환

![image](https://user-images.githubusercontent.com/58356031/187133991-4883f51d-b8e5-412d-bdad-39ce89132600.png)

<br/><br/>

- shortUrl은 Base58을 사용하여 랜덤으로 생성

- 이미 변환한적이 있는 url이 입력되면 변환했던 url로 반환

