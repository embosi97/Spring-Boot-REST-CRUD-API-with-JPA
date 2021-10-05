package com.springbootRest.crudDemo.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.springbootRest.crudDemo.dao.EmployeeDAO;
import com.springbootRest.crudDemo.entity.Employee;

@Service
public class EmployeeServiceImplementation implements EmployeeService {
	
	
	private EmployeeDAO employeeDAO;
	
	public EmployeeServiceImplementation(EmployeeDAO employeeDAO) {
		this.employeeDAO = employeeDAO;
	}
	
	@Override
	@Transactional
	public List<Employee> findAllEmployees() {
		return employeeDAO.findAllEmployees();
	}

	@Override
	@Transactional
	public Employee findByID(int id) {
		return employeeDAO.findByID(id);
	}

	@Override
	@Transactional
	public void saveEmployee(Employee employee) {
		employeeDAO.saveEmployee(employee);

	}

	@Override
	@Transactional
	public void deleteEmployee(int id) {
		employeeDAO.deleteEmployee(id);

	}

}
