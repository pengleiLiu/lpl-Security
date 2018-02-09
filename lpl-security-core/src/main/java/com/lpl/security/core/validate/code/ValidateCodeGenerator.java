package com.lpl.security.core.validate.code;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * 
 * @author lpl
 *
 */
public interface ValidateCodeGenerator {

	ValidateCode generate(ServletWebRequest request);
	
	
}
