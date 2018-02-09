/**
 * 
 */
package com.lpl.demo.web.controller.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.lpl.demo.web.controller.Filter.TimeFilter;
import com.lpl.demo.web.controller.interceptor.TimeInterceptor;


/**
 * @author lpl
 *
 */
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

	@Autowired
	private TimeInterceptor interceptor;
	
	@Override
	public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
		// TODO Auto-generated method stub
		//super.configureAsyncSupport(configurer);
		//callable
//		configurer.registerCallableInterceptors(interceptors);
		//deferred
//		configurer.registerDeferredResultInterceptors(interceptors);
	}
	/**
	 * 将自己配置的拦截器进行注册
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
//		registry.addInterceptor(interceptor);
	}
	
//	@Bean
	public FilterRegistrationBean timeFilter() {
		
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
		TimeFilter timeFilter = new TimeFilter();
		filterRegistrationBean.setFilter(timeFilter);	
		List<String> urls = new ArrayList<>();
		urls.add("/*");
		filterRegistrationBean.setUrlPatterns(urls);
		
		return filterRegistrationBean;
	}
}

