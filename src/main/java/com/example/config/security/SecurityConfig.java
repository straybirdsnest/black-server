package com.example.config.security;

import com.example.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;

@Configuration
// To switch off the default web security configuration completely
// @EnableWebSecurity does not disable the authentication manager configuration
// 也就是说还会有一个默认密码的
// @EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableScheduling
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
//@Order(ManagementServerProperties.ACCESS_OVERRIDE_ORDER)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired TokenService tokenService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(new TokenAuthenticationFilter(tokenService), BasicAuthenticationFilter.class)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf().disable()
                .httpBasic().disable()
                .authorizeRequests()
                .antMatchers("/api/register", "/api/token").permitAll()
                .anyRequest().authenticated()
                .and().exceptionHandling().authenticationEntryPoint(unauthorizedEntryPoint());
    }

    @Bean
    public AuthenticationEntryPoint unauthorizedEntryPoint() {
        return (request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
    }

    @Configuration
    @Order(Ordered.HIGHEST_PRECEDENCE)
    protected static class CleanAuthenticationManager extends GlobalAuthenticationConfigurerAdapter {

        @Override
        public void init(AuthenticationManagerBuilder auth) throws Exception {
            // do nothing
            auth.authenticationProvider(null);
        }
    }
}