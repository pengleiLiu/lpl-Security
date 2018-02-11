package com.lpl.security.core.validate.code.image;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import com.lpl.security.core.properties.SecurityProperties;
import com.lpl.security.core.validate.code.ValidateCode;
import com.lpl.security.core.validate.code.ValidateCodeException;
import com.lpl.security.core.validate.code.ValidateCodeProcessor;

public class ImageCodeFilter extends OncePerRequestFilter implements InitializingBean {

	private AuthenticationFailureHandler authenticationFailureHandler;
	
	private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();
	
	private Set<String> urls = new HashSet<>();
	
	private SecurityProperties securityProperties;
	
	private AntPathMatcher pathMatcher = new AntPathMatcher();
	
	@Override
	public void afterPropertiesSet() throws ServletException{
		super.afterPropertiesSet();
		String[] configUrls = StringUtils.splitByWholeSeparatorPreserveAllTokens(securityProperties.getCode().getImage().getUrl(), ",");
		if(ArrayUtils.isNotEmpty(configUrls)) {
			for(String configUrl : configUrls) {
				urls.add(configUrl);
			}
		}
		urls.add("/authentication/form");
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		boolean action = false;
		for(String url : urls) {
			if(pathMatcher.match(url, request.getRequestURI())) {
				action = true;
			}
		}
		
		if(action) {
			try {
				validate(new ServletWebRequest(request));
			} catch (ValidateCodeException e) {
				authenticationFailureHandler.onAuthenticationFailure(request, response, e);
			}
		}
		//继续进行执行
		filterChain.doFilter(request, response);
	}

	private void validate(ServletWebRequest request) throws ServletRequestBindingException {

		ImageCode codeInSession = (ImageCode) sessionStrategy.getAttribute(request, ValidateCodeProcessor.SESSION_KEY_PREFIX + "IMAGE");
	
		String codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(), "imageCode");
		
		if(StringUtils.isBlank(codeInRequest)) {
			throw new ValidateCodeException("验证码的值不能为空");
		}
		
		if(codeInSession == null) {
			throw new ValidateCodeException("验证码不能为空");
		}
		
		if(codeInSession.isExpried()) {
			sessionStrategy.removeAttribute(request, ValidateCodeProcessor.SESSION_KEY_PREFIX + "IMAGE");
			throw new ValidateCodeException("验证码已经过期");
		}
		
		if(!StringUtils.equals(codeInSession.getCode(), codeInRequest)) {
			throw new ValidateCodeException("验证码输入错误");
		}
	
		sessionStrategy.removeAttribute(request, ValidateCodeProcessor.SESSION_KEY_PREFIX + "IMAGE");
	}

	public AuthenticationFailureHandler getAuthenticationFailureHandler() {
		return authenticationFailureHandler;
	}

	public void setAuthenticationFailureHandler(AuthenticationFailureHandler authenticationFailureHandler) {
		this.authenticationFailureHandler = authenticationFailureHandler;
	}

	public SessionStrategy getSessionStrategy() {
		return sessionStrategy;
	}

	public void setSessionStrategy(SessionStrategy sessionStrategy) {
		this.sessionStrategy = sessionStrategy;
	}

	public Set<String> getUrls() {
		return urls;
	}

	public void setUrls(Set<String> urls) {
		this.urls = urls;
	}

	public SecurityProperties getSecurityProperties() {
		return securityProperties;
	}

	public void setSecurityProperties(SecurityProperties securityProperties) {
		this.securityProperties = securityProperties;
	}

	public AntPathMatcher getPathMatcher() {
		return pathMatcher;
	}

	public void setPathMatcher(AntPathMatcher pathMatcher) {
		this.pathMatcher = pathMatcher;
	}
	

}
