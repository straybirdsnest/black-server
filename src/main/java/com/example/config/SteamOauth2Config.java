package com.example.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SteamOauth2Config extends WebSecurityConfigurerAdapter {

    public static final String STEAM_OPENID_SERVICE_ISSUER = "http://steamcommunity.com/openid/?l=chinese";

    /*
    @Bean
    public OIDCAuthenticationProvider oidcAuthenticationProvider() {
        OIDCAuthenticationProvider oidcAuthenticationProvider = new OIDCAuthenticationProvider();
        oidcAuthenticationProvider.setAuthoritiesMapper(namedAdminAuthoritiesMapper());
        return oidcAuthenticationProvider;
    }

    @Bean
    public NamedAdminAuthoritiesMapper namedAdminAuthoritiesMapper() {
        NamedAdminAuthoritiesMapper namedAdminAuthoritiesMapper = new NamedAdminAuthoritiesMapper();
        HashSet<SubjectIssuerGrantedAuthority> subjectIssuerGrantedAuthorities = new HashSet<>();
        subjectIssuerGrantedAuthorities.add(new SubjectIssuerGrantedAuthority("90342.ASDFJWFA", "http://localhost:8080/openid-connect-server-webapp/"));
        namedAdminAuthoritiesMapper.setAdmins(subjectIssuerGrantedAuthorities);
        return namedAdminAuthoritiesMapper;
    }

    @Bean
    public StaticSingleIssuerService staticSingleIssuerService() {
        StaticSingleIssuerService staticSingleIssuerService = new StaticSingleIssuerService();
        staticSingleIssuerService.setIssuer(STEAM_OPENID_SERVICE_ISSUER);
        return staticSingleIssuerService;
    }

    @Bean
    public DynamicServerConfigurationService dynamicServerConfigurationService() {
        DynamicServerConfigurationService dynamicServerConfigurationService = new DynamicServerConfigurationService();
        return dynamicServerConfigurationService;
    }

    @Bean
    public StaticClientConfigurationService staticClientConfigurationService() {
        StaticClientConfigurationService staticClientConfigurationService = new StaticClientConfigurationService();
        HashMap<String, RegisteredClient> registeredClientHashMap = new HashMap<>();
        RegisteredClient registeredClient = new RegisteredClient();
        registeredClient.setClientName("my-client-name");
        registeredClient.setClientSecret("my-google-client-secret-from-console");
        HashSet<String> scopes = new HashSet<>();
        scopes.add("email");
        scopes.add("openid");
        scopes.add("profile");
        registeredClient.setScope(scopes);
        registeredClientHashMap.put("https://accounts.google.com", registeredClient);
        staticClientConfigurationService.setClients(registeredClientHashMap);
        HashSet<String> redirectUris = new HashSet<>();
        redirectUris.add("https://my-redirect-uri-setup-in-google/");
        registeredClient.setRedirectUris(redirectUris);
        return staticClientConfigurationService;
    }

    @Bean
    public StaticAuthRequestOptionsService staticAuthRequestOptionsService() {
        StaticAuthRequestOptionsService staticAuthRequestOptionsService = new StaticAuthRequestOptionsService();
        return staticAuthRequestOptionsService;
    }

    @Bean
    public PlainAuthRequestUrlBuilder plainAuthRequestUrlBuilder() {
        return new PlainAuthRequestUrlBuilder();
    }

    @Bean
    public OIDCAuthenticationFilter OICAuthenticationFilter() throws Exception {
        OIDCAuthenticationFilter oidcAuthenticationFilter = new OIDCAuthenticationFilter();
        //oidcAuthenticationFilter.setAuthenticationManager(oidcAuthenticationProvider());

        oidcAuthenticationFilter.setIssuerService(staticSingleIssuerService());
        oidcAuthenticationFilter.setServerConfigurationService(dynamicServerConfigurationService());
        oidcAuthenticationFilter.setClientConfigurationService(staticClientConfigurationService());
        oidcAuthenticationFilter.setAuthRequestOptionsService(staticAuthRequestOptionsService());
        oidcAuthenticationFilter.setAuthRequestUrlBuilder(plainAuthRequestUrlBuilder());
        return oidcAuthenticationFilter;
    }
    */

    @Autowired
    public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("marissa").password("koala").roles("USER").and().withUser("paul")
                .password("emu").roles("USER");
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/api/**");
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http
                .authorizeRequests()
                .antMatchers("/requests").permitAll()
                .antMatchers("/tokens").permitAll()
                .antMatchers("/api/**").permitAll()
                .antMatchers("/login.jsp").permitAll()
                .anyRequest().hasRole("USER")
                .and()
                .exceptionHandling()
                .accessDeniedPage("/login.jsp?authorization_error=true")
                .and()
                // TODO: put CSRF protection back into this endpoint
                .csrf()
                .requireCsrfProtectionMatcher(new AntPathRequestMatcher("/oauth/authorize"))
                .disable()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login.jsp")
                .and()
                .formLogin()
                .loginProcessingUrl("/login")
                .failureUrl("/login.jsp?authentication_error=true")
                .loginPage("/login.jsp");
        // @formatter:on
        http.csrf().disable();
    }

}
