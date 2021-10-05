package com.springbootRest.crudDemo.service;

import java.util.List;

import com.springbootRest.crudDemo.entity.Employee;

public interface EmployeeService {
	
	public List<Employee> findAllEmployees();
	
	public Employee findByID(int id);
	
	public void saveEmployee(Employee employee);
	
	public void deleteEmployee(int id);
	
	

}
