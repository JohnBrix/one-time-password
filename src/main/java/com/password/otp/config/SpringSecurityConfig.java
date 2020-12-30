package com.password.otp.config;


import com.password.otp.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
	//dont mind the red its not error
	@Autowired
	private AccessDeniedHandler accessDeniedHandler;
	
	@Autowired
	private MyUserDetailsService myUserDetailsService;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.csrf().disable().authorizeRequests()
			.antMatchers("/", "/css/**","/img/**","/webjars/**").permitAll()
			.antMatchers("/admin/**").hasAnyRole("ADMIN")
			.antMatchers("/user/**").hasAnyRole("USER")
			.anyRequest().authenticated() //Rest of all request need authentication			 
        .and()
        .formLogin()
			.loginPage("/login")  //Loginform all can access .. 
			.defaultSuccessUrl("/dashboard")
			.failureUrl("/login?error")
			.permitAll()
			.and()
        .logout()
			.permitAll()
			.and()
        .exceptionHandling().accessDeniedHandler(accessDeniedHandler);
			
	}
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		auth.userDetailsService(myUserDetailsService).passwordEncoder(passwordEncoder);;
    }
	
}
