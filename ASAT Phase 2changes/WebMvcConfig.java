/**
 * 
 */
package com.ibm.asat.admin;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import  com.ibm.asat.admin.security.AuthenticationInterceptor;

import com.ibm.asat.admin.security.AppIdAuthUtilityImpl;




@SuppressWarnings( "deprecation" )
@Configuration
@EnableWebMvc
@ComponentScan( basePackages = { "com.ibm.asat.admin.controller", "com.ibm.asat.admin.security" } )
public class WebMvcConfig extends WebMvcConfigurerAdapter
{
    @Bean
    public MappingJackson2HttpMessageConverter customJackson2HttpMessageConverter()
    {
        MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion( JsonInclude.Include.NON_NULL );
        objectMapper.configure( DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false );
        jsonConverter.setObjectMapper( objectMapper );
        return jsonConverter;
    }

    @Override
    public void configureMessageConverters( List<HttpMessageConverter<?>> converters )
    {
        converters.add( customJackson2HttpMessageConverter() );

        super.configureMessageConverters( converters );
    }

   @Bean
    public InternalResourceViewResolver internalResourceViewResolver()
    {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix( "/WEB-INF/jsp/" );
        resolver.setSuffix( ".jsp" );
        return resolver;
    }
    
    @Bean
    AuthenticationInterceptor getAuthenticationInterceptor() {
        return new AuthenticationInterceptor();
    }
    
    @Bean
    AppIdAuthUtilityImpl getAppIdAuthUtilityImpl() {
        return new AppIdAuthUtilityImpl();
    }
    
    

   @Override
    public void addInterceptors (InterceptorRegistry registry) {
        registry.addInterceptor(getAuthenticationInterceptor()).addPathPatterns("/admin/authenticate/","/admin/*");
   

    }
   
       
}
