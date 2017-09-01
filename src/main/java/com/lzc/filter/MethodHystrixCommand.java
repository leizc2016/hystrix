package com.lzc.filter;

import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixCommandProperties.ExecutionIsolationStrategy;
import com.netflix.hystrix.HystrixThreadPoolKey;

public class MethodHystrixCommand extends HystrixCommand<String> {

	private String param1;
	
	private String param2;
	
	public MethodHystrixCommand(String param1,String param2) {
		super(HystrixCommand.Setter
				.withGroupKey(HystrixCommandGroupKey.Factory.asKey("SemaphoreTestGroup"))
				.andCommandKey(HystrixCommandKey.Factory.asKey("SemaphoreTestKey"))
				.andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("SemaphoreTestThreadPoolKey"))
				.andCommandPropertiesDefaults(
						HystrixCommandProperties.Setter().withExecutionTimeoutInMilliseconds(10000)
								.withExecutionIsolationStrategy(ExecutionIsolationStrategy.SEMAPHORE)
								.withExecutionIsolationSemaphoreMaxConcurrentRequests(20)));
		this.param1 = param1;
		
		this.param2 =param2;
	}

	protected String run() throws Exception {
		long id=Thread.currentThread().getId();
		Thread.sleep(2000);
		System.out.println("param1="+param1+" ,param2="+param2 + " ["+id+" ]");
		
		return "执行成功了"+" ["+id+" ]";
	}

	protected String  getFallback() {
		long id=Thread.currentThread().getId();
		System.out.println("----error--");
		return "执行失败了"+" ["+id+" ]";
	}
}