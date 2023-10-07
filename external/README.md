## 외부설정과 프로필

#### 외부설정이란?
 - 하나의 어플리케이션을 여러 환경에서 사용해야 할 때가 있다.
   - 개발환경 : 개발 서버, 개발 DB 사용
   - 운영환경 : 운영 서버, 운영 DB 사용
 - 각 환경에서 DB에 접근하려면 서로 다른 url을 사용해야 하는 문제가 있다.
 - 이를 해결하기 위한 가장 단순한 방법은 환경 마다 따로 빌드 하는 것
<br/>

![image](https://github.com/rlatjsrnr/springboot/assets/137128415/74f8c32f-6ada-4a15-a788-3f1237d42f2a)

하지만 이 방법은 좋은 방법이 아니다.
 - 환경에 따라서 빌드를 여러번 해야 한다.
 - 개발 버전과 운영 버전의 빌드 결과물이 다르다. 따라서 개발 환경에서 검증이 되더라도 운영 환경에서 다른 빌드 결과를 사용하기 때문에 예상치 못한 문제가 발생할 수 있다. 개발용 빌드가 끝나고 검증한 다음에 운영용 빌드를 해야 하는데 그 사이에 누군가 다른 코드를 변경할 수도 있다. 한마디로 진짜 같은 소스코드에서 나온 결과물인지 검증하기가 어렵다.
 - 각 환경에 맞추어 최종 빌드가 되어 나온 빌드 결과물은 다른 환경에서 사용할 수 없어서 유연성이 떨어진다. 향후 다른 환경이 필요하면 그곳에 맞도록 또 빌드를 해야 한다.
 
이런 이유로 다음과 같이 빌드는 한 번만 하고 실행 시점에 외부 설정값을 주입해 준다.
<br/>

![image](https://github.com/rlatjsrnr/springboot/assets/137128415/2452c567-8e50-40fe-87e3-4c71e89c1f34)

#### 외부설정
어플리케이션을 실행할 때 필요한 설정값을 외부에서 불러오는 방법 네 가지
 - OS 환경 변수: OS에서 지원하는 외부 설정, 해당 OS를 사용하는 모든 프로세스에서 사용
 - 자바 시스템 속성: 자바에서 지원하는 외부 설정, 해당 JVM안에서 사용
 - 자바 커맨드 라인 인수: 커맨드 라인에서 전달하는 외부 설정, 실행시 main(args) 메서드에서 사용
 - 외부 파일(설정 데이터): 프로그램에서 외부 파일을 직접 읽어서 사용
   - 애플리케이션에서 특정 위치의 파일을 읽도록 해둔다. 예) data/hello.txt
   - 그리고 각 서버마다 해당 파일안에 다른 설정 정보를 남겨둔다.
     - 개발 서버 hello.txt : url=dev.db.com
     - 운영 서버 hello.txt : url=prod.db.com

#### 외부설정 - OS 환경 변수
OS 환경 변수(OS environment variables)는 해당 OS를 사용하는 모든 프로그램에서 읽을 수 있는 설정값이다. 한마디로 다른 외부 설정과 비교해서 사용 범위가 가장 넓다.
 - System.getenv() 를 사용하면 전체 OS 환경 변수를 Map 으로 조회할 수 있다
 - System.getenv(key) 를 사용하면 특정 OS 환경 변수의 값을 String 으로 조회할 수 있다.
환경 변수는 전역변수같은 느낌, 다른 프로그램에서도 사용 가능

#### 외부설정 - 자바 시스템 속성
자바 시스템 속성(Java System properties)은 실행한 JVM 안에서 접근 가능한 외부 설정이다. 추가로 자바가 내부에서 미리 설정해두고 사용하는 속성들도 있다.
 - 예) java -Durl=dev -jar app.jar
 - -D VM 옵션을 통해서 key=value 형식을 주면 된다. 이 예제는 url=dev 속성이 추가된다.
 - 순서에 주의해야 한다. -D 옵션이 - jar 보다 앞에 있다.
 - 어플리케이션 내에서는 System.getProperties() 를 사용하면 Map 과 유사한( Map 의 자식 타입) key=value 형식의 Properties 를 받을 수 있다. 이것을 통해서 모든 자바 시스템 속성을 조회할 수 있다.
 - System.getProperty(key) 를 사용하면 속성값을 조회할 수 있다.
 - 사용자가 직접 정의하는 자바 시스템 속성(url , username , password)
   - IDE에서 VM 옵션 추가
     - Modify options -> Add VM options
     - VM options에 -Durl=devdb -Dusername=dev_user -Dpassword=dev_pw 를 추가한다.
   - 자바 시스템 속정을 자바 코드로 설정하기
     - 설정: System.setProperty(propertyName, "propertyValue")
     - 조회: System.getProperty(propertyName)

#### 외부설정 - 커맨드 라인 인수
커맨드 라인 인수(Command line arguments)는 애플리케이션 실행 시점에 외부 설정값을 main(args) 메서드의 args 파라미터로 전달하는 방법이다.
 - 예) java -jar app.jar dataA dataB
 - 필요한 데이터를 마지막 위치에 스페이스로 구분해서 전달하면 된다. 이 경우 dataA , dataB 2개의 문자가 args 에 전달된다.
 - IDE에서는 Program arguments 에 공백으로 구분하여 입력하면 된다.
 - Jar파일 실행 시 java -jar project.jar dataA dataB 로 추가해 줄 수도 있다.
 - key=value 형태로 데이터를 전달 하기 위해서는 --로 시작하면 된다
   - --username=userA --username=userB

#### 외부설정 - 스프링 통합
위의 방식을 스프링은 Environment 와 PropertySource 라는 추상화를 통해서 편리하게 제공한다.
<br/>

![image](https://github.com/rlatjsrnr/springboot/assets/137128415/67738933-2d68-4b04-83c7-9f8a4a61eb98)

 - 스프링은 PropertySource 라는 추상 클래스를 제공하고, 각각의 외부 설정를 조회하는 XxxPropertySource 구현체를 만들어두었다. 
 - 스프링은 로딩 시점에 필요한 PropertySource 들을 생성하고, Environment 에서 사용할 수 있게 연결해둔다.
 - Environment 를 통해서 특정 외부 설정에 종속되지 않고, 일관성 있게 key=value 형식의 외부 설정에 접근할 수 있다.
   - environment.getProperty(key) 를 통해서 값을 조회할 수 있다
   - Environment 는 내부에서 여러 과정을 거쳐서 PropertySource 들에 접근한다.
   - 같은 값이 있을 경우를 대비해서 스프링은 미리 우선순위를 정해두었다.(더 유연할수록, 더 좁을수록 높은 우선순위를 가진다)

#### 설정데어터 - 외부파일
 - 설정 데이터를 외부파일로 관리할 때 application.properties 파일 안에 key=value 형식으로 작성하면 된다.
 - 다른 환경에서 다른 설정 적용을 위하여 프로필을 사용하고 application-profileName.properties 로 파일을 지정한 후 외부 설정값으로 프로필 이름을 받아 사용한다.
 - #--- 또는 !--- (application.yml은 ---) 을 이용하여 파일 내부에서 논리적으로 영역을 나눌 수 있다.

```
spring.config.activate.on-profile=dev
url=dev.db.com
username=dev_user
password=dev_pw
#---
spring.config.activate.on-profile=prod
url=prod.db.com
username=prod_user
password=prod_pw
```

 - 논리적으로 영역을 구분하는 #--- 위 아래로는 주석을 적으면 안된다.
 - 가장 윗줄에 spring.config.activate.on-profile을 작성하지 않으면 default 프로필로 지정되고 기본적으로 적용된다.
 - 설정 파일은 위에서 아래로 순차적으로 읽어지고 만약 읽은 설정 데이터중 같은 key값이 존재한다면 덮어쓴다.
