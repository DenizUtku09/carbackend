package ashina.carrental.business.abstracts.userServices;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


import ashina.carrental.entities.concretes.Job;
import ashina.carrental.entities.concretes.users.Employee;

public interface EmployeeService {



    /**
     * Registers a new employee.
     *
     * @param employee the employee to register
     * @return the registered employee
     */
    Employee registerNewEmployee(Employee employee);

    /**
     * Deletes an employee by their ID.
     *
     * @param id the ID of the employee to delete
     */
    void deleteById(int id);

    /**
     * Updates an employee.
     *
     * @param employee the updated employee
     * @return the updated employee
     */
    Employee updateEmployee(Employee employee);

    /**
    * Updates the email of an employee identified by their ID.
    *
    * @param id the ID of the employee to update
    * @param newEmail the new email address
    * @return the updated employee
    */
    Employee updateEmployeeEmail(int id, String newEmail);

    /**
    * Updates the password of an employee identified by their ID.
    *
    * @param id the ID of the employee to update
    * @param newPassword the new password
    * @return the updated employee
    */
    Employee updateEmployeePassword(int id, String newPassword);

    /**
    * Updates the password of an employee identified by their ID.
    *
    * @param id the ID of the employee to update
    * @param newFullName the new full name
    * @return the updated employee
    */
    Employee updateEmployeeFullName(int id, String newFullName);

    /**
    * Updates the password of an employee identified by their ID.
    *
    * @param id the ID of the employee to update
    * @param newNationalIdentificationNumber the new national identification number
    * @return the updated employee
    */
    Employee updateEmployeeNationalIdentificationNumber(int id, String newNationalIdentificationNumber);

    /**
    * Updates the password of an employee identified by their ID.
    *
    * @param id the ID of the employee to update
    * @param newDateOfBirth the new birth date
    * @return the updated employee
    */
    Employee updateEmployeeDateOfBirth(int id, LocalDate newDateOfBirth);

    /**
    * Updates the password of an employee identified by their ID.
    *
    * @param id the ID of the employee to update
    * @param newPhoneNumber the new phone Number
    * @return the updated employee
    */
    Employee updateEmployeePhoneNumber(int id, String newPhoneNumber);

    /**
    * Updates the job of an employee identified by their ID.
    *
    * @param id the ID of the employee to update
    * @param newJobId the new job
    * @return the updated employee
    */
    Employee updateEmployeeJob(int id, int newJobId);
    
    /**
     * Retrieves all employees.
     *
     * @return a list of all employees
     */
    void deleteEmployeeJob(int id);
    List<Employee> getAllEmployees();

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



    List<Employee> sortEmployeesByNameAlphabetically();
    List<Employee> sortEmployeesByNameAlphabeticallyReversed();
    Employee sortEmployeeByName(String fullname);
    Job findJobByTitle(String title);

    
}
