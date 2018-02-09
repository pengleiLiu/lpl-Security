package com.lpl.demo.validator;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * 写一个自定义的验证注解需要有下面三个接口
 * @author lpl
 *
 */
@Target({ElementType.METHOD,ElementType.FIELD})		
@Retention(RetentionPolicy.RUNTIME)		
@Constraint(validatedBy = MyConstraintValidator.class)	//约束
public @interface MyConstraint {

	String message();

	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };
}
