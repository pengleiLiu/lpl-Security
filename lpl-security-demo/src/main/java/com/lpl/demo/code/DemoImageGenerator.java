package com.lpl.demo.code;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import com.lpl.security.core.validate.code.ImageCode;
import com.lpl.security.core.validate.code.ValidateCode;
import com.lpl.security.core.validate.code.ValidateCodeGenerator;
/**
 * 自定义一个图形验证码
 * @author lpl
 *
 */
//@Component("imageCodeGenerator")
public class DemoImageGenerator implements ValidateCodeGenerator {

	/**
	 * 重写我们的ValidateCodeGenerator接口
	 */
	@Override
	public ValidateCode generate(ServletWebRequest request) {
		System.out.println("更高级的图形验证码生成代码");
		return null;
	}

}
