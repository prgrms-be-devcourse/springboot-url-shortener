# springboot-url-shortener

SprintBoot URL Shortener 구현 미션 Repository 입니다.


![short_url](https://user-images.githubusercontent.com/74031333/173844700-fb205a8d-2bae-4824-aad5-61f90a9ce9f5.gif)

## 💡 요구사항

각 요구사항을 모두 충족할 수 있도록 노력해봅시다.

- [x] URL 입력폼 제공 및 결과 출력
- [x] URL Shortening Key는 8 Character 이내로 생성
- [x] 단축된 URL 요청시 원래 URL로 리다이렉트

## PR 포인트 & 궁금한 점

### URL 검증
저는 사용자가 입력하는 URL이 제대로 된 URL인지 애초에 연결이 되는 URL인지 확인해야 한다고 생각했습니다.
처음에 URL의 형식인지 확인하기 위해서 정규표현 식을 사용해서 모든 URL의 형식을 검사해보려고 했지만 복잡한 URL을 검사에 계속 실패했습니다.
그래서 결국에는 Http(s):// 의 형식만 검사하고 입력한 URL에 이 형식이 없거나 만족하지 않는다면 "http://" 을 replace 하여 만들어진 URL을 다시 접속해보는 식으로 URL을 검사했습니다.
실제로 URL을 검사하거나 그 형식을 찾는 경우에는 어떤 방식을 사용하는지 궁금합니다!

### 같은 URL의 다른 short URL
저는 이 과제를 하면서 bit.ly를 직접 사용해 보았습니다.
bit.ly에서는 같은 URL을 입력해도 매번 다른 short URL을 만들어 주었고 여러 개 만든 short URL을 다 접속해봐도 원래의 URL로 잘 접속이 가능했습니다.
이번에 저도 이런 방식을 사용해보려고 했지만, 굳이 이렇게 안 하고 한 번 만든 URL을 계속 사용하는 게 더 낫다고 생각하게 되었습니다.
bit.l 처럼ont id='ul_11' color='red' class='ul' onclick='fShowHelp(11)'>처럼 같은 URL을 요청하면 여러 개의 다른 short URL을 만들어 주는 것이 맞는 것인지 궁금합니다.



