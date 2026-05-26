package ashina.carrental.dataAccess.abstracts.UsersDaos;

import org.springframework.data.jpa.repository.JpaRepository;

import ashina.carrental.entities.concretes.users.Employee;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeDao extends JpaRepository<Employee, Integer>{

    Employee findEmployeeById(int id);

     /**
     * Retrieves an employee by their unique identifier (ID).
     *
     * @param id the employee ID
     * @return an Optional containing the employee, or an empty Optional if not found
     */
    Optional<Employee> findById(int id);

     /**
     * Retrieves an employee by their email.
     *
     * @param email the employee email
     * @return an Optional containing the employee, or an empty Optional if not found
     */
    Optional<Employee> findByEmail(String email);

    /**
     * Retrieves an employee by their national identification number.
     *
     * @param nationalIdentificationNumber the employee national identification number
     * @return an Optional containing the employee, or an empty Optional if not found
     */
    Optional<Employee> findByNationalIdentificationNumber(String nationalIdentificationNumber);



     /**
     * Checks if an employee with the given email exists.
     *
     * @param email the employee email
     * @return true if an employee with the email exists, false otherwise
     */
    boolean existsByEmail(String email);

    /**
     * Checks if an employee with the given national identification number exists.
     *
     * @param nationalIdentificationNumber the employee national identification number
     * @return true if an employee with the national identification number exists, false otherwise
     */
    boolean existsByNationalIdentificationNumber(String nationalIdentificationNumber);


    List<Employee> findAllByOrderByFullnameAsc();
    List<Employee> findAllByOrderByFullnameDesc();

    Employee findEmployeeByFullname(String fullname);
}
