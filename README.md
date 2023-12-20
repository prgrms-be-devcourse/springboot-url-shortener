# springboot-url-shortener

## 요구사항

- [x] URL 입력폼 제공 및 결과 출력
- [x] URL Shortening Key는 8 Character 이내로 생성
- [x] 단축된 URL 요청시 원래 URL로 리다이렉트
- [x] 단축된 URL에 대한 요청 수 정보저장 (optional)
- [ ] Shortening Key를 생성하는 알고리즘 2개 이상 제공하며 애플리케이션 실행중 동적으로 변경 가능 (optional)

### Short URL의 동작 과정

1. 원본 URL을 입력하고 Shorten 버튼을 클릭합니다.
2. Unique Key를 7문자 생성합니다.
3. Unique Key와 원본 URL을 DB에 저장합니다.
4. bitly.com/{Unique Key} 로 접근하면, DB를 조회하여 원본 URL로 redirect합니다.
