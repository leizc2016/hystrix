package com.lzc.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lzc.filter.MethodHystrixCommand;

//import com.lzc.accout.LoginService;

@Controller
@RequestMapping("/demo/")
public class DemoController {

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

}
