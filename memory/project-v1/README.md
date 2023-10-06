## 순수 라이브러리 사용하기

 - 라이브러리 추가
   - project-v1/libs 폴더를 생성
   - memory-v1 프로젝트에서 빌드한 memory-v1.jar 를 이곳에 복사
   - project-v1/build.gradle 에 memory-v1.jar 를 추가
```
dependencies {
 implementation files('libs/memory-v1.jar') //추가
 implementation 'org.springframework.boot:spring-boot-starter-web'
 compileOnly 'org.projectlombok:lombok'
 annotationProcessor 'org.projectlombok:lombok'
 testImplementation 'org.springframework.boot:spring-boot-starter-test'
}
```

 - Auto Configuration 사용하지 않고 직접 빈 등록
   - MemoryConfig 에 사용할 빈들을 등록 해 줌

#### 정리
 - 외부 라이브러리를 직접 만들고 또 그것을 프로젝트에 라이브러리로 불러서 적용해보았다.
 - 라이브러리를 사용하는 클라이언트 개발자 입장을 생각해보면, 라이브러리 내부에 있는 어떤 빈을 등록해야하는지 알아야 하고, 그것을 또 하나하나 빈으로 등록해야 한다. 지금처럼 간단한 라이브러리가 아니라 초기 설정이 복잡하다면 사용자 입장에서는 상당히 귀찮은 작업이 될 수 있다.
 - 이런 부분을 자동으로 처리해주는 것이 바로 스프링 부트 자동 구성(Auto Configuration)이다
