package sg.mvp.employee.api.application.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import sg.mvp.employee.api.application.form.EmployeeForm;
import sg.mvp.employee.api.facade.EmployeeFacade;
import sg.mvp.employee.api.framework.ApiResponse;
import sg.mvp.employee.api.framework.controller.BaseControllerAbstract;

@RestController
@Scope("prototype")
public class EmployeeController extends BaseControllerAbstract {

	private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

	@Autowired
	private EmployeeFacade employeeFacade;
	
	@RequestMapping(value = "/users/upload", method = RequestMethod.POST, consumes = { "multipart/form-data" })
	public ResponseEntity<ApiResponse> uploadUsers(@RequestParam MultipartFile file) {
		ApiResponse apiResponse = employeeFacade.loadUsersFromCsvFile(file);
		return returnResponseEntity(apiResponse);
	}

	@GetMapping("/users")
	public ResponseEntity<ApiResponse> getUsersBySalaryRange(
			@RequestParam(required = false, defaultValue = "0") Double minSalary,
			@RequestParam(required = false, defaultValue = "4000.00") Double maxSalary,
			@RequestParam(defaultValue = "id,asc") String[] sort) {
		ApiResponse apiResponse = employeeFacade.findEmployeesBySalaryRange(minSalary, maxSalary, sort);
		return returnResponseEntity(apiResponse);
	}

	@GetMapping("/users/{id}")
	public ResponseEntity<ApiResponse> getUserById(@PathVariable("id") String id) {
		ApiResponse apiResponse = employeeFacade.getUserById(id);
		return returnResponseEntity(apiResponse);
	}

	@DeleteMapping("/users/{id}")
	public ResponseEntity<ApiResponse> deleteUserById(@PathVariable("id") String id) {
		logger.info("Entered into deleteUserById...");
		ApiResponse apiResponse = employeeFacade.deleteUserById(id);
		return returnResponseEntity(apiResponse);
	}

	@PutMapping("/users/{id}")
	public ResponseEntity<ApiResponse> updateUser(@RequestBody EmployeeForm employeeForm) {
		logger.info("Entered into updateUser...");
		ApiResponse apiResponse = employeeFacade.updateUser(employeeForm);
		return returnResponseEntity(apiResponse);
	}

	@PostMapping("/users")
	public ResponseEntity<ApiResponse> createUser(@RequestBody EmployeeForm employeeForm) {
		logger.info("Inside createUser...");
		ApiResponse apiResponse = employeeFacade.createUser(employeeForm);
		return returnResponseEntity(apiResponse);
	}
	
	/*
	 * Bellow end point extra for development and testing
	 */
	@RequestMapping("/")
	public String index() {
		return "Employee Salary Management Web Service is up :) ";
	}
		
	@GetMapping("/all")
	public ResponseEntity<ApiResponse> getAllUsers() {
		ApiResponse apiResponse = employeeFacade.getAllUsers();
		return returnResponseEntity(apiResponse);
	}	


}
