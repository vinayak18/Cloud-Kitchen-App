package com.restaurant.userservice.config;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import com.restaurant.userservice.filter.JWTAccessDeniedHandler;
import com.restaurant.userservice.filter.JWTAuthenticationEntryPoint;
import com.restaurant.userservice.filter.JWTTokenGeneratorFilter;
import com.restaurant.userservice.filter.JWTTokenValidatorFilter;

import jakarta.servlet.http.HttpServletRequest;

@Configuration
public class AppSecurityConfig {

	@Autowired
	JWTAuthenticationEntryPoint entryPoint;

	@Autowired
	JWTAccessDeniedHandler accessDeniedHandler;

	@Bean
	CorsConfigurationSource getCorsConfigurationSource() {

		return new CorsConfigurationSource() {

			@Override
			public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
				CorsConfiguration config = new CorsConfiguration();
//		        config.setAllowedOrigins(Collections.singletonList(CorsConfiguration.ALL));
				config.setAllowedOrigins(Arrays.asList("https://localhost:4200","http://localhost:4201"));	
				config.setAllowedMethods(Collections.singletonList("*"));
				config.setAllowCredentials(true);
				config.setAllowedHeaders(Collections.singletonList("*"));
				config.setExposedHeaders(Arrays.asList("Authorization"));
				config.setMaxAge(3600L);
				return config;
			}
		};
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().cors()
				.configurationSource(getCorsConfigurationSource()).and()
				.csrf((csrf) -> csrf.ignoringRequestMatchers("/api/v1/userservice/auth/google/login", "/api/v1/userservice/auth/facebook/**", "/useApplication/**","/api/v1/userservice/auth/user/register",
						"/api/v1/userservice/verify/**","/api/v1/userservice/public/user/**"))
				.addFilterAfter(new JWTTokenGeneratorFilter(), BasicAuthenticationFilter.class)
				.addFilterBefore(new JWTTokenValidatorFilter(), BasicAuthenticationFilter.class).exceptionHandling()
				.authenticationEntryPoint(entryPoint).accessDeniedHandler(accessDeniedHandler).and()
				.authorizeHttpRequests((request)->request.requestMatchers("/api/v1/userservice/auth/user/login").authenticated()
						.requestMatchers("/api/v1/userservice/verify/**").hasAnyRole("ADMIN","CUSTOMER","SOCIAL_USER")
						.requestMatchers("/api/v1/userservice/validate/user").hasAnyRole("ADMIN","CUSTOMER","SOCIAL_USER")
				.requestMatchers("/api/v1/userservice/auth/google/login", "/api/v1/userservice/auth/facebook/**", "/useApplication/**","/api/v1/userservice/auth/user/register","/api/v1/userservice/public/user/**").permitAll())
				.httpBasic(Customizer.withDefaults()).formLogin().loginProcessingUrl("/api/v1/userservice/auth/user/login").permitAll();
		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
