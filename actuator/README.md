## 액츄에이터
 - 프로덕션 준비 기능
   - 서비스는 언제든 장애가 발생할 수 있다. 이를 모니터링하는 것이 중요하다.
   - 개발자는 어플리케이션을 개발할 때 요구사항만 개발하는 것이 아니다.
   - 다른 중요한 업무는 서비스에 문제가 없는지 모니터링하고 지표를 심어 감시하는 활동이다.
   - 이를 프로덕션 준비 기능이라 한다.
   - 지표(metric), 추적(trace), 감사(auditing), 모니터링
   - 스프링부트가 제공하는 엑츄에이터는 이 프로덕션 준비 기능으로 매우 편리하게 사용할 수 있도록 다양한 편의기능을 제공한다.
   - 마이크로미터, 프로메테우스, 그라파나와 같은 최근 유행하는 모니터링 시스템과 쉽게 연동할 수 있는 기능도 제공한다

Spring Boot Actuator 라이브러리 추가
```
implementation 'org.springframework.boot:spring-boot-starter-actuator' 
```

액츄에이터는 /actuator 경로를 통해서 기능을 제공한다.
```
{
 "_links": {
   "self": {
     "href": "http://localhost:8080/actuator",
     "templated": false
 },
 "health-path": {
   "href": "http://localhost:8080/actuator/health/{*path}",
   "templated": true
 },
 "health": {
   "href": "http://localhost:8080/actuator/health",
   "templated": false
   }
 }
}
```


 - 액츄에이터의 기능을 웹에 노출시키기 위해선 설정을 추가해주어야 한다.(application.yml에 추가)
```
management:
 endpoints:
   web:
   exposure:
     include: "*"
```
추가 후
```
{
 "_links": {
   "self": {
     "href": "http://localhost:8080/actuator",
     "templated": false
 },
 "beans": {
   "href": "http://localhost:8080/actuator/beans",
   "templated": false
 },
 "caches": {
   "href": "http://localhost:8080/actuator/caches",
   "templated": false
 },
 "caches-cache": {
   "href": "http://localhost:8080/actuator/caches/{cache}",
   "templated": true
 },
 "health-path": {
   "href": "http://localhost:8080/actuator/health/{*path}",
   "templated": true
 },
 "health": {
   "href": "http://localhost:8080/actuator/health",
   "templated": false
 },
 "info": {
   "href": "http://localhost:8080/actuator/info",
   "templated": false
 },
 "conditions": {
   "href": "http://localhost:8080/actuator/conditions",
   "templated": false
 },
 "configprops-prefix": {
   "href": "http://localhost:8080/actuator/configprops/{prefix}",
   "templated": true
 },
 "configprops": {
   "href": "http://localhost:8080/actuator/configprops",
   "templated": false
 },
 "env": {
   "href": "http://localhost:8080/actuator/env",
   "templated": false
 },
 "env-toMatch": {
   "href": "http://localhost:8080/actuator/env/{toMatch}",
   "templated": true
 },
 "loggers": {
   "href": "http://localhost:8080/actuator/loggers",
   "templated": false
 },
 "loggers-name": {
   "href": "http://localhost:8080/actuator/loggers/{name}",
   "templated": true
 },
 "heapdump": {
   "href": "http://localhost:8080/actuator/heapdump",
   "templated": false
 },
 "threaddump": {
   "href": "http://localhost:8080/actuator/threaddump",
   "templated": false
 },
 "metrics": {
   "href": "http://localhost:8080/actuator/metrics",
   "templated": false
 },
 "metrics-requiredMetricName": {
   "href": "http://localhost:8080/actuator/metrics/{requiredMetricName}",
   "templated": true
 },
 "scheduledtasks": {
   "href": "http://localhost:8080/actuator/scheduledtasks",
   "templated": false
 },
 "mappings": {
   "href": "http://localhost:8080/actuator/mappings",
   "templated": false
   }
 }
}
```

 - 액츄에이터가 제공하는 기능 하나하나를 엔드포인트라 한다.
 - 각각 /actuator/엔드포인트 명  을 통해 접근할 수 있다.

#### 엔드포인트 설정
 - 엔드포인트를 사용하려면 다음 두 가지의 과정이 모두 필요하다
   - 엔드포인트 활성화
   - 엔드포인트 노출
  
 - 엔드포인트 활성화 : 해당 엔드포인트를 사용할지 말지를 선택
 - 엔드포인트 노출 : 활성화 된 엔드포인트를 HTTP에 노출할지 말지 선택
 - shotdown을 제외한 대부분의 엔드포인트는 기본적으로 활성화 되어있다.
application.yml - 모든 엔드포인트를 웹에 노출
```
management:
 endpoints:
  web:
   exposure:
     include: "*"
```

application.yml - shutdown 엔드포인트 활성화
```
management:
 endpoint:
   shutdown:
     enabled: true
 endpoints:
   web:
     exposure:
       include: "*"
```
각 엔드포인트의 활성화는  management.endpoint.{엔드포인트명}.enabled=true 로 가능

#### 엔드포인트 목록
 - beans : 스프링 컨테이너에 등록된 스프링 빈을 보여준다.
 - conditions : condition 을 통해서 빈을 등록할 때 평가 조건과 일치하거나 일치하지 않는 이유를 표시한다.
 - configprops : @ConfigurationProperties 를 보여준다.
 - env : Environment 정보를 보여준다.
 - health : 애플리케이션 헬스 정보를 보여준다.
 - httpexchanges : HTTP 호출 응답 정보를 보여준다. HttpExchangeRepository 를 구현한 빈을 별도로 등록해야 한다.
 - info : 애플리케이션 정보를 보여준다.
 - loggers : 애플리케이션 로거 설정을 보여주고 변경도 할 수 있다.
 - metrics : 애플리케이션의 메트릭 정보를 보여준다.
 - mappings : @RequestMapping 정보를 보여준다.
 - threaddump : 쓰레드 덤프를 실행해서 보여준다.
 - shutdown : 애플리케이션을 종료한다. 이 기능은 기본으로 비활성화 되어 있다

#### 액츄에이터와 보안
 - 액츄에이터가 제공하는 기능들은 어플리케이션의 내부 정보를 너무 많이 노출한다.
 - 외부 인터넷 망이 공개된 곳에 액츄에이터의 엔드포인트를 공해하는것은 보안상 좋지 않다.
 - 내부에서만 접근 가능한 내부망을 사용하는 것이 안전하다.
 - 예를들면 내부망에서만 접근 가능한 포트를 설정 해준다.
   - management.server.port=(내부에서만 접근 가능한 포트)
 - 포트 분리가 어렵다면 URL경로에 인터셉터나 스프링 시큐리티를 통하여 인증된 사용자만 접근 가능하도록 추가한다.
 - 엔트포인트 경로 변경
application.yml
```
management:
 endpoints:
   web:
     base-path: "/manage"
```

## 마이크로미터


