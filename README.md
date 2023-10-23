# springboot-url-shortener
<!--
  템플릿은 아직 PR 작성이 익숙하지 않으신 분들을 위해서 제공하는 가이드입니다!
  리뷰어 또는 이 PR을 보게 될 다른 사람들이 이 PR을 보는데 참고할 수 있는 내용이 있다면 포함해서 작성해주시면 됩니다.
-->


사용자가 입력한 url에 대해 더 짧은 형태인 단축 url을 제공하는 서비스 입니다



- URL 입력폼 제공 및 결과
- URL Shortening Key는 8 Character 이내로 생성
- 단축된 URL 요청시 원래 URL로 리다이렉트
- 단축된 URL에 대한 요청 수 정보저장
- Shortening Key를 생성하는 알고리즘 2개 이상 제공하며 애플리케이션 실행중 동적으로 변경 가능
	- (1) Base62
	- (2) 인덱스 번호 자체를 활용
- IP 별 요청 횟수 제한 : Rate Limit Algorithm(Bucket4j) 사용
- 배포 :~~https://shortenworld.kro.kr~~

https://github.com/prgrms-be-devcourse/springboot-url-shortener/assets/49016275/ee090c94-0845-46f3-a5ab-d3b8b652b9a0



- 초기 화면
<img width="400" alt="Screenshot 2023-10-09 at 2 53 20 AM" src="https://github.com/prgrms-be-devcourse/springboot-url-shortener/assets/49016275/766f2c13-1f2e-421d-80ce-bb8b26f383b6">

- 링크 단축하기 / 통계 버튼 클릭시
<img width="400" alt="Screenshot 2023-10-09 at 2 53 39 AM" src="https://github.com/prgrms-be-devcourse/springboot-url-shortener/assets/49016275/081437bc-dea4-4264-bf44-6df0a44deb6f">
<img width="400" alt="Screenshot 2023-10-09 at 2 53 47 AM" src="https://github.com/prgrms-be-devcourse/springboot-url-shortener/assets/49016275/c7151b3e-4c8b-4688-a064-f740f8a6e8be">

- 유효하지 않은 단축 url로 접속한 경우
<img width="400" alt="Screenshot 2023-10-09 at 11 13 27 PM" src="https://github.com/prgrms-be-devcourse/springboot-url-shortener/assets/49016275/f711bd00-569b-4196-9038-e8ee1b7d6b63">


- 같은 IP로 너무 많은 요청을 보낸 경우
<img width="400" alt="Screenshot 2023-10-09 at 10 56 30 PM" src="https://github.com/prgrms-be-devcourse/springboot-url-shortener/assets/49016275/f3c379d3-2eb3-4c6a-b698-31c2638a619c">

