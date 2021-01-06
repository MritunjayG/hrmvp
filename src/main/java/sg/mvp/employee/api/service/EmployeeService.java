package sg.mvp.employee.api.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import sg.mvp.employee.api.model.Employee;
import sg.mvp.employee.api.repository.EmployeeRepository;

@Service
public class EmployeeService {

	private static final Logger logger = LoggerFactory.getLogger(EmployeeService.class);

	@Autowired
	private EmployeeRepository employeeRepository;

	public List<Employee> getAllEmployees() {
		logger.info("Entered into getAllEmployees...");
		return employeeRepository.findAll();
	}

	public Employee getEmployeeById(String empId) {
		logger.info("Entered into getEmployeeById..." + empId);
		Employee employee = null;
		Optional<Employee> emp = employeeRepository.findById(empId);
		if (emp.isPresent()) {
			employee = emp.get();
		}
		return employee;
	}

	public void deleteEmployeeById(String empId) {
		logger.info("Entered into deleteEmployeeById..." + empId);
		employeeRepository.deleteById(empId);
	}

	public Employee findByLogin(String login) {
		logger.info("Entered into findByLogin..." + login);
		Employee employee = null;
		employee = employeeRepository.findByLogin(login);
		return employee;
	}

	public Employee updateEmployee(Employee employee) {
		logger.info("Entered into updateEmployee..." + employee);
		employee = employeeRepository.save(employee);
		return employee;
	}

	public Employee createEmployee(Employee employee) {
		logger.info("Entered into createEmployee..." + employee);
		employee = employeeRepository.save(employee);
		return employee;
	}

	public List<Employee> findEmployeesBySalaryRange(Double minSalary, Double maxSalary, Sort by) {
		logger.info("Entered into findBySalaryRange... minSalary:" + minSalary + ", maxSalary :" + maxSalary + ", by :"
				+ by);
		List<Employee> employeeList = employeeRepository.findBySalaryRange(minSalary, maxSalary, by);
		return employeeList;
	}

	public List<Employee> loadEmployees(List<Employee> employees) {
		logger.info("Entered into loadEmployees..." + employees);
		employees = employeeRepository.saveAll(employees);
		return employees;
	}

}
