package com.springbootRest.crudDemo.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springbootRest.crudDemo.entity.Employee;
import com.springbootRest.crudDemo.service.EmployeeService;

@RestController
@RequestMapping("/testing")
public class EmployeeRESTController {
	
	//Refactoring the code so instead of using the DAO directly, it'll use the
	//service layer, which uses all of the DAO methods anyway by calling them
	//is its own methods (named the same)
	
	//old code
	//private EmployeeDAO myEmployeeDAO;
	
	private EmployeeService employeeService;
	
	@Value("${foo.intro}")
	private String introduction;
	
	//Dependency injection (DAO methods used by Service, which did an dependency injection for EmployeeDAO)
	@Autowired
	public EmployeeRESTController(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}
	
	@GetMapping(value="")
	public String intro() {
		return introduction;
	}
	
	//expose "/employees" and return list of employees
	@GetMapping("/employees")
	public List<Employee> findAllEmployees() {
		return employeeService.findAllEmployees();
	}
	
	//Get Request (read data)
	//Add mapping for GET /employees/{employeeId}
	//What is bracketed in the previous comment is known as a path variable
	@GetMapping("/employees/{id}")
	public Employee getEmployee(@PathVariable int id) {
		
		//Call upon the findByID method from EmployeeService which basically just calls
		//the same method from EmployeeDAO to get the Employee object with the parameterized id
		//if said object even exists
		Employee employeeByID = employeeService.findByID(id);
		
		//If the Employee object with the given id doesn't exist, throw a RTE
		if(employeeByID == null) {
			throw new RuntimeException("ID of " + id + " Not Found!");
		}
		
		//If the object exists, simply return it
		return employeeByID;
	}
	
	//POST Request (insert data)
	//RequestBody requests a JSON body form from a service like PostMan, and will add the content to the DB
	@PostMapping("/employees")
	public Employee addEmployee(@RequestBody Employee empID) {
		
		//Set the primary ID to 0, which actually assigns a new PK number
		empID.setId(0);
		
		//Call upon EmployeeService's method to save the employee
		employeeService.saveEmployee(empID);
		
		//Finally return that new Employee object
		return empID;
	}
	
	//PUT Request (update data)
	//Updating the Employee
	@PutMapping("/employees")
	public Employee updateEmployee(@RequestBody Employee empID) {
		
		//No need to change PK since we are not making a new Employee object in the DB, simply updating an existing one
		//Saving changes made to the Employee object
		employeeService.saveEmployee(empID);
		
		//Returning the updated Employee
		return empID;
	}
	
	//DELETE Request (delete data)
	@DeleteMapping("/employees/{id}")
	public String deleteEmployee(@PathVariable int id) {
		//Call upon the findByID method from EmployeeService which basically just calls
		//a method that goes by the same name from EmployeeDAO to get the Employee object with the parameterized id
		//if said object even exists
		Employee employeeByID = employeeService.findByID(id);
		
		//If the Employee object with the given id doesn't exist, throw a RTE
		if(employeeByID == null) {
				throw new RuntimeException("ID of " + id + " Not Found!");
		}
		//Just delete the specified Employee
		employeeService.deleteEmployee(id);
		
		//Finally return a string notifying us that everything was in order.
		return "Employee " + id + " has been deleted from the DB";
	}
}
