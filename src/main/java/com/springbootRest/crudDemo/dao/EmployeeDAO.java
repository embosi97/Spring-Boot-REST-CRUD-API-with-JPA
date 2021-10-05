package com.springbootRest.crudDemo.dao;

import java.util.List;

import com.springbootRest.crudDemo.entity.Employee;

public interface EmployeeDAO {
	
	//This method will display all of the Employees in the MySQL DB
	public List<Employee> findAllEmployees();
	
	//Will find an Employee based on ID
	public Employee findByID(int id);
	
	//Saving a new Employee into the DB
	public void saveEmployee(Employee employee);
	
	//Delete an Employee from the DB by the ID
	public void deleteEmployee(int id);
	
	//Adding an Employee into the Database (We don't need to provide a Primary Key)

}
