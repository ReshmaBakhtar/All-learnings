package com.ibm.asat.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
//@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
@EnableOAuth2Sso
public class HttpSecurityConfig extends WebSecurityConfigurerAdapter {
	




   /* @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .formLogin()
                .loginPage("/login")
                .failureUrl("/login?error")
                .usernameParameter("email")
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .permitAll();
    }*/
    
    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        http.csrf().disable();
        // The pages does not require login
        http.authorizeRequests().antMatchers("/", "/admin/authenticate").permitAll();

        http
                .cors().and()
                .httpBasic()
                .and()
                .authorizeRequests()
                .antMatchers("/*", "/admin/authenticate", "/admin/*").permitAll()
                /*.antMatchers("/db/**").access("hasRole('ADMIN') and hasRole('DBA')")
                .antMatchers("/users/**").hasRole("USER")//USER role can access /users/**
                .antMatchers("/admin/**").hasRole("ADMIN")//ADMIN role can access /admin/**
*/              //  .antMatchers("/quests/**").permitAll()// anyone can access /quests/**
                .anyRequest().authenticated()
                .and()                
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))               
                .invalidateHttpSession(true)
            //    .logoutSuccessUrl("/api/user/logoutSuccess")               
                .permitAll()
                .and()
                .sessionManagement()
                .sessionFixation()
                .migrateSession()
                .maximumSessions(1)
                .expiredUrl("/sessionExpired.html")
                .maxSessionsPreventsLogin(false);
    }

/*    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(new BCryptPasswordEncoder());
    }*/
	
}
