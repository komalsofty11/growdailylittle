package com.basic.spring.security.service;

import com.basic.spring.security.entity.Employee;
import com.basic.spring.security.model.EmployeeModel;

public interface EmployeeService {

	Employee registerEmployee(EmployeeModel empModel);

	void saveVerificationTokenForEmployee(String token, Employee employee);

	String validateVerificationToken(String token);

}
