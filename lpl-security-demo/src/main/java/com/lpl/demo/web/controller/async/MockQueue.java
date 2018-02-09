package com.lpl.demo.web.controller.async;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class MockQueue {

	private String placeOrder;
	private String completeOrder;
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	public String getPlaceOrder() {
		return placeOrder;
	}

	public void setPlaceOrder(String placeOrder) throws InterruptedException {
		new Thread(()-> {
			logger.info("接到订单请求" + placeOrder);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.completeOrder = placeOrder;
			logger.info("下单请求处理完毕" + completeOrder);
		}).start();; 
	}

	public String getCompleteOrder() {
		return completeOrder;
	}

	public void setCompleteOrder(String completeOrder) {
		this.completeOrder = completeOrder;
	}
	
	
}
