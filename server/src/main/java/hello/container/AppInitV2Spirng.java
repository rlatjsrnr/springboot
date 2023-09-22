package hello.container;

import hello.spring.HelloConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletRegistration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class AppInitV2Spirng implements AppInit{
    @Override
    public void onStartup(ServletContext servletContext) {
        System.out.println("AppInitV2Spirng.onStartup");
        
        // 스프링 컨테이너 생성
        AnnotationConfigWebApplicationContext appContext = new AnnotationConfigWebApplicationContext();
        appContext.register(HelloConfig.class);
        
        // 스프링 mvc 디스페처 서블릿 생성, 스프링 컨테이너 연결
        DispatcherServlet dispatcher = new DispatcherServlet(appContext);
        ServletRegistration.Dynamic servlet = servletContext.addServlet("dispatcherV2", dispatcher);


        servlet.addMapping("/spring/*");

    }
}
