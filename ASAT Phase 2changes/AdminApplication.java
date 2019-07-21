package com.ibm.asat.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.support.ErrorPageFilter;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories( "com.ibm.asat.common.repositories" )
@ComponentScan(basePackages="com.ibm.asat")
@EnableOAuth2Sso
public class AdminApplication extends SpringBootServletInitializer
{
    public static void main( String[] args )
    {
        SpringApplication.run( AdminApplication.class, args );
    }

    @Override
    protected SpringApplicationBuilder configure( SpringApplicationBuilder application )
    {
        return application.sources( AdminApplication.class );
    }

    @Bean
    public ErrorPageFilter errorPageFilter()
    {
        return new ErrorPageFilter();
    }

    @SuppressWarnings( { "rawtypes", "unchecked" } )
    @Bean
    public FilterRegistrationBean disableSpringBootErrorFilter( ErrorPageFilter filter )
    {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter( filter );
        filterRegistrationBean.setEnabled( false );
        return filterRegistrationBean;
    }
}
