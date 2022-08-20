package com.basic.spring.security.event;

import org.springframework.context.ApplicationEvent;

import com.basic.spring.security.entity.Employee;

public class RegistrationCompleteEvent extends ApplicationEvent {

	private Employee employee;

	// url for employees that will be send to them on emails
	private String applicationUrl;

	public RegistrationCompleteEvent(Employee employee, String applicationUrl) {
		super(employee);
		this.employee = employee;
		this.applicationUrl = applicationUrl;

	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public String getApplicationUrl() {
		return applicationUrl;
	}

	public void setApplicationUrl(String applicationUrl) {
		this.applicationUrl = applicationUrl;
	}
	

}
