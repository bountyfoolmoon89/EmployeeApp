import java.util.List;

public interface EmployeeDAO {
    void createEmployee(Employee employee);
    Employee getEmployeeById(long id);
    List<Employee> getAllEmployees();
    void updateEmployee(Employee employee);
    void deleteEmployee(Employee employee);
}
