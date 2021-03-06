package com.lpl.security.browser;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import com.lpl.security.core.properties.SecurityProperties;
import com.lpl.security.core.validate.code.ValidateCodeFilter;
import com.lpl.security.core.validate.code.image.ImageCodeFilter;
import com.lpl.security.core.validate.code.sms.SmsCodeAuthenticationSecurityConfig;
import com.lpl.security.core.validate.code.sms.SmsCodeFilter;

@Configuration
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private SecurityProperties securityProperties;
	
	@Autowired
	private AuthenticationSuccessHandler lplAuthenticationSuccessHandler;
	
	@Autowired 
	private AuthenticationFailureHandler lplAuthenticationFailureHandler;

	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	/**
	 * 记住我配置
	 * @return
	 */
	@Bean
	public PersistentTokenRepository persistentTokenRepository() {
		JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
		tokenRepository.setDataSource(dataSource);
		//启动的时候自动创建表
		//tokenRepository.setCreateTableOnStartup(true);
		return tokenRepository;
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		//图片验证码过滤验证码过滤
		ImageCodeFilter imageCodeFilter = new ImageCodeFilter();
		imageCodeFilter.setAuthenticationFailureHandler(lplAuthenticationFailureHandler);
		imageCodeFilter.setSecurityProperties(securityProperties);
		imageCodeFilter.afterPropertiesSet();
		//短信验证码过滤
		SmsCodeFilter smsCodeFilter = new SmsCodeFilter();
		smsCodeFilter.setAuthenticationFailureHandler(lplAuthenticationFailureHandler);
		smsCodeFilter.setSecurityProperties(securityProperties);
		smsCodeFilter.afterPropertiesSet();
		
		http.addFilterBefore(smsCodeFilter, UsernamePasswordAuthenticationFilter.class)	//自定义的一个图片过滤器
			.addFilterBefore(imageCodeFilter, UsernamePasswordAuthenticationFilter.class)	//自定义的一个短信验证码过滤器
			.formLogin()	//表单登陆
				.loginPage("/authentication/require")	//指定登陆页面所在的位置，调到自定义的controller上
				.loginProcessingUrl("/authentication/form")
				.successHandler(lplAuthenticationSuccessHandler)
				.failureHandler(lplAuthenticationFailureHandler)
			.and()
				/**
				 * 设置remember-me配置
				 */
				.rememberMe()
				.tokenRepository(persistentTokenRepository())
				.tokenValiditySeconds(securityProperties.getBrowser().getRememberMeSecond())
				.userDetailsService(userDetailsService)
			.and()
			.authorizeRequests()	//对请求进行授权
			.antMatchers("/authentication/require",
					securityProperties.getBrowser().getLoginPage(),
					"/code/*").permitAll()	//当访问前面的页面的时候，不需要进行认证
			.anyRequest()	//任何请求
			.authenticated() //都需要身份认证
			.and()
			.csrf().disable()
			.apply(smsCodeAuthenticationSecurityConfig);
	}
	
}
