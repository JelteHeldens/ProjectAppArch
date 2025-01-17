package com.AppArch.Project.Config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity

public class SecurityConfig {
	@Autowired
	DataSource dataSource;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	@Bean
	SecurityFilterChain beveilig(HttpSecurity http) throws Exception {
		http
		.csrf(csrf -> csrf.disable())
		.authorizeHttpRequests(authorize -> authorize
				 
				.requestMatchers("/register", "/css/**", "/js/**", "/images/**","/favicon.ico","/css/ptrn.png","/registreer", "/users/**", "/user/**", "/fragments/**", "/taskform", "/tasks/**", "offer/**", "/taskGebode/**", "/toewijzing","/favicon.ico" ).permitAll()
				.requestMatchers("/newJob", "/klant/**", "/taskDetail/**", "/taskedite", "/taskDelete", "/taskDelete2","/taskReview/**","/finalizeTask","/gebodeTask","/klant/review").hasRole("klant")
				.requestMatchers("/bodIntrekken","/completeTask/**","/takeTask").hasRole("klusjesman")
				.requestMatchers("/","/index","/home","/info", "/edit/profiel", "/klusjesman/profile", "/taskGebode/**", "/task/**", "/profile").authenticated()
				
				//.anyRequest().permitAll()
			)
		
			.formLogin(form -> form
					.loginPage("/login").permitAll()
					.defaultSuccessUrl("/", true) // de true is er dat het altijd naar / gaat wijzen ondanks dat je /profile had ingetikt
			)
			
			.logout (logout -> logout
					.logoutSuccessUrl("/")
			);
		return http.build();
	}
	
	@Autowired
	public void dbauth(AuthenticationManagerBuilder auth) throws Exception{
		auth.jdbcAuthentication().dataSource(dataSource)
		.usersByUsernameQuery("select email, passwd, 1 from customers where email = ?")
		.authoritiesByUsernameQuery("select email, authority from authorities where email = ?");
	}
}
