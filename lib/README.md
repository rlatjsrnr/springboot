## Spring-boot starter와 library
스프링부트가 나오기 이전 개발자들은 수 많은 라이브러리들을 선택해야 했고, 각 라이브러리의 버전까지 고민해야 했다.<br/>
이러한 문제들 때문에 처음 프로젝트를 세팅하는데 상당히 많은 시간을 소비했다. <br/>

스프링부트는 다음과 같은 기능을 제공한다.
 - 외부 라이브러리 버전 관리
 - 스프링 부트 스타터 제공

#### Spring-boot library version management
스프링 부트는 개발자 대신에 수 많은 라이브러리의 버전을 직접 관리해준다. <br/>
이제 개발자는 원하는 라이브러리만 고르고 라이브러리의 버전은 생략해도 된다. <br/>
그러면 스프링 부트가 부트 버전에 맞춘 최적화된 라이브러리 버전을 선택해준다.<br/>
버전 관리 기능을 사용하려면 io.spring.dependency-management 플러그인을 사용해야 한다.<br/>

```
plugins {
 id 'org.springframework.boot' version '3.0.2'
 id 'io.spring.dependency-management' version '1.1.0' //추가
 id 'java'
}
```
 - 스프링부트가 관리하지 않는 라이브러리도 있는데, 이 때는 버전을 직접 적어주어야 한다.

#### Spring-boot starter
 - 앞서 보았듯이 웹 프로젝트를 하나 실행하려면 생각보다 수 많은 라이브러리가 필요하다.
 - 스프링 웹 MVC, 내장 톰캣, JSON 처리, 스프링 부트 관련, LOG, YML 등등 다양한 라이브러리가 사용된다.
 - 개발자 입장에서는 그냥 웹 프로젝트를 하나 시작하고 싶은 것이고, 일반적으로 많이 사용하는 대중적인 라이브러리들을 포함해서 간단하게 시작하고 싶을 것이다.
 - 스프링부트는 이런 문제를 해결하기 위해 프로젝트를 시작하는데 필요한 관련 라이브러리를 모아둔 스프링부트 스타터를 제공한다.
 - 스프링부트 스타터 덕분에 누구나 쉽고 편리하게 프로젝트를 시작할 수 있다
 - ex) spring-boot-starter-web : 스프링 웹 MVC, 내장 톰캣, JSON 처리, 스프링 부트 관련, LOG, YML 등등을 포함하고 있다.

 - 스프링부트 스타터 이름 패턴
   -  공식: spring-boot-starter-*
   -  비공식: thirdpartyproject-spring-boot-starter
      - ex) mybatis-spring-boot-starter
