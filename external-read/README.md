## 외부 설정 사용 - Environment
다음과 같은 외부 설정들은 스프링이 제공하는 Environment 를 통해서 일관된 방식으로 조회할 수 있다.
 - 설정 데이터( application.properties )
 - OS 환경변수
 - 자바 시스템 속성
 - 커맨드 라인 옵션 인수

스프링은 Environment 는 물론이고 Environment 를 활용해서 더 편리하게 외부 설정을 읽는 방법들을 제공한다.
 - Environment
 - @Value - 값 주입
 - @ConfigurationProperties - 타입 안전한 설정 속성

#### 속성 변환기
Environment.getProperty(key, Type) 를 호출할 때 타입 정보를 주면 해당 타입으로 변환해준다.
 - env.getProperty("my.datasource.etc.max-connection", Integer.class) : 문자 -> 숫자로 변환
 - env.getProperty("my.datasource.etc.timeout", Duration.class) : 문자 -> Duration (기간) 변환
 - env.getProperty("my.datasource.etc.options", List.class) : 문자 -> List 변환 (A,B -> [A,B])

#### 외부설정 사용 - @Value
application.properties 에 필요한 외부 설정을 추가하고, Environment 를 통해서 해당 값들을 읽어서, MyDataSource 를 만들었다. 향후 외부 설정 방식이 달라져도, 예를 들어서 설정 데이터 ( application.properties )를 사용하다가 커맨드 라인 옵션 인수나 자바 시스템 속성으로 변경해도 애플리케이션 코드를 그대로 유지할 수 있다.
<br/>
이 방식의 단점은 Environment 를 직접 주입받고, env.getProperty(key) 를 통해서 값을 꺼내는 과정을 반복해야 한다는 점이다. <br/>
스프링은 @Value 를 통해서 외부 설정값을 주입 받는 더욱 편리한 기능을 제공한다. <br/>
물론 @Value도 Environment를 사용한다.

```
@Bean
 public MyDataSource myDataSource2(
 @Value("${my.datasource.url}") String url,
 @Value("${my.datasource.username}") String username,
 @Value("${my.datasource.password}") String password,
 @Value("${my.datasource.etc.max-connection}") int maxConnection,
 @Value("${my.datasource.etc.timeout}") Duration timeout,
 @Value("${my.datasource.etc.options}") List<String> options) {
 return new MyDataSource(url, username, password, maxConnection,
timeout, options);
 }
```

만약 키를 찾지 못할 경우 코드에서 기본값을 사용하려면 다음과 같이 : 뒤에 기본값을 적어주면 된다.
 - 예) @Value("${my.datasource.etc.max-connection:1}") : key 가 없는 경우 1 을 사용한다.

@Value 방식도 좋지만, 하나하나 일일이 키 값을 입력받고, 주입 받아와야하는 부분이 번거롭다. 또한 설정 데이터들도 하나하나 분리되어 있는 것이 아니라 정보의 묶음 이다. 객체로 변환하면 좋을 것이다.

#### 외부설정 사용 - @ConfigurationProperties
 - Type-safe Configuration Properties
 - 스프링은 외부 설정의 묶음 정보를 객체로 변환하는 기능을 제공한다.
 - 이것을 타입 안전한 설정 속성이라 한다.
 - 객체를 사용하면 타입을 사용할 수 있고, 실수로 잘못된 타입이 들어오는 문제도 방지할 수 있다.
 - @EnableConfigurationProperties(MyDataSourcePropertiesV1.class)
   - 스프링에게 사용할 @ConfigurationProperties 를 지정해주어야 한다
   - 이렇게 하면 해당 클래스는 스프링 빈으로 등록되고, 필요한 곳에서 주입 받아서 사용할 수 있다.
 - @ConfigurationProperties를 하나하나 등록할 때는 @EnableConfigurationProperties 사용한다.
 - @ConfigurationProperties 를 특정 범위로 자동 등록할 때는 @ConfigurationPropertiesScan 을 사용하면 된다.

```
@SpringBootApplication
@ConfigurationPropertiesScan({ "com.example.app", "com.example.another" })
public class MyApplication {}
```
 - application.properties 에 필요한 외부 설정을 추가하고, @ConfigurationProperties 의 생성자 주입을 통해서 값을 읽어들이면 Setter가 없으므로 개발자가 중간에 실수로 값을 변경하는 문제가 발생하지 않는다.

#### 외부설정 사용 - @ConfigurationProperties 검증
 - @ConfigurationProperties 를 통해서 숫자가 들어가야 하는 부분에 문자가 입력되는 문제와 같은 타입이 맞지 않는 데이터를 입력하는 문제는 예방할 수 있다. 그런데 문제는 숫자의 범위라던가, 문자의 길이 같은 부분은 검증이 어렵다.
 - @ConfigurationProperties 은 자바 객체이기 때문에 스프링이 자바 빈 검증기를 사용할 수 있도록 지원한다.
 - 자바 빈 검증기를 사용하려면 spring-boot-starter-validation 이 필요하다. build.gradle에 다음 코드를 추가한다.
```
implementation 'org.springframework.boot:spring-boot-starter-validation' //추가
```

```
@Getter
 public static class Etc {
   @Min(1)
   @Max(999)
   private int maxConnection;
   @DurationMin(seconds = 1)
   @DurationMax(seconds = 60)
   private Duration timeout;
   private List<String> options;

  public Etc(int maxConnection, Duration timeout, List<String> options) {
   this.maxConnection = maxConnection;
   this.timeout = timeout;
   this.options = options;
   }
 }
```
 - maxConnection은 1~999 사이의 값을 허용한다.
 - timeout은 1~60초를 허용한다.


## YAML
YAML(YAML Ain't Markup Language)은 사람이 읽기 좋은 데이터 구조를 목표로 한다. 확장자는 yaml , yml 이다. 주로 yml 을 사용한다.
<br/><br/>
application.properties 예시
```
my.datasource.url=local.db.com
my.datasource.username=local_user
my.datasource.password=local_pw
my.datasource.etc.max-connection=1
my.datasource.etc.timeout=3500ms
my.datasource.etc.options=CACHE,ADMIN
```
<br/>
application.yml 예시
<br/><br/>

```
my:
 datasource:
   url: local.db.com
   username: local_user
   password: local_pw
   etc:
     max-connection: 1
     timeout: 60s
     options: LOCAL, CACHE
```

application.properties , application.yml 을 같이 사용하면 application.properties 가 우선권을 가진다 둘 중 하나만 쓰자


