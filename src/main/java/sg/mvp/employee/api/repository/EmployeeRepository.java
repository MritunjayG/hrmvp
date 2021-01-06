package sg.mvp.employee.api.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import sg.mvp.employee.api.model.Employee;


public interface EmployeeRepository extends JpaRepository<Employee, String> {

//    List<Employee> findByName(String name);

    @Query("SELECT e FROM Employee e WHERE e.salary >= ?1 AND e.salary < ?2")
    List<Employee> findBySalaryRange(double minSalary, double maxSalary, Sort sort);
    
    
    Employee findByLogin(String login);

}
