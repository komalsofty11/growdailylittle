package com.basic.spring.security.service;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.basic.spring.security.entity.Employee;
import com.basic.spring.security.entity.VerificationToken;
import com.basic.spring.security.model.EmployeeModel;
import com.basic.spring.security.repository.EmployeeRepository;
import com.basic.spring.security.repository.VerificationTokenRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService {
	@Autowired
	private EmployeeRepository employeeRepository;
	@Autowired
	private VerificationTokenRepository verificationTokenRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public Employee registerEmployee(EmployeeModel empModel) {
		Employee employee = new Employee();
		employee.setEmail(empModel.getEmail());
		employee.setFirstName(empModel.getFirstName());
		employee.setLastName(empModel.getLastName());
		employee.setRole("USER");
		employee.setPassword(passwordEncoder.encode(empModel.getPassword()));
		employeeRepository.save(employee);
		return employee;
	}

	@Override
	public void saveVerificationTokenForEmployee(String token, Employee employee) {
		VerificationToken verificationToken = new VerificationToken(employee, token);
		verificationTokenRepository.save(verificationToken);

	}

	@Override
	public String validateVerificationToken(String token) {
		VerificationToken verificationToken = verificationTokenRepository.findByToken(token);
		if (verificationToken == null) {
			return "invalid token";

		}
		Employee employee = verificationToken.getEmployee();
		Calendar calendar = Calendar.getInstance();

		if (verificationToken.getExpirationTime().getTime() - calendar.getTime().getTime() <= 0) {

			verificationTokenRepository.delete(verificationToken);
			return "expired";
		}
		employee.setEnabled(true);
		employeeRepository.save(employee);
		return "valid";

	}

}
