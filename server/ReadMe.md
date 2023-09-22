# server

## build.gradle
plugins{<br/>
  &nbsp;&nbsp;id 'war'<br/>
}<br/>
 - 톰캣 같은 웹 애플리케이션 서버(WAS) 위에서 동작하는 WAR 파일을 만들어준다.<br/>
<br/>
dependencies { <br/>
 &nbsp;&nbsp;implementation 'jakarta.servlet:jakarta.servlet-api:6.0.0'<br/>
}<br/>
 - jakarta.servlet-api : 서블릿을 사용할 때 필요한 라이브러리<br/>

## WAR 빌드와 배포
 - 프로젝트 빌드 : ./gradlew build
 - WAR 파일 생성 확인 : build/libs/server-0.0.1-SNAPSHOT.war

 - WAR 배포 :
    1. 톰캣 서버를 종료한다. ./shutdown.sh
    2. 톰캣폴더/webapps 하위를 모두 삭제한다.
    3. 빌드된 server-0.0.1-SNAPSHOT.war 를 복사한다.
    4. 톰캣폴더/webapps 하위에 붙여넣는다.
        톰캣폴더/webapps/server-0.0.1-SNAPSHOT.war
    5. 이름을 변경한다.
        톰캣폴더/webapps/ROOT.war
    6. 톰캣 서버를 실행한다. ./startup.sh

  - 실행 결과 확인 : http://localhost:8080/index.html


## 서블릿 컨테이너 초기화1
서블릿은 ServletContainerInitializer 라는 인터페이스 제공<br/>
서블릿 컨테이너는 실행 시점에 초기화 메서드인 onStartup()을 호출하여 <br/>
필요한 기능들을 초기화 하거나 등록함<br/>
<br/>
resources/META-INF/services/jakarta.servlet.ServletContainerInitializer <br/>
위와 같이 파일을 만들고 파일 내부에 hello.container.MyContainerInitV1 이와같이 클래스를 패키지 경로 포함하여 입력해줘서 <br/>
WAS에게 실행할 초기화 클래스를 알려줌

## 서블릿 컨테이너 초기화2
서블릿을 등록하는 방법은 두 가지가 있음
 - @WebServlet 어노테이션
 - 프로그래밍 방식

#### 프로그래밍 방식으로 서블릿을 등록하는 이유
@WebServlet 을 사용하면 편리하게 등록할 수는 있지만 유연하게 변경하긴 어려움<br/>
마치 하드코딩 된 것 처럼 동작<br/>
반면 프로그래밍 방식은 더 많은 코딩이 필요하지만 무한한 유연성을 제공

 - 프로그래밍 방식
   애플리케이션 초기화
    - 애플리케이션 초기화를 위해선 인터페이스가 꼭 필요함
    - AppInit 인터페이스를 만들고 이를 구현한 AppInitV1Servlet을 만듬
    - 여기에서 HelloServlet을 등록해줌
  
  #### 애플리케이션 초기화 과정
  1. @HandlesTypes 애노테이션에 애플리케이션 초기화 인터페이스를 지정한다.<br/>
      여기서는 앞서 만든 AppInit.class 인터페이스를 지정했다.
  2. 서블릿 컨테이너 초기화( ServletContainerInitializer )는 파라미터로 넘어오는 Set<Class<?>> c 에 애플리케이션 초기화 인터페이스      의 구현체들을 모두 찾아서 클래스 정보로 전달한다.<br/>
      여기서는 @HandlesTypes(AppInit.class) 를 지정했으므로 AppInit.class 의 구현체인 AppInitV1Servlet.class 정보가 전달된다. <br/>
      참고로 객체 인스턴스가 아니라 클래스 정보를 전달하기 때문에 실행하려면 객체를 생성해서 사용해야 한다.
  3. appInitClass.getDeclaredConstructor().newInstance() 리플렉션을 사용해서 객체를 생성한다. 참고로 이 코드는
     new AppInitV1Servlet() 과 같다 생각하면 된다.
  4. appInit.onStartup(ctx)
      애플리케이션 초기화 코드를 직접 실행하면서 서블릿 컨테이너 정보가 담긴 ctx 도 함께 전달한다.

#### 애플리캐이션 초기화를 하는 이유
  - 편리함 <br/>
    서블릿 컨테이너를 초기화 하려면 ServletContainerInitializer 인터페이스를 구현한 코드를
    만들어야 한다. 여기에 추가로 META-INF/services/
    jakarta.servlet.ServletContainerInitializer 파일에 해당 코드를 직접 지정해주어야 한다.
    애플리케이션 초기화는 특정 인터페이스만 구현하면 된다.
    
 - 의존성 <br/>
    애플리케이션 초기화는 서블릿 컨테이너에 상관없이 원하는 모양으로 인터페이스를 만들 수 있다.
    이를 통해 애플리케이션 초기화 코드가 서블릿 컨테이너에 대한 의존을 줄일 수 있다. 특히
    ServletContext ctx 가 필요없는 애플리케이션 초기화 코드라면 의존을 완전히 제거할 수도 있다.
  
