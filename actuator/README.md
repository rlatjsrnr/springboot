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
 - 마이크로미터는 애플리케이션 메트릭 파사드라고 불리는데, 애플리케이션의 메트릭(측정 지표)을 마이크로미터가 정한 표준 방법으로 모아서 제공해준다.
 - 쉽게 이야기해서 마이크로미터가 추상화를 통해서 구현체를 쉽게 갈아끼울 수 있도록 해두었다.
 - 보통은 스프링이 이런 추상화를 직접 만들어서 제공하지만, 마이크로미터라는 이미 잘 만들어진 추상화가 있기 때문에 스프링은 이것을 활용한다.
 - 스프링 부트 액츄에이터는 마이크로미터를 기본으로 내장해서 사용한다.
 - 개발자는 마이크로미터가 정한 표준 방법으로 메트릭(측정 지표)를 전달하면 된다.
 - 그리고 사용하는 모니터링 툴에 맞는 구현체를 선택하면 된다.
 - 이후에 모니터링 툴이 변경되어도 해당 구현체만 변경하면 된다. 애플리케이션 코드는 모니터링 툴이 변경되어도 그대로 유지할 수 있다.
 - 마이크로미터가 지원하는 모니터링 툴
   - AppOptics
   - Atlas
   - CloudWatch
   - Datadog
   - Dynatrace
   - Elastic
   - Ganglia
   - Graphite
   - Humio
   - Influx
   - Instana
   - JMX
   - KairosDB
   - New Relic
   - Prometheus
   - SignalFx
   - Stackdriver
   - StatsD
   - Wavefront
  - 마이크로미터는 이미 다양한 지표 수집 기능을 만들어 제공한다.
  - 스프링부트의 엑츄에이터는 마이크로미터가 제공하는 지표 수집을 @AutoConfiguration을 통해 자동 등록해 준다.
  - 엑츄에이터에 metrics 엔트포인트를 사용하면 기본으로 제공되는 메트릭들을 확인할 수 있다.
    - http://localhost:8080/actuator/metrics
  - metrics 엔드포인트는 다음과 같은 패턴을 사용해서 더 자세히 확인할 수 있다.
    - http://localhost:8080/actuator/metrics/{name}
  - metrics 내부에는 Tag가 존재하는데 이 Tag를 활용하여 필터링이 가능하다.
  - tag=KEY:VALUE 과 같은 형식을 사용해야 한다.
    - http://localhost:8080/actuator/metrics/jvm.memory.used?tag=area:heap

## 프로메테우스와 그라파나
 - 프로메테우스 (9090 포트)
   - 애플리케이션에서 발생한 메트릭을 그 순간만 확인하는 것이 아니라 과거 이력까지 함께 확인하려면 메트릭을 보관하는 DB가 필요하다.
   - 이렇게 하려면 어디선가 메트릭을 지속해서 수집하고 DB에 저장해야 한다.
   - 프로메테우스가 바로 이런 역할을 담당한다.
 - 그라파나 (3000 포트)
   - 프로메테우스가 DB라고 하면, 이 DB에 있는 데이터를 불러서 사용자가 보기 편하게 보여주는 대시보드가 필요하다.
   - 그라파나는 매우 유연하고, 데이터를 그래프로 보여주는 툴이다.
   - 수 많은 그래프를 제공하고, 프로메테우스를 포함한 다양한 데이터소스를 지원한다.

 - 구조
   - 스프링 부트 액츄에이터와 마이크로미터를 사용하면 수 많은 메트릭을 자동으로 생성한다.
     - 마이크로미터 프로메테우스 구현체는 프로메테우스가 읽을 수 있는 포멧으로 메트릭을 생성한다
   - 프로메테우스는 이렇게 만들어진 메트릭을 지속해서 수집한다.
   - 프로메테우스는 수집한 메트릭을 내부 DB에 저장한다.
   - 사용자는 그라파나 대시보드 툴을 통해 그래프로 편리하게 메트릭을 조회한다. 이때 필요한 데이터는 프로메테우스를 통해서 조회한다.
 
![image](https://github.com/rlatjsrnr/springboot/assets/137128415/61f56d36-699d-474d-ac1d-b9a675b878fc)

#### 프로메테우스 - 어플리케이션 설정
build.gradle 추가

```
implementation 'io.micrometer:micrometer-registry-prometheus' //추가
```

 - 마이크로미터 프로메테우스 구현 라이브러리를 추가한다.
 - 스프링 부트와 액츄에이터가 자동으로 마이크로미터 프로메테우스 구현체를 등록해서 동작하도록 설정해준다.
 - 액츄에이터에 프로메테우스 메트릭 수집 엔드포인트가 자동으로 추가된다.
   - /actuator/prometheus
 - 포멧 차이
   - 프로메테우스는 . 대신에 _ 포멧을 사용한다.
   - 로그수 처럼 지속해서 숫자가 증가하는 메트릭을 카운터라 한다. 프로메테우스는 카운터 메트릭의 마지막에는 관례상 _total 을 붙인다.

#### 프로메테우스 - 수집 설정
prometheus.yml 수정

```
- job_name: "spring-actuator"
 metrics_path: '/actuator/prometheus'
 scrape_interval: 1m
 static_configs:
  - targets: ['localhost:8080']
```

 - job_name : 수집하는 이름이다. 임의의 이름을 사용하면 된다.
 - metrics_path : 수집할 경로를 지정한다.
 - scrape_interval : 수집할 주기를 설정한다.
 - targets : 수집할 서버의 IP, PORT를 지정한다.

#### 프로메테우스 - 게이지와 카운터
 - 게이지(Gauge)
   - 임의로 오르내일 수 있는 값
   - 예) CPU 사용량, 메모리 사용량, 사용중인 커넥션
 - 카운터(Counter)
   - 단순하게 증가하는 단일 누적 값
   - 예) HTTP 요청 수, 로그 발생 수
  
 - 게이지는 오르락 내리락 하는 값이기 때문에 그냥 그대로 사용하면 된다.
 - 반면, 카운터는 단순하게 증가하는 단일 누적 값이다. 그래프가 그냥 쭉 우상향한다.
 - 데이터를 한 눈에 확인하기가 어렵다.
 - 이를 해결하기 위해 increase(), rate() 같은 함수를 지원한다.
    - increase() : 지정한 시간 단위별로 증가를 확인할 수 있다. 마지막에 [시간] 을 사용해서 범위 벡터를 선택해야 한다.
    - increase(http_server_requests_seconds_count{uri="/log"}[1m])
![image](https://github.com/rlatjsrnr/springboot/assets/137128415/72cc6e5e-28eb-4986-9c69-18c389b0c78d)

    - rate() : 범위 백터에서 초당 평균 증가율을 계산한다.
    - increase() 가 숫자를 직접 카운트 한다면, rate() 는 여기에 초당 평균을 나누어서 계산한다.
    - rate(data[1m]) 에서 [1m] 이라고 하면 60초가 기준이 되므로 60을 나눈 수이다.
    - rate(data[2m]) 에서 [2m] 이라고 하면 120초가 기준이 되므로 120을 나눈 수이다.
    - 그냥 초당 얼마나 증가하는가를 나타내는 지표라 보면 된다.
![image](https://github.com/rlatjsrnr/springboot/assets/137128415/deafd963-4133-4405-8ef0-7f1634b6f5db)

    - irate() : rate 와 유사한데, 범위 벡터에서 초당 순간 증가율을 계산한다. 급격하게 증가한 내용을 확인하기 좋다.
![image](https://github.com/rlatjsrnr/springboot/assets/137128415/bf260a6c-c7fe-467d-8e24-cea3c72991f0)

#### 그라파나 - 연동
프로메테우스의 단점은 한눈에 들어오는 대시보드를 만들어보기 어렵다는 점이다. 이 부분은 그라파나를 사용하면 된다. <br/>
프로메테우스의 데이터를 활용하여 데시보드를 만들 수 있다.

 - 설정(Configuration)에서 Data sources 선택
 - Add data source 선택
 - Prometheus 선택

 - 그라파나를 활용하여 대시보드를 만들기위해선 다음 세 가지가 필요하다.
   - 어플리케이션 실행
   - 프로메테우스 실행
   - 그라파나 실행

#### 그라파나 -공유 대시보드 활용
 - https://grafana.com/grafana/dashboards
 - 위 사이트에 접속하면 이미 누군가 만들어둔 수 많은 대시보드가 공개되어 있다.
 - 필요한거 잘 가져다가 쓰자.
 - 가져올 대시보드의 아이디를 복사하고
 - 그라파나에 접속한 후 Dashboards 메뉴 -> New -> Import 선택
 - 복사한 아이디 입력 후 로드

## 메트릭 등록 - 카운터
 - MeterRegistry : 마이크로미터 기능을 제공하는 핵심 컴포넌트
   - 스프링을 통해서 주입 받아서 사용하고, 이곳을 통해서 카운터, 게이지 등을 등록한다.
 
   ![image](https://github.com/rlatjsrnr/springboot/assets/137128415/ef9c40a4-7b1b-4cf8-baf9-17df57da840c)

   - Counter.builder(name)을 통해 카운터를 생성한다. name은 메트릭 이름
   - register(registry) : 만든 카운터를 MeterRegistry 에 등록한다. 이렇게 등록해야 실제 동작한다.

## 메트릭 등록 - @Counted
 - 앞서 적용한 메트릭의 단점은 로직이 핵심 비즈니스 개발 로직에 침투했다는 점이다.
 - 이를 해결하기 위해 스프링 AOP를 사용한다.
 - 마이크로미터는 이런 상황에 맞춰 필요한 AOP요소를 만들어 두었다.

 - @Counted("name") 애노테이션을 측정을 원하는 메서드에 적용한다.
 - name 은 메트릭 이름
 - CountedAspect 를 빈으로 등록하면 @Counted 를 인지해서 Counter 를 사용하는 AOP를 적용한다.

## 메트릭 등록 - Timer
 - 시간을 측정하는데 사용된다.
 - 카운터와 유사한데, Timer 를 사용하면 실행 시간도 함께 측정할 수 있다.
 - Timer 는 다음과 같은 내용을 한번에 측정해준다
   - seconds_count : 누적 실행 수 - 카운터
   - seconds_sum : 실행 시간의 합 - sum
   - seconds_max : 최대 실행 시간(가장 오래걸린 실행 시간) - 게이지
     - 내부에 타임 윈도우라는 개념이 있어서 1~3분 마다 최대 실행 시간이 다시 계산된다.
![image](https://github.com/rlatjsrnr/springboot/assets/137128415/ea00ac47-0abc-4dd6-869f-f9ab09c99994)

 - Timer.builder(name) 를 통해서 타이머를 생성한다. name 에는 메트릭 이름을 지정한다.
 - register(registry) : 만든 타이머를 MeterRegistry 에 등록한다. 이렇게 등록해야 실제 동작한다.
 - 타이머를 사용할 때는 timer.record() 를 사용하면 된다. 그 안에 시간을 측정할 내용을 함수로 포함하면 된다.
   
## 메트릭 등록 - @Timed
 - 타이머는 @Timed 라는 애노테이션을 통해 AOP를 적용할 수 있다
 - @Timed("name") 타입이나 메서드 중에 적용할 수 있다. 타입에 적용하면 해당 타입의 모든 public 메서드에 타이머가 적용된다
 - TimedAspect 를 빈으로 등록해야 @Timed 에 AOP가 적용된다.

## 메트릭 등록 - 게이지
![image](https://github.com/rlatjsrnr/springboot/assets/137128415/8920b8be-5247-4d5f-a314-fb7ea15175e4)

 - 게이지를 확인하는 함수는 외부에서 메트릭을 확인할 때 호출 된다.
 - 프로메테우스가 주기적으로 메트릭을 확인하기 때문에 stock gauge call 로그가 주기적으로 남는다.

## 모니터링 환경 구성
 - 대시보드
 - 어플리케이션 추적 - 핀포인트
 - 로그

 - 대시보드 : 전체를 한눈에 볼 수 있는 가장 높은 뷰
   - 마이크로미터, 프로메테우스, 그라파나 등등
   - 모니터링 대상
     - 시스템 메트릭(CPU, 메모리)
     - 어플리케이션 메트릭(톰캣 쓰레드 풀, DB 커넥션 풀, 어플리케이션 호출 수)
     - 비즈니스 메트릭(주문수, 취소수)
      
 - 애플리케이션 추적 : 주로 각각의 HTTP 요청을 추적, 일부는 마이크로서비스 환경에서 분산 추적
   - 핀포인트(오픈소스), 스카우트(오픈소스), 와탭(상용), 제니퍼(상용)
  
 - 로그 : 가장 자세한 추적, 원하는데로 커스텀 가능, 같은 HTTP 요청을 묶어서 확인할 수 있는 방법이 중요, MDC 적용
   - 파일로 직접 로그를 남기는 경우
     - 일반 로그와 에러 로그는 파일을 구분해서 남기자
     - 에러 로그만 확인해서 문제를 바로 정리할 수 있음
   - 클라우드에 로그를 저장하는 경우
     - 검색이 잘 되도록 구분
    
  
 
