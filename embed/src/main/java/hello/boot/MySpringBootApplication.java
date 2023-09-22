package hello.boot;

import org.springframework.context.annotation.ComponentScan;

import java.lang.annotation.*;

@Target(ElementType.TYPE) // 적용대상
// 컴파일러가 어노테이션을 다루는 기술, 타이밍
@Retention(RetentionPolicy.RUNTIME) // 정보유지대상
@Documented
@ComponentScan
public @interface MySpringBootApplication { // 어노테이션 명

}
