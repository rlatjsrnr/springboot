## 스프링부트와 웹 서버
 - 내장 톰캣을 사용해서 빌드와 배포를 편리하게 한다.
 - 빌드시 하나의 Jar를 사용하면서, 동시에 Fat Jar 문제도 해결한다.
 - 지금까지 진행한 내장 톰캣 서버를 실행하기 위한 복잡한 과정을 모두 자동으로 처리한다

#### 프로젝트 생성
<strong>Spring initializr</strong> : https://start.spring.io
</br>
프로젝트 생성 시 Dependencies에 Spring Web을 추가해주면 이전에 했던 모든 작업을 다 해줌

#### 실행 과정
```
@SpringBootApplication
  public class BootApplication {
    public static void main(String[] args) {
      SpringApplication.run(BootApplication.class, args);
    }
  }
```
 - 스프링 부트를 실행할 때는 자바 main() 메서드에서 SpringApplication.run() 을 호출해주면 된다.
 - 여기에 메인 설정 정보를 넘겨주는데, 보통 @SpringBootApplication 애노테이션이 있는 현재 클래스를 지정해주면 된다.
 - 참고로 현재 클래스에는 @SpringBootApplication 애노테이션이 있는데, 이 애노테이션 안에는 컴포넌트 스캔을 포함한 여러 기능이 설정되어 있다. 기본 설정은 현재 패키지와 그 하위 패키지 모두를 컴포넌트 스캔한다.

위의 코드를 실행 하는 동안 이전에 해 보았던 스프링 컨테이너 생성과 내장 톰캣 생성이 된다.

#### 빌드와 배포
jar 빌드 : ./gradlew clean build <br/>
빌드에 성공하면 build/libs/boot-0.0.1-SNAPSHOT.jar 이 위치에 jar파일이 생성됨 <br/>
jar 실행 : java -jar boot-0.0.1-SNAPSHOT.jar<br/>
 -> 정상 동작

#### 스프링부트 실행 가능 Jar
위의 jar파일의 압축을 풀어보면 내부에 jar파일이 존재한다 <br/>
이는 FatJar의 단점을 해결하기 위함이다 <br/>

 - 문제: 어떤 라이브러리가 포함되어 있는지 확인하기 어렵다.
   - 해결: jar 내부에 jar를 포함하기 때문에 어떤 라이브러리가 포함되어 있는지 쉽게 확인할 수 있다.
 - 문제: 파일명 중복을 해결할 수 없다.
   - 해결: jar 내부에 jar를 포함하기 때문에 a.jar , b.jar 내부에 같은 경로의 파일이 있어도 둘다 인식할 수 있다.
 

