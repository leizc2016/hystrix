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

public class FilterHystrixCommand extends HystrixCommand {
	private FilterChain chain;
	private ServletRequest req;
	private ServletResponse rep;

	protected FilterHystrixCommand(ServletRequest req, ServletResponse rep, FilterChain chain) {
		super(HystrixCommand.Setter
				.withGroupKey(HystrixCommandGroupKey.Factory.asKey("SemaphoreTestGroup"))
				.andCommandKey(HystrixCommandKey.Factory.asKey("SemaphoreTestKey"))
				.andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("SemaphoreTestThreadPoolKey"))
				.andCommandPropertiesDefaults(
						HystrixCommandProperties.Setter().withExecutionTimeoutInMilliseconds(10000)
								.withExecutionIsolationStrategy(ExecutionIsolationStrategy.SEMAPHORE)
								.withExecutionIsolationSemaphoreMaxConcurrentRequests(20)));

		this.chain = chain;
		this.req = req;
		this.rep = rep;
	}

	protected Object run() throws Exception {
		long id=Thread.currentThread().getId();
		System.out.println("开始执行 ["+id+" ]");
		this.chain.doFilter(this.req, this.rep);
		System.out.println("执行完毕 ["+id+" ]");
		return 1;
	}

	protected Object getFallback() {
		long id=Thread.currentThread().getId();
		System.out.println("失败了 ["+id+" ]");
		return null;
	}
}