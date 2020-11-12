package library.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.extras.springsecurity5.dialect.SpringSecurityDialect;

@Configuration
public class WebConfig implements WebMvcConfigurer {   
	
	@Autowired
    private MessageSource messageSource;
	
	 @Bean
	    public BCryptPasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		
		registry.addViewController("/").setViewName("index");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/loggedIn").setViewName("index");
        registry.addViewController("/registration").setViewName("registration");
        registry.addViewController("/authors").setViewName("authors");
        registry.addViewController("/books").setViewName("books");
        registry.addViewController("/user/rent/{id}").setViewName("/user/result");
        registry.addViewController("/admin/addauthor").setViewName("/admin/author-add");
        registry.addViewController("/admin/editauthor/{id}").setViewName("/admin/author-edit");
        registry.addViewController("/admin/addbook").setViewName("/admin/book-add");
        registry.addViewController("/admin/editbook/{id}").setViewName("/admin/book-edit");
        registry.addViewController("/admin/users").setViewName("/admin/user");
        
	}
	
    @Override
    public Validator getValidator() {
        LocalValidatorFactoryBean factory = new LocalValidatorFactoryBean();
        factory.setValidationMessageSource(messageSource);
        return factory;
    }

    @Bean
	public SpringSecurityDialect securityDialect() {
	    return new SpringSecurityDialect();
	}

}
