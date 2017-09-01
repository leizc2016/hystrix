package com.lzc.filter;

import java.io.IOException;
import java.io.PrintStream;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class HystrixFilter implements Filter {
	public void destroy() {
	}

	public void doFilter(ServletRequest req, ServletResponse rep, FilterChain chain) throws IOException,
			ServletException {
		FilterHystrixCommand filterHystrixCommand = new FilterHystrixCommand(req, rep, chain);
		filterHystrixCommand.execute();
	}

	public void init(FilterConfig arg0) throws ServletException {
		System.out.println("HystrixFilter 开始执行了");
	}
}