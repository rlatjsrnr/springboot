## 자동 구성 라이브러리 사용하기

 - project-v2/libs 폴더를 생성
 - memory-v2 프로젝트에서 빌드한 memory-v2.jar를 복사
 - project-v2/build.gradle 에 memory-v2.jar를 추가
```
dependencies {
 implementation files('libs/memory-v2.jar') //추가
 implementation 'org.springframework.boot:spring-boot-starter-web'
 compileOnly 'org.projectlombok:lombok'
 annotationProcessor 'org.projectlombok:lombok'
 testImplementation 'org.springframework.boot:spring-boot-starter-test'
}
```

 - 자동 구성 덕분에 빈 등록이나 추가 설정 없이 단순하게 라이브러이의 추가만으로 사용 가능

## 자동구성의 이해1 - 스프링부트의 동작
 - 스프링부트는 resources/META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports 파일을 읽어 자동 구성으로 사용한다.
 - 스프링 부트 자동 구성이 동작하는 원리는 다음 순서로 확인할 수 있다.
   1. @SpringBootApplication
   2. @EnableAutoConfiguration
   3. @Import(AutoConfigurationImportSelector.class)
 - @SpringBootApplication 어노테이션 안에 @EnableAutoConfiguration 어노테이션이 존재하고 이것은 자동 구성을 활성화하는 기능을 한다.
 - @EnableAutoConfiguration 어노테이션 안에 @Import(AutoConfigurationImportSelector.class) 가 존재하는데 이는 설정정보를 포함할 때 사용한다.
 - 그런데 AutoConfigurationImportSelector은 @Configuration이 아니다.

## 자동 구성의 이해2 - ImportSelector
@Import에 설정 정보를 추가하는 방법은 두 가지가 있다.
 - 정적인 방법: @Import (클래스) 이것은 정적이다. 코드에 대상이 딱 박혀 있다. 설정으로 사용할 대상을 동적으로 변경할 수 없다.
 - 동적인 방법: @Import ( ImportSelector ) 코드로 프로그래밍해서 설정으로 사용할 대상을 동적으로 선택할 수 있다.
<br/>
@AutoConfiguration도 설정 파일이다. 하지만 일반 스프링 설정과는 라이프사이클이 다르기 떄문에 컴포넌트스캔의 대상이 되어선 안된다.<br/>
그래서 스프링부트가 제공하는 컴포넌트스캔에는 @AutoConfiguration 을 제외하는 AutoConfigurationExcludeFilter 필터가 포함되어 있다.

```
@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan(excludeFilters = {
  @Filter(type = FilterType.CUSTOM, classes = TypeExcludeFilter.class),
  @Filter(type = FilterType.CUSTOM, classes = AutoConfigurationExcludeFilter.class)
})
public @interface SpringBootApplication {...}
```

