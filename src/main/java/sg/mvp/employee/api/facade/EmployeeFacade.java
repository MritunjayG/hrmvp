package sg.mvp.employee.api.facade;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import sg.mvp.employee.api.application.form.EmployeeForm;
import sg.mvp.employee.api.facade.helper.CSVHelper;
import sg.mvp.employee.api.framework.ApiResponse;
import sg.mvp.employee.api.model.Employee;
import sg.mvp.employee.api.service.EmployeeService;

@Service
public class EmployeeFacade {

	private static final Logger logger = LoggerFactory.getLogger(EmployeeFacade.class);

	@Autowired
	private EmployeeService employeeService;

	public ApiResponse getAllUsers() {
		ApiResponse apiResponse = new ApiResponse();
		List<Employee> employees = new ArrayList<Employee>();
		employees = employeeService.getAllEmployees();
		apiResponse.setResult(employees);
		apiResponse.setStatus("200 -Ok");
		return apiResponse;
	}

	public ApiResponse getUserById(String empId) {
		ApiResponse apiResponse = new ApiResponse();
		Employee employee = new Employee();
		employee = employeeService.getEmployeeById(empId);
		apiResponse.setResult(employee);
		apiResponse.setStatus("200 -Ok");
		return apiResponse;
	}

	public ApiResponse deleteUserById(String id) {
		String message = "";
		ApiResponse apiResponse = new ApiResponse();
		Employee employee = employeeService.getEmployeeById(id);
		if (employee != null) {
			employeeService.deleteEmployeeById(id);
			message = "Successfully deleted";
		} else {
			message = "No such employee";
		}
		apiResponse.setStatus(message);
		return apiResponse;
	}

	public ApiResponse updateUser(EmployeeForm employeeForm) {
		String message = "";
		ApiResponse apiResponse = new ApiResponse();

		if (employeeForm.getSalary() <= 0) {
			message = "Invalid salary"; // HttpStatus.BAD_REQUEST
			apiResponse.setStatus(message);
			return apiResponse;
		}

		Employee employee = employeeService.getEmployeeById(employeeForm.getId());
		if (employee == null) {
			message = "No such employee"; // HttpStatus.BAD_REQUEST
			apiResponse.setStatus(message);
			return apiResponse;
		}

		// Validate login : only if login value has changed for the User
		if (!employeeForm.getLogin().equalsIgnoreCase(employee.getLogin())) {
			employee = employeeService.findByLogin(employeeForm.getLogin());
			if (employee != null) {
				message = "Employee login not unique"; // HttpStatus.BAD_REQUEST
				apiResponse.setStatus(message);
				return apiResponse;
			}
		}

		Employee emp = new Employee(employeeForm.getId(), employeeForm.getLogin(), employeeForm.getName(),
				employeeForm.getSalary(), employeeForm.getStartDate());

		emp = employeeService.updateEmployee(emp);
		message = "Successfully updated"; // HttpStatus.CREATED
		apiResponse.setStatus(message);
		apiResponse.setResult(emp);
		return apiResponse;
	}

	public ApiResponse createUser(EmployeeForm employeeForm) {
		String message = "";
		ApiResponse apiResponse = new ApiResponse();

		if (employeeForm.getSalary() <= 0) {
			message = "Invalid salary"; // HttpStatus.BAD_REQUEST
			apiResponse.setStatus(message);
			return apiResponse;
		}

		Employee employee = employeeService.getEmployeeById(employeeForm.getId());
		if (employee != null) {
			message = "Employee ID already exists"; // HttpStatus.BAD_REQUEST
			apiResponse.setStatus(message);
			return apiResponse;
		}

		employee = employeeService.findByLogin(employeeForm.getLogin());
		if (employee != null) {
			message = "Employee login not unique"; // HttpStatus.BAD_REQUEST
			apiResponse.setStatus(message);
			return apiResponse;
		}

		Employee emp = new Employee(employeeForm.getId(), employeeForm.getLogin(), employeeForm.getName(),
				employeeForm.getSalary(), employeeForm.getStartDate());

		emp = employeeService.createEmployee(emp);
		message = "Successfully created"; // HttpStatus.CREATED
		apiResponse.setStatus(message);
		apiResponse.setResult(emp);
		return apiResponse;
	}

	public ApiResponse findEmployeesBySalaryRange(Double minSalary, Double maxSalary, String[] sort) {

		String message = "";
		ApiResponse apiResponse = new ApiResponse();
		List<Order> orders = new ArrayList<Order>();

		if (sort[0].contains(",")) {
			// will sort more than 2 fields
			// sortOrder="field, direction"
			for (String sortOrder : sort) {
				String[] _sort = sortOrder.split(",");
				orders.add(new Order(getSortDirection(_sort[1]), _sort[0]));
			}
		} else {
			// sort=[field, direction]
			orders.add(new Order(getSortDirection(sort[1]), sort[0]));
		}

		List<Employee> employees = employeeService.findEmployeesBySalaryRange(minSalary, maxSalary, Sort.by(orders));

		if (employees.isEmpty()) {
			message = "No Content"; // HttpStatus.NO_CONTENT
			apiResponse.setStatus(message);
			return apiResponse;
		}

		message = "Ok"; // OK
		apiResponse.setStatus(message);
		apiResponse.setResult(employees);
		return apiResponse;
	}

	private Sort.Direction getSortDirection(String direction) {
		if (direction.equals("asc")) {
			return Sort.Direction.ASC;
		} else if (direction.equals("desc")) {
			return Sort.Direction.DESC;
		}

		return Sort.Direction.ASC;
	}

	public ApiResponse loadUsersFromCsvFile(MultipartFile file) {
		String message = "";
		ApiResponse apiResponse = new ApiResponse();
		List<Employee> employees = new ArrayList<Employee>();
		String fileName;
		InputStream is;

		if (file.isEmpty()) {
			message = "Failed to store empty file!";
			apiResponse.setStatus(message);
			return apiResponse;
		}

		try {
			fileName = file.getOriginalFilename();
			logger.info("Uploaded file name : " + fileName);

			is = file.getInputStream();
			employees = CSVHelper.csvToEmployees(is);
			logger.info("Parsing and validation completed ");

			logger.info("Load Employee call started ");
			employees = employeeService.loadEmployees(employees);
			logger.info("Load Employee call completed ");

			message = "Ok"; // OK
		} catch (IOException e) {
			message = "IOException has occured, see log for stackTrace.";
			e.printStackTrace();
		}

		apiResponse.setStatus(message);
		apiResponse.setResult(employees);
		return apiResponse;
	}

}
