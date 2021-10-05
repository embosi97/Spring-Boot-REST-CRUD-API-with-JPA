package com.springbootRest.crudDemo.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.springbootRest.crudDemo.entity.Employee;
//Repository basically means that this class will be used to encapsulate, store, and retrieve a collection of objects 
//Which is what we're doing as we are getting Employee objects from the MySQL DB and doing something with them
@Repository
public class EmployeeDAOImplementHibernate implements EmployeeDAO {

	//define field for entity manager
	private EntityManager entityManager;
	
	// setting up constructor injection
	//Autowiring injects the dependency for us without us having to explicitly define it
	@Autowired
	public EmployeeDAOImplementHibernate(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	@Override
	//This annotation is used to create database transactions
	@Transactional
	public List<Employee> findAllEmployees() {
		//Create a current Hibernate session so we can get the employee columns and rows from the DB
		Session currentHibernateSession = entityManager.unwrap(Session.class);
		
		//Create the query and ensure that we are getting it from the employees table ("FROM EMPLOYEES") and that they're converted into Employee objects 
		Query<Employee> employeeQuery = currentHibernateSession.createQuery("from Employee", Employee.class);
		
		//Execute the above, which will generate a list of Employee objects with the info from the DB, which we will pass as a list object
		List<Employee> employeesList = employeeQuery.getResultList();
		
		//return the results as a List of Employees
		return employeesList;
	}

	@Override
	public Employee findByID(int id) {
		//Get the current Hibernate session
		Session currentSession = entityManager.unwrap(Session.class);
		
		//Get the employee
		Employee employeeByID = currentSession.get(Employee.class, id);
		
		//Return the employee
		return employeeByID;
	}

	@Override
	public void saveEmployee(Employee employee) {
		//Get session
		Session currentSession = entityManager.unwrap(Session.class);
		
		//Save Or Update the parameterized Employee object
		currentSession.saveOrUpdate(employee);
	}

	@Override
	public void deleteEmployee(int id) {
		//Get session
		Session currentSession = entityManager.unwrap(Session.class);
				
		//Create a Query
		Query<Employee> employeeQuery = currentSession.createQuery("delete from Employee where id=:employeeId");
		
		//Set the parameters for the Query we created above, which would delete an Employee of a particular ID
		employeeQuery.setParameter("employeeId", id);
		
		//Executes a delete or update
		employeeQuery.executeUpdate();
		
	}

}
