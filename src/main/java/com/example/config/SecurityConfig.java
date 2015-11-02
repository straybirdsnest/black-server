package com.example.config;

import com.example.config.restful.RestAuthenticationEntryPoint;
import com.example.config.restful.Token;
import org.jose4j.jwe.ContentEncryptionAlgorithmIdentifiers;
import org.jose4j.jwe.JsonWebEncryption;
import org.jose4j.jwe.KeyManagementAlgorithmIdentifiers;
import org.jose4j.keys.AesKey;
import org.jose4j.lang.ByteUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

@Configuration
@EnableWebMvcSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private SecurityUserDetailsService securityUserDetailsService;

    @Autowired
    RestAuthenticationEntryPoint entryPoint;

    @Autowired
    Environment env;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
						.authorizeRequests()
				.antMatchers("/register").permitAll()
                .antMatchers("/api/**").authenticated();
        /*
                .and()
                .addFilterAfter(new CustomTokenAuthenticationFilter("/api/**"), UsernamePasswordAuthenticationFilter.class)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling().authenticationEntryPoint(entryPoint);
        */
        // disable csrf proetection for no browser application will be served
        http.csrf().disable();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		// auth.inMemoryAuthentication().withUser("user").password("password").roles("USER");
        // auth.jdbcAuthentication().passwordEncoder(passwordEncoder());
        auth.userDetailsService(securityUserDetailsService);
	}

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

	@Bean
	public JsonWebEncryption jsonWebEncryption() {
		Key key = new AesKey(ByteUtil.randomBytes(16));
		JsonWebEncryption jwe = new JsonWebEncryption();
		jwe.setAlgorithmHeaderValue(KeyManagementAlgorithmIdentifiers.A128KW);
		jwe.setEncryptionMethodHeaderParameter(ContentEncryptionAlgorithmIdentifiers.AES_128_CBC_HMAC_SHA_256);
		jwe.setKey(key);
		return jwe;
	}

    @Bean
    public SecretKeySpec key() throws NoSuchAlgorithmException{
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        String key = env.getProperty("blackserver.secretkey");
        kgen.init(128, new SecureRandom(key.getBytes()));
        byte[] enCodeFormat = kgen.generateKey().getEncoded();
        SecretKeySpec result = new SecretKeySpec(enCodeFormat, "AES");
        Token.setKey(result);
        return result;
    }

}
