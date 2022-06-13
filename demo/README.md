# springboot-url-shortener
SprintBoot URL Shortener 구현 미션 Repository 입니다.

## 기능구현사항
- [X] URL 입력폼 제공 및 결과 출력
- [X] URL Shortening Key는 8 Character 이내로 생성
- [X] 단축된 URL 요청시 원래 URL로 리다이렉트
- [ ] 단축된 URL에 대한 요청 수 정보저장 (optional)
- [ ] Shortening Key를 생성하는 알고리즘 2개 이상 제공하며 애플리케이션 실행중 동적으로 변경 가능 (optional)

## 동작과정
1. 원본 URL을 입력하고 `Shorten URL` 버튼을 누릅니다.
2. 기존에 요청온적없는 새로운 URL이라면 DB에 저장 후 Short URL을 생성합니다. 
3. Short URL을 누르면 원본 URL로 redirect 합니다.