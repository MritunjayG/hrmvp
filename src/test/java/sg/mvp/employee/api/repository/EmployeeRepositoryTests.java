package sg.mvp.employee.api.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import sg.mvp.employee.api.model.Employee;

@DataJpaTest
public class EmployeeRepositoryTests {

	@Autowired
	private EmployeeRepository employeeRepository;

	
	@Test
	public void whenFindById_thenReturnEmployee() {
		// when
		Optional<Employee> employee = employeeRepository.findById("e9999");
		//then
		assertThat(employee.isPresent()).isEqualTo(true);
	}
	
	@Test
	public void whenFindByLogin_thenReturnEmployee() {
		// when
		Employee employee = employeeRepository.findByLogin("girimrit");
		

		// then
		assertThat(employee.getLogin()).isEqualTo("girimrit");
	}

}
