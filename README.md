# springboot-url-shortener
**Marco**팀 SprintBoot URL Shortener 구현 미션 Repository 입니다.  

<br/>

## 과제 설명
- [x] URL 입력폼 제공 및 결과 출력
    - 생성된 단축 url은 페이지에서 리스트로 확인 가능
    - 기존에 생성되었던 리스트는 중복 생성 x
    - 생성된 url은 복사 버튼으로 복사 가능
    - clear 버튼으로 생성된 shortUrl 모두 삭제 가능
- [x] URL Shortening Key는 8 Character 이내로 생성
    - Auto Increment로 생성된 id에 Base62를 적용해 UniqueKey 생성
- [x] 단축된 URL 요청시 원래 URL로 리다이렉트


<br/>


## 사진 
<img src="https://github.com/spring-comes-to-us/springboot-url-shortener/assets/85065626/58732961-84a8-4311-9d72-b692de85fe40" width=45%>
<img src="https://github.com/spring-comes-to-us/springboot-url-shortener/assets/85065626/692a8699-9ac4-45aa-8f7a-af4e4d793311" width=45%>

<br/>

## 시연 영상
https://github.com/spring-comes-to-us/springboot-url-shortener/assets/85275893/0105fbc2-f539-4b05-925e-46c49d8c1ae0

<br/>

## 팀원 소개
<table>
    <tr align="center">
        <td><B>Backend</B></td>
        <td><B>Backend</B></td>
        <td><B>Backend</B></td>
        <td><B>Backend</B></td>
        <td><B>Backend</B></td>
        <td><B>Backend</B></td>
    </tr>
    <tr align="center">
        <td><a href="https://github.com/ASak1104">김현우(Harry)</a></td>
        <td><a href="https://github.com/IjjS">송인재</a></td>
        <td><a href="https://github.com/yenzip">엄예림</a></td>
        <td><a href="https://github.com/Sehee-Lee-01">이세희</a></td>
        <td><a href="https://github.com/shoeone96">이중원</a></td>
        <td><a href="https://github.com/uijin-j">정의진</a></td>
    </tr>
    <tr align="center">
        <td>
            <img src="https://github.com/ASak1104.png?size=100">
        </td>
        <td>
            <img src="https://github.com/IjjS.png?size=100" width=100px>
        </td>
        <td>
            <img src="https://github.com/yenzip.png?size=100">
        </td>
        <td>
            <img src="https://github.com/Sehee-Lee-01.png?size=100">
        </td>
        <td>
            <img src="https://github.com/shoeone96.png?size=100">
        </td>
        <td>
            <img src="https://github.com/uijin-j.png?size=100" width=100px>
        </td>
    </tr>
</table>

<br/>


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
