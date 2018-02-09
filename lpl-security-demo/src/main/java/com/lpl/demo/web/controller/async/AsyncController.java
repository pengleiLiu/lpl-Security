package com.lpl.demo.web.controller.async;

import java.util.concurrent.Callable;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;


@RestController
public class AsyncController {
	
	@Autowired
	private MockQueue mockQueue;
	
	@Autowired
	private DeferredResultHolder deferredResultHolder;

	private Logger logger = LoggerFactory.getLogger(getClass());
	/**
	 *  RunAble
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/order")
	public Callable<String> order() throws Exception {
		logger.info("主线程开始");
		
		Callable<String> result = new Callable<String>() {
			@Override
			public String call() throws Exception {
				logger.info("副线程开始");
				Thread.sleep(1000);
				logger.info("副线程返回");
				return "success";
			}
		};
		logger.info("主线程返回");
		return result;
	}
	/**
	 * 模仿消息队列的DeferredResult
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/order1")
	public DeferredResult<String> orderMQ() throws Exception {
		logger.info("主线程开始");
		//生成随机数
		String randomNumber = RandomStringUtils.randomNumeric(8);
		mockQueue.setPlaceOrder(randomNumber);
		
		DeferredResult<String> result = new DeferredResult();
		
		deferredResultHolder.getMap().put(randomNumber, result);
		logger.info("主线程返回");
		return result;
	}
}

