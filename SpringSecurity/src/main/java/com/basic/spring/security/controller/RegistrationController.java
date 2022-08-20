package com.basic.spring.security.controller;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.weaver.NewConstructorTypeMunger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.basic.spring.security.entity.Employee;
import com.basic.spring.security.event.RegistrationCompleteEvent;
import com.basic.spring.security.model.EmployeeModel;
import com.basic.spring.security.service.EmployeeService;

@RestController

public class RegistrationController {
	@Autowired
	EmployeeService employeeService;
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@PostMapping("/register")
	public String registerEmployee(@RequestBody EmployeeModel empModel,final HttpServletRequest request) {
		Employee emp=employeeService.registerEmployee(empModel);
		//to create url that will send on email
		publisher.publishEvent(new RegistrationCompleteEvent(emp,applicationUrl(request)));
		return "success";
		
		}

	private String applicationUrl(HttpServletRequest request) {
		
		return "http://"+
		request.getServerName()+":"+request.getServerPort()+request.getContextPath();
	}
	@GetMapping("/verifyRegistration")
	public String verifyRegisteration(@RequestParam("token") String token){
		
		String result=employeeService.validateVerificationToken(token);
		if(result.equalsIgnoreCase("valid")) {
			return "employee verified successfully";
		}
		return "Not Valid employee";
		}
		
	
	
	

}
