## 자동 구성 라이브러리 만들기

 - MemoryAutoConfig에 @AutoConfiguration 추가
 - 자동 구성 대상 지정
   - 스프링 부트 자동 구성을 적용하려면, 다음 파일에 자동 구성 대상을 꼭 지정해주어야 함
   - src/main/resources/META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports
   - 위의 파일에 memory.MemoryAutoConfig 작성 해줌.
   - 스프링 부트는 시작 시점에 org.springframework.boot.autoconfigure.AutoConfiguration.imports 의 정보를 읽어서 자동 구성으로 사용한다
