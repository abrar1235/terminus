package com.terminus.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.terminus.service.IUserService;
import com.terminus.util.ApplicationUtil;

@Configuration
@EnableWebSecurity
@SuppressWarnings("deprecation")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private static final String[] ALLOWED_ORIGINS = { "/api/v1/user/addUser", "/api/v1/login" };

	@Autowired
	ApplicationUtil appHelper;

	@Autowired
	IUserService userService;

	@Value("${secret}")
	String secret;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers(ALLOWED_ORIGINS).permitAll().anyRequest().authenticated().and().httpBasic()
				.and().cors().and().csrf().disable().addFilter(new JWTAuthenticationFilter(authenticationManager()))
				.addFilter(new JWTAuthorizationFilter(authenticationManager(), secret)).exceptionHandling()
				.authenticationEntryPoint(new JWTAuthenticationEntryPoint()).and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userService).passwordEncoder(encoder());
	}

	@Bean
	public PasswordEncoder encoder() {
		return new PasswordEncoder() {

			@Override
			public boolean matches(CharSequence rawPassword, String encodedPassword) {
				return encode(rawPassword).equals(encodedPassword);
			}

			@Override
			public String encode(CharSequence rawPassword) {
				try {
					return appHelper.encryptPassword(rawPassword.toString());
				} catch (Exception e) {
					return "";
				}
			}
		};
	}
}
