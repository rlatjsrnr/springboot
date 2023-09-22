package hello.selector;

import org.springframework.context.annotation.Bean;

public class HelloConfig {

    @Bean
    public HelloBean helloBean(){
        return new HelloBean();
    }
}
