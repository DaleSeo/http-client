# HTTP Client
> HTTP API 호출을 용이하다록 도와주는 HTTP Client 모듈

## 요구사항
- HTTP API를 호출할 때, 매번 세팅하는 인증/공통 헤더들이 자동으로 세팅되었으면...
    - 세팅되는 헤더 값들이 런타임에 바꿀 수 있었으면...
- Spring 프레임워크를 안 쓰는 어플리케이션도 사용할 수 있도록...
- 클라이언트 시스템 입장에서 관심이 없거나 처리가 까다로운 다음 작업들을 대신 해주었으면...

## 주요 기능

- 인증 헤더 세팅
: 인증을 위한 필수 헤더 세팅
- 공통 헤더 세팅
: 매 요청마다 세팅해야하는 공통 헤더에 대한 보일러 템필릿 코드 제거
- HTTP 요청/응답 전문과 Java 객체간 컨버팅
: 요청 Java 객체를 요청 전문으로 변환하고, 응답 전문을 Java 객체로 변환
- 오류 처리
: SAC API 호출 과정에서 예외 발생 시 프레임워크 표준 에러 타입(StorePlatformException)로 변환하여 Throw

## 주요 기능
- 특정 연동 기술(Apache HttpClient, Java HttpConnection, Spring RestTemplate)에 의존하지 않음
- Generic을 통해 좀 더 Type Safe하게 구현
- Spring RestTemplate 설정을 좀 더 안전하게 쉽게
- 요청 정보, 응답 정보 로깅 및 설정 커스터마이징


## 주요 인터페이스
- Base URL 해결사 인터페이스
    - HTTP API 호출 시 사용할 Base URL을 HTTP 연동 클라이언트(HttpClient)에게 알려줌
    - 3개의 구현체(FileBaseUrlResolver, FixedBaseUrlResolver, MemoryBaseUrlResolver)를 제공
    - Base URL을 결정하는 로직이 복잡할 경우, 서브 시스템에서 직접 구현 요망
-  Header 해결사 인터페이스
    - SAC API 호출 시 세팅될 Header 값들을 HTTP 연동 클라이언트(HttpClient)에게 알려줌
    - 2개의 구현체(FileHeaderResolver, FixedHeaderResolver)를 제공
    - Header 값을 구하는 로직이 복잡할 경우, 서브 시스템에서 직접 구현 요망

## 작동 매커니즘
- HttpClient 인터페이스의 get과 post 메소드는 SAC API 호출을 위해 런타임에 변경되야 할 최소한의 인자(인터페이스 아이디, API 경로, 응답 전문 타입, 요청 전문 객체 또는 쿼리 객체)만 받는다.
- 나머지 런타임에 바뀌지 않는 공통적인 요소들 (공통 헤더 세팅, 인증 헤더 세팅, 쿼리 스트링 조합, 최종 URL 건설) 등은 모듈 내부에서 처리한다.
- 모듈 내부에서 공통적인 요소들을 구해내기 위해서 서브 시스템에서는 2개의 해결사 인터페이스(BaseUrlResolver와 HeaderResolver)의 구현체를 HttpClient의 생성자를 통해 제공해줘야 한다.
- 더욱 상세한 요청 정보를 인자로 받는 HttpClient 인터페이스의 exchange 메소드를 통해서 위에서 세팅한 공통적인 요소들은 모두 덮어쓰기가 가능하다.