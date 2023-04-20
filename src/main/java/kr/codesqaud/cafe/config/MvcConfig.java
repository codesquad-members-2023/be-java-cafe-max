package kr.codesqaud.cafe.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
//요구사항: 아래의 Adapter가 deprecated되었는데 이유와 해결책에 대해 생각해 보고 해결하자....!!!
//  ===> 쓰임새가 사라진 WebMvcConfigurerAdapter 클래스는 스프링 부트 2.0에서 제외(Deprecated)되었다. -> (....!!!!!??)
public class MvcConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry){
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);  // ( = Integer.MIN_VALUE  ....)
//        registry.addViewController("/questions/form").setViewName("qna/form");
//        일종의 GetMapping 구현
    }
}
