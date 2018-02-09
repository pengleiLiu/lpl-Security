package com.lpl.demo.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lpl.demo.dto.User;
import com.lpl.demo.dto.UserQueryCondition;
import com.lpl.demo.exception.UserNotExistException;
import com.lpl.security.browser.MyUserDetailsService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value="/users")
public class UserController {
	
	
	@GetMapping
	//@JsonView(User.UserSimpleView.class)
	//@ApiOperation(value = "用户查询服务")
	public List<User> query(UserQueryCondition condition,
			@PageableDefault(page = 2, size = 17, sort = "username,asc") Pageable pageable) {

		System.out.println(ReflectionToStringBuilder.toString(condition, ToStringStyle.MULTI_LINE_STYLE));

		System.out.println(pageable.getPageSize());
		System.out.println(pageable.getPageNumber());
		System.out.println(pageable.getSort());

		List<User> users = new ArrayList<>();
		users.add(new User());
		users.add(new User());
		users.add(new User());
		return users;
	}

	/**
	 * @Valid进行验证，根据实体类中设置的注解进行验证。
	 * BindingResult errors 返回错误信息
	 * @param user
	 * @return
	 */
	@ApiOperation(value="创建用户", notes="")
	@PostMapping
	public User create(@Valid @RequestBody User user,BindingResult errors) {
		
		if(errors.hasErrors()) {
			errors.getAllErrors().stream().forEach(error -> System.out.println(error.getDefaultMessage()));
		}
		
		System.out.println(user.getId());
		System.out.println(user.getUsername());
		System.out.println(user.getPassword());
		System.out.println(user.getBirthday());

		user.setId("1");
		return user;
	}
	/**
	 * 获取用户信息
	 * @param id
	 * @return
	 */
	@ApiOperation(value="获取用户信息", notes="")
	@GetMapping("/{id:\\d+}")
	public User getUser(@PathVariable String id) {
		
//		throw new RuntimeException(id);
		User user = new User();
		System.out.println(id);
		user.setUsername("zhangsan");
		return user;
	}
	
	/**
	 * 修改用户
	 * @param user
	 * @param errors
	 * @return
	 */
	@PutMapping("{id:\\d+}")
	public User update(@Valid @RequestBody User user,BindingResult errors) {
		
		if(errors.hasErrors()) {
			errors.getAllErrors().stream().forEach(error -> System.out.println(error.getDefaultMessage()));
		}
		
		System.out.println(user.getId());
		System.out.println(user.getUsername());
		System.out.println(user.getPassword());
		System.out.println(user.getBirthday());

		user.setId("1");
		return user;
	}
	/**
	 * 删除用户
	 * @param id
	 */
	@DeleteMapping("/{id:\\d+}")
	public void delete(@PathVariable String id) {
		System.out.println(id);
	}
}
