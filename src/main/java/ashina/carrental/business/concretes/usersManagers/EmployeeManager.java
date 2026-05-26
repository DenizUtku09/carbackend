package ashina.carrental.business.concretes.usersManagers;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ashina.carrental.business.abstracts.userServices.EmployeeService;
import ashina.carrental.dataAccess.abstracts.JobDao;
import ashina.carrental.dataAccess.abstracts.UsersDaos.EmployeeDao;
import ashina.carrental.entities.concretes.Job;
import ashina.carrental.entities.concretes.users.Employee;

import static java.util.Collections.sort;

@Service
public class EmployeeManager implements EmployeeService{

    @Autowired
    private EmployeeDao employeeDao;

    @Autowired
    private JobDao jobDao;

    /**
     * Registers a new employee by saving the employee object using the employeeDao.
     * @param employee The employee to be registered.
     * @return The registered employee.
     */

    @Override
    public Employee registerNewEmployee(Employee employee) {
       return employeeDao.save(employee);
    }

     /**
     * Retrieves a list of all employees from the data source using the employeeDao.
     * @return A list of all employees.
     */
    @Override
    public List<Employee> getAllEmployees() {
       return employeeDao.findAll();
    }

     /**
     * Retrieves an employee by their ID from the data source using the employeeDao.
     * @param id The ID of the employee to retrieve.
     * @return An optional containing the employee if found, or empty if not found.
     */
   @Override
   public Optional<Employee> findById(int id) {
      return employeeDao.findById(id);
   }

   /**
    * Retrieves an employee by their email from the data source using the employeeDao.
    * @param email The email of the employee to retrieve.
    * @return An optional containing the employee if found, or empty if not found.
    */
   @Override
   public Optional<Employee> findByEmail(String email) {
      return employeeDao.findByEmail(email);   
   }

   /**
    * Retrieves an employee by their national identification number from the data source using the employeeDao.
    * @param nationalIdentificationNumber The national identification number of the employee to retrieve.
    * @return An optional containing the employee if found, or empty if not found.
    */
   @Override
   public Optional<Employee> findByNationalIdentificationNumber(String nationalIdentificationNumber) {
      return employeeDao.findByNationalIdentificationNumber(nationalIdentificationNumber);
   }



   /**
    * Checks if an employee with the given email exists in the data source using the employeeDao.
    * @param email The email to check.
    * @return True if an employee with the given email exists, false otherwise.
    */
   @Override
   public boolean existsByEmail(String email) {
      return employeeDao.existsByEmail(email);
   }

   /**
    * Deletes an employee by their ID from the data source using the employeeDao.
    * @param id The ID of the employee to delete.
    */
   @Override
   public void deleteById(int id) {
      employeeDao.deleteById(id);
   }

   /**
    * Checks if an employee with the given national identification number exists in the data source using the employeeDao.
    * @param nationalIdentificationNumber The national identification number to check.
    * @return True if an employee with the given national identification number exists, false otherwise.
    */
   @Override
   public boolean existsByNationalIdentificationNumber(String nationalIdentificationNumber) {
     return employeeDao.existsByNationalIdentificationNumber(nationalIdentificationNumber);
   }



   @Override
   public List<Employee> sortEmployeesByNameAlphabetically() {
      List<Employee> employees=employeeDao.findAllByOrderByFullnameAsc();
      return employees;


   }

   @Override
   public List<Employee> sortEmployeesByNameAlphabeticallyReversed() {
      List<Employee> employees=employeeDao.findAllByOrderByFullnameDesc();
      return employees;
   }

   @Override
   public Employee sortEmployeeByName(String fullname) {
      Employee existingEmployee=employeeDao.findEmployeeByFullname(fullname);
      if(existingEmployee==null){
         throw new RuntimeException("This employee has not been found by name.");}
      else{
         return existingEmployee;
      }
   }

   @Override
   public Job findJobByTitle(String title) {
      Job existingJob=jobDao.findJobByTitle(title);
      if(existingJob!=null){
         return existingJob;
      }
      else{
         throw new EntityNotFoundException("This job title has not been found by title");
      }
   }

   /**
    * Updates an employee by saving the updated employee object using the employeeDao.
    * @param employee The updated employee object.
    * @return The updated employee.
    * @throws RuntimeException if the employee to be updated is not found.
    */
   @Override
   public Employee updateEmployee(Employee employee) {

      Optional<Employee> existingEmployee = employeeDao.findById(employee.getId());

      if (existingEmployee.isPresent()) {

         Employee updatetedEmployee = employeeDao.save(employee);
        
         return updatetedEmployee;
      } else {
         throw new RuntimeException("Employee not found.");
      }
   }

   /**
    * Updates the email of an employee identified by their ID.
    *
    * @param id the ID of the employee to update
    * @param newEmail the new email address
    * @return the updated employee
    */
   @Override
   public Employee updateEmployeeEmail(int id, String newEmail) {

      Optional<Employee> existingEmployee = employeeDao.findById(id);

      if (existingEmployee.isPresent()) {

         Employee employee = existingEmployee.get();

         employee.setEmail(newEmail);

         Employee updatedEmployee = employeeDao.save(employee);

         return updatedEmployee;

      } else {

         throw new RuntimeException("Employee Not Found");
         
      }
   }
   /**
    * Updates the password of an employee identified by their ID.
    *
    * @param id the ID of the employee to update
    * @param newPassword the new password
    * @return the updated employee
    */
   @Override
   public Employee updateEmployeePassword(int id, String newPassword) {
      
      Optional<Employee> existingEmployee = employeeDao.findById(id);

      if (existingEmployee.isPresent()) {

         Employee employee = existingEmployee.get();

         employee.setPassword(newPassword);

         Employee updatedEmployee = employeeDao.save(employee);
         
         return updatedEmployee;
      } else {throw new RuntimeException("Employee Not Found");}

   }

   /**
    * Updates the Full name of an employee identified by their ID.
    *
    * @param id the ID of the employee to update
    * @param newFullName the new full name
    * @return the updated employee
    */
   @Override
   public Employee updateEmployeeFullName(int id, String newFullName) {

      Optional<Employee> existingEmployee = employeeDao.findById(id);

      if (existingEmployee.isPresent()) {

         Employee employee = existingEmployee.get();

         employee.setFullname(newFullName);

         Employee updatedEmployee = employeeDao.save(employee);

         return updatedEmployee;
      } else {

         throw new RuntimeException("Employee Not Found");

      }
   }

   /**
    * Updates the National Identification of an employee identified by their ID.
    *
    * @param id the ID of the employee to update
    * @param newNationalIdentificationNumber the new national identification number
    * @return the updated employee
    */
   @Override
   public Employee updateEmployeeNationalIdentificationNumber(int id, String newNationalIdentificationNumber) {

      Optional<Employee> existingEmployee = employeeDao.findById(id);
      
      if(existingEmployee.isPresent()) {

         Employee employee = existingEmployee.get();

         employee.setNationalIdentificationNumber(newNationalIdentificationNumber);

         Employee updatedEmployee = employeeDao.save(employee);

         return updatedEmployee;
      } else {

         throw new RuntimeException("Employee Not Found");

      }
   }

   /**
    * Updates the Birth Date of an employee identified by their ID.
    *
    * @param id the ID of the employee to update
    * @param newDateOfBirth the new birth date
    * @return the updated employee
    */
   @Override
   public Employee updateEmployeeDateOfBirth(int id, LocalDate newDateOfBirth) {

      Optional<Employee> existingEmployee = employeeDao.findById(id);

      if (existingEmployee.isPresent()) {

         Employee employee = existingEmployee.get();

         employee.setDateOfBirth(newDateOfBirth);

         Employee updatedEmployee = employeeDao.save(employee);

         return updatedEmployee;
      } else {

         throw new RuntimeException("Employee Not Found");

      }
   }

   /**
    * Updates the phone number of an employee identified by their ID.
    *
    * @param id the ID of the employee to update
    * @param newPhoneNumber the new phone Number
    * @return the updated employee
    */
   @Override
   public Employee updateEmployeePhoneNumber(int id, String newPhoneNumber) {

      Optional<Employee> existingEmployee = employeeDao.findById(id);

      if (existingEmployee.isPresent()) {

         Employee employee = existingEmployee.get();

         employee.setPhoneNumber(newPhoneNumber);

         Employee updatedEmployee = employeeDao.save(employee);

         return updatedEmployee;
      } else {

         throw new RuntimeException("Employee Not Found");

      }
   }

   /**
    * Updates the job of an employee identified by their ID.
    *
    * @param id the ID of the employee to update
    * @param newJobId the new job
    * @return the updated employee
    */
   @Override
   public Employee updateEmployeeJob(int id, int newJobId) {
      
     Optional<Employee> existingEmployee = employeeDao.findById(id);

     if (existingEmployee.isPresent()) {

         Employee employee = existingEmployee.get();

         Job newJob = jobDao.findById(newJobId).orElseThrow(() -> new RuntimeException("Job Not Found"));
       
         employee.setJob(newJob);

         Employee updatedEmployee = employeeDao.save(employee);

         return updatedEmployee;
     } else {

      throw new RuntimeException("Employee Not Found");

     }

   }

   @Override
   public void deleteEmployeeJob(int id) {
      Employee existingEmployee=employeeDao.findEmployeeById(id);
      if(existingEmployee!=null){
         existingEmployee.setJob(null);
         employeeDao.save(existingEmployee);}
      else{
         throw new RuntimeException("This employee does not exist by id.");
      }}

}
