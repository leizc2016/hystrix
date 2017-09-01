package com.lzc.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lzc.filter.MethodHystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

//import com.lzc.accout.LoginService;

@Controller
@RequestMapping("/demo/")
public class DemoController {
	private static int succ =0;
	private static Byte lock = new Byte("1");
	
	
	@RequestMapping("main")
	public String main() throws InterruptedException {
		Thread.sleep(5000);
		return "main";

	}

	@RequestMapping("method")
	public String method(HttpServletRequest request, HttpServletResponse response) throws InterruptedException {
		String param1 = request.getParameter("param1");
		String param2 = request.getParameter("param2");
		MethodHystrixCommand methodHystrixCommand = new MethodHystrixCommand(param1, param2);
		String result = methodHystrixCommand.execute();
		Thread.sleep(5000);
		System.out.println(result);
		return "main";

	}
	
	
	@RequestMapping("anno")
	@HystrixCommand(fallbackMethod = "okFallback", commandProperties = {
			@HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds",value="2000"),
			@HystrixProperty(name="circuitBreaker.requestVolumeThreshold",value="5")
	})
	public String anno(HttpServletRequest request, HttpServletResponse response) throws InterruptedException {
		Thread.sleep(1000);
		long id=Thread.currentThread().getId();
		System.out.println("vvv 成功了 ["+id+" ]");
		synchronized (lock) {
			succ++;
			System.out.println(succ);
		}
		
		return "main";

	}
	
	protected Object getFallback() {
		long id=Thread.currentThread().getId();
		System.out.println("失败了 ["+id+" ]");
		return null;
	}
	
	public String okFallback(HttpServletRequest req,HttpServletResponse rep) {  
        System.out.println("execute okFallback!");  
        return "fallback";  
    }
    public String okFallback() {
        return "fallbackssssss";
    }

}
