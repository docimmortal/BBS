package com.bbs.configs;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ProjectSecurityConfig {

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests().requestMatchers("/h2-console/**").permitAll()
        .and().csrf().ignoringRequestMatchers("/h2-console/**")
        .and().headers().frameOptions().sameOrigin();
		http.csrf().disable().authorizeHttpRequests()
		.requestMatchers("/images/**").permitAll()
		.anyRequest().authenticated()
		.and().httpBasic()
		.and().logout().permitAll()
		.and().formLogin(form -> form.loginPage("/login").defaultSuccessUrl("/hello", true).permitAll());
		http.headers().frameOptions().disable();
		return http.build();
	}
	
	@Bean
	public UserDetailsService userDetailService(DataSource dataSource) {
		return new JdbcUserDetailsManager(dataSource);
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
}
