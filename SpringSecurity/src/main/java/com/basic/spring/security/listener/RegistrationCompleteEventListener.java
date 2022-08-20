package com.basic.spring.security.listener;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.basic.spring.security.entity.Employee;
import com.basic.spring.security.event.RegistrationCompleteEvent;
import com.basic.spring.security.service.EmployeeService;

import lombok.extern.slf4j.Slf4j;
@Component
@Slf4j
public class RegistrationCompleteEventListener implements ApplicationListener<RegistrationCompleteEvent> {
 
private static final Logger log = LoggerFactory.getLogger(RegistrationCompleteEventListener.class);

	@Autowired
	private EmployeeService employeeService;
	
	@Override
	public void onApplicationEvent(RegistrationCompleteEvent event) {
		
		// create the verification token for the employee
		
		Employee employee=event.getEmployee();
		String token=UUID.randomUUID().toString();//when employee hit the url in the email will match the token saved in db

		employeeService.saveVerificationTokenForEmployee(token,employee);
		
		//send mail to employee
		String url=event.getApplicationUrl()+"/verifyRegistration?token="+token;
		log.info("click the link to verify your account:{}",url);
		
	}

}
