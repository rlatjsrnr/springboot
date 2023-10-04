# WAR 배포 방식의 단점

 - 톰캣 같은 WAS를 별도로 설치해야 한다.
 - 개발 환경 설정이 복잡하다.
 - 단순한 자바라면 별도의 설정을 고민하지 않고, main() 메서드만 실행하면 된다.
 - 웹 애플리케이션은 WAS 실행하고 또 WAR와 연동하기 위한 복잡한 설정이 들어간다.
 - 배포 과정이 복잡하다. WAR를 만들고 이것을 또 WAS에 전달해서 배포해야 한다.
 - 톰캣의 버전을 변경하려면 톰캣을 다시 설치해야 한다.

# JAR 배포 - 내장톰캣
1) jar
build.gradle - buildJar
```
task buildJar(type: Jar) {
 manifest {
 attributes 'Main-Class': 'hello.embed.EmbedTomcatSpringMain'
 }
 with jar
}
```

jar 빌드 : ./gradlew clean buildJar <br/>
빌드 성공 하면 build/libs/embed-0.0.1-SNAPSHOT.jar 만들어짐 <br/>
jar 실행 : java -jar embed-0.0.1-SNAPSHOT.jar  <br/>
-> 오류 발생
###### 원인 
jar파일 안에 jar 파일이 들어갈 수 없다.

2) FatJar
대안으로는 fat jar 또는 uber jar 라고 불리는 방법이다.<br/>
Jar 안에는 Jar를 포함할 수 없다. 하지만 클래스는 얼마든지 포함할 수 있다.<br/>
라이브러리에 사용되는 jar 를 풀면 class 들이 나온다. 이 class 를 뽑아서 새로 만드는 jar 에 포함하는 것이다.<br/>
이렇게 하면 수 많은 라이브러리에서 나오는 class 때문에 뚱뚱한(fat) jar 가 탄생한다. 그래서 FatJar 라고 부르는 것이다.<br/>
```
task buildFatJar(type: Jar) {
 manifest {
 attributes 'Main-Class': 'hello.embed.EmbedTomcatSpringMain'
 }
 duplicatesStrategy = DuplicatesStrategy.WARN
 from { configurations.runtimeClasspath.collect { it.isDirectory() ? it :
zipTree(it) } }
 with jar
}
```
jar 빌드 : ./gradlew clean buildFatJar <br/>
빌드 성공 하면 build/libs/embed-0.0.1-SNAPSHOT.jar 만들어짐 <br/>
jar 실행 : java -jar embed-0.0.1-SNAPSHOT.jar  <br/>
 -> 정상 실행

###### FatJar 장점
 - Fat Jar 덕분에 하나의 jar 파일에 필요한 라이브러리들을 내장할 수 있게 되었다.
 - 내장 톰캣 라이브러리를 jar 내부에 내장할 수 있게 되었다.
 - 하나의 jar 파일로 배포부터, 웹 서버 설치+실행까지 모든 것을 단순화 할 수 있다.

 - WAR 단점과 해결
  - 톰캣 같은 WAS를 별도로 설치해야 한다. <br/>
    해결: WAS를 별도로 설치하지 않아도 된다. 톰캣 같은 WAS가 라이브러리로 jar 내부에 포함되어 있다.
  - 개발 환경 설정이 복잡하다.<br/>
    단순한 자바라면 별도의 설정을 고민하지 않고, main() 메서드만 실행하면 된다.
  - 웹 애플리케이션은 WAS를 연동하기 위한 복잡한 설정이 들어간다.<br/>
    해결: IDE에 복잡한 WAS 설정이 필요하지 않다. 단순히 main() 메서드만 실행하면 된다.
  - 배포 과정이 복잡하다. WAR를 만들고 이것을 또 WAS에 전달해서 배포해야 한다.<br/>
    해결: 배포 과정이 단순하다. JAR를 만들고 이것을 원하는 위치에서 실행만 하면 된다.
  - 톰캣의 버전을 업데이트 하려면 톰캣을 다시 설치해야 한다.<br/>
    해결: gradle에서 내장 톰캣 라이브러리 버전만 변경하고 빌드 후 실행하면 된다.

###### FatJar 단점
 - 어떤 라이브러리가 포함되어 있는지 확인하기 어렵다.
   - 모두 class 로 풀려있으니 어떤 라이브러리가 사용되고 있는지 추적하기 어렵다.
 - 파일명 중복을 해결할 수 없다.
   - 클래스나 리소스 명이 같은 경우 하나를 포기해야 한다. -> 프로그램이 정상 작동하지 않음

