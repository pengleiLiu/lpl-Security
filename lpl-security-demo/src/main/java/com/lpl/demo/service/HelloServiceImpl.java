package com.lpl.demo.service;

import org.springframework.stereotype.Service;
/**
 * 
 * @author lpl
 *
 */
@Service
public class HelloServiceImpl implements HelloService {

	@Override
	public String greeting(String name) {
		System.out.println("greeting");
		return "hello" + name;
	}

}
