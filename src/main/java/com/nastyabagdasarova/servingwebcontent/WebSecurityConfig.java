package com.nastyabagdasarova.servingwebcontent;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {
	@Autowired
	private DataSource dataSource;
	
	@Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/public/**").addResourceLocations("file:public/");
        
        registry.addResourceHandler("/private/**").addResourceLocations("file:private/");
        
        registry.addResourceHandler("/video/**").addResourceLocations("file:video/");
    }
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		
		String[] staticResources  =  {
		        "/dist/**",
		        "/js/**",
		        "/video.js/**",
		        "/images/**",
		        "/font/**",
		        "/scss/**",
		        "/main.css",
		        "/main_mobile.css",
		        "/favicon.ico",
		        "/public/**",
		    };
		
		http
			.authorizeRequests()
				.antMatchers("/", "/strip","/exotic","/api/**","/acrobatics","/registration","/forget","/rest/**",
						"/cart/**","/payment/result","/chat/admin","/contacts","/contacts/send","/api/videoqr").permitAll()
	            .antMatchers(staticResources).permitAll()
				.anyRequest().authenticated()
			.and()
				.formLogin()
				.defaultSuccessUrl("/account", true)
				.loginPage("/login")
				.permitAll()
			.and()
		        .rememberMe()
	            .alwaysRemember(true)
	            .tokenValiditySeconds(86400 * 30)
	            .rememberMeCookieName("remember")//mouni
	            .key("djklHFks898990*&a98jdahuauiofsk")
			.and()
				.logout()
				.logoutUrl("/logout")
				.logoutSuccessUrl("/login")
				.permitAll();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// TODO Auto-generated method stub

		auth.jdbcAuthentication()
			.dataSource(dataSource)
			//.passwordEncoder(NoOpPasswordEncoder.getInstance())
			.passwordEncoder(passwordEncoder())
			.usersByUsernameQuery("SELECT username, password, active FROM user WHERE username = ?")
			.authoritiesByUsernameQuery("SELECT user.username, user.roles FROM user "
					+ "INNER JOIN user_role ON user.id = user_role.id_of_user WHERE user.username = ?");
		
	}

	/*
	@Bean
	@Override
	public UserDetailsService userDetailsService() {
		@SuppressWarnings("deprecation")
		UserDetails user =
			 User.withDefaultPasswordEncoder()
				.username("user")
				.password("password")
				.roles("USER")
				.build();

		return new InMemoryUserDetailsManager(user);
	}
	*/
	
	@Bean
	public PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}
	
}
