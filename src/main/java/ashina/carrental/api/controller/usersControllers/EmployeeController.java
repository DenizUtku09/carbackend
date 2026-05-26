package ashina.carrental.api.controller.usersControllers;

import java.time.LocalDate;
import java.util.List;

import ashina.carrental.entities.concretes.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import ashina.carrental.business.abstracts.userServices.EmployeeService;
import ashina.carrental.business.abstracts.userServices.usersLoginServices.LoginService;
import ashina.carrental.entities.concretes.users.Employee;
import ashina.carrental.entities.concretes.users.usersLogin.LoginRequest;
import ashina.carrental.entities.concretes.users.usersLogin.LoginResponse;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    // This class serves as the controller for employee-related API endpoints

    // Injects an instance of the EmployeService interface for handling user operations
    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private LoginService loginService;

    /**
     * Registers a new employee.
     *
     * @param employee the employee to register
     * @return the registered employee
     */
    @PostMapping("/register")
    public ResponseEntity<Employee> registerNewEmployee(@RequestBody Employee employee){
        // This method handles POST requests to the "/api/employees/register" URL and registers a new employee 

         if (employeeService.existsByEmail(employee.getEmail())) {
            // Check if an employee with the given email already exists
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Email already registered.");
                    // If the email is already registered, throw a BadRequest exception with a custom error message
        }

        if (employeeService.existsByNationalIdentificationNumber(employee.getNationalIdentificationNumber())) {
            // Check if an employee with the given national identification number already exists
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "National Identification Number already registered.");
                    // If the national identification number is already registered, throw a BadRequest exception with a custom error message
        }

        // Register the new employee by calling the registerNewEmployee() method of the EmployeeService interface
        Employee savedEmployee = employeeService.registerNewEmployee(employee);

        // Return a ResponseEntity with the saved employee and HTTP 200 status
        return ResponseEntity.ok(savedEmployee);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        LoginResponse response = loginService.login(request);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/updateEmployee")
    public ResponseEntity<Employee> updateEmployee(@RequestBody Employee employee){
        
        // This method handles PUT requests to the "/api//updateEmployee" URL and update a employee 

        // Call the updateEmployee method of the employeeService to update the employee
        Employee updatedEmployee = employeeService.updateEmployee(employee);

        // Return the updated employee in the response body with a HTTP 200 OK status
        return ResponseEntity.ok(updatedEmployee);
    }

    /**
    * Handles a PUT request to update the email of an employee identified by their ID.
    *
    * @param id The ID of the employee to update.
    * @param newEmail The new email address to assign to the employee.
    * @return ResponseEntity<Employee> The updated employee in the response body with a HTTP 200 OK status.
    */
    @PutMapping("/updateEmployeeEmail/{id}")
    public ResponseEntity<Employee> updateEmployeeEmail(@PathVariable("id") int id, @RequestParam("newEmail") String newEmail){

        // This method Handles a PUT request to update the email of an employee identified by their ID.

        // Call the updateEmployeeEmail method of the employeeService to update the employee's email
        Employee updatedEmployee = employeeService.updateEmployeeEmail(id, newEmail);

         // Return the updated employee in the response body with a HTTP 200 OK status
        return ResponseEntity.ok(updatedEmployee);
    }

    /**
    * Handles a PUT request to update the password of an employee identified by their ID.
    *
    * @param id the ID of the employee to update.
    * @param newPassword The new password to assign to the employee.
    * @return ResponseEntity<Employee> The updated employee in the response body with a HTTP 200 OK status.
    */
    @PutMapping("/updateEmployeePassword/{id}")
    public ResponseEntity<Employee> updateEmployeePassword(@PathVariable("id") int id, @RequestParam("newPassword") String newPassword){
        
        // This method Handles a PUT request to update the password of an employee identified by their ID.

        // Call the updateEmployeePassword method of the employeeService to update the employee's password
        Employee updatedEmployee = employeeService.updateEmployeePassword(id, newPassword);

        // Return the updated employee in the response body with a HTTP 200 OK status
        return ResponseEntity.ok(updatedEmployee);
    }

    /**
    * Handles a PUT request to update the full name of an employee identified by their ID.
    *
    * @param id the ID of the employee to update.
    * @param newFullName The new full name to assign to the employee.
    * @return ResponseEntity<Employee> The updated employee in the response body with a HTTP 200 OK status.
    */
    @PutMapping("/updateEmployeeFullName/{id}")
    public ResponseEntity<Employee> updateEmployeeFullName(@PathVariable("id") int id, @RequestParam("newFullName") String newFullName){
        
        // This method Handles a PUT request to update the full name of an employee identified by their ID.

        // Call the updateEmployeeFullName method of the employeeService to update the employee's full name
        Employee updatedEmployee = employeeService.updateEmployeeFullName(id, newFullName);

        // Return the updated employee in the response body with a HTTP 200 OK status
        return ResponseEntity.ok(updatedEmployee);
    }

    /**
    * Handles a PUT request to update the national identification number of an employee identified by their ID.
    *
    * @param id the ID of the employee to update.
    * @param newFullName The new national identification number to assign to the employee.
    * @return ResponseEntity<Employee> The updated employee in the response body with a HTTP 200 OK status.
    */
    @PutMapping("/updateEmployeeNationalIdentificationNumber/{id}")
    public ResponseEntity<Employee> updateEmployeeNationalIdentificationNumber(@PathVariable("id") int id, @RequestParam("newNationalIdentificationNumber") String newNationalIdentificationNumber){
        
        // This method Handles a PUT request to update the national identification number of an employee identified by their ID.

        // Call the upadateEmployeeNationalIdentificationNumber method of the employeeService to update the employee's national identification number
        Employee updatedEmployee = employeeService.updateEmployeeNationalIdentificationNumber(id, newNationalIdentificationNumber);
        
        // Return the updated employee in the response body with a HTTP 200 OK status
        return ResponseEntity.ok(updatedEmployee);
    }

    /**
    * Updates the password of an employee identified by their ID.
    *
    * @param id the ID of the employee to update
    * @param newDateOfBirth the new birh date
    * @return the updated employee
    */
    @PutMapping("/updateEmployeeDateOfBirth/{id}")
    public ResponseEntity<Employee> updateEmployeeDateOfBirth(@PathVariable("id") int id, @RequestParam("newDateOfBirth") LocalDate newDateOfBirth){
        
        // This method Handles a PUT request to update the birth date of an employee identified by their ID.

        // Call the updateEmployeeDateOfBirth method of the employeeService to update the employee's birth date.
        Employee updatedEmployee = employeeService.updateEmployeeDateOfBirth(id, newDateOfBirth);

        // Return the updated employee in the response body with a HTTP 200 OK status
        return ResponseEntity.ok(updatedEmployee);
    }

    /**
    * Updates the phone number of an employee identified by their ID.
    *
    * @param id the ID of the employee to update
    * @param newPhoneNumber the new phone Number
    * @return the updated employee
    */
    @PutMapping("/updateEmployeePhoneNumber/{id}")
    public ResponseEntity<Employee> updateEmployeePhoneNumber(@PathVariable("id") int id, @RequestParam("newPhoneNumber") String newPhoneNumebr){
        
        // This method Handles a PUT request to update the birth date of an employee identified by their ID.
        
        // Call the updateEmployeePhoneNumber method of the employeeService to update the employee's phone number.
        Employee updatedEmployee = employeeService.updateEmployeePhoneNumber(id, newPhoneNumebr);

        // Return the updated employee in the response body with a HTTP 200 OK status
        return ResponseEntity.ok(updatedEmployee);
    }

    /**
    * Updates the job of an employee identified by their ID.
    *
    * @param id the ID of the employee to update
    * @param newJobId the new job
    * @return the updated employee
    */
    @PutMapping("updateEmployeeJob/{id}")
    public ResponseEntity<Employee> updateEmployeeJob(@PathVariable("id") int id, @RequestParam("newJobId") int newJobId){

        // This method Handles a PUT request to update the birth date of an employee identified by their ID.

        // Call the updateEmployeeJob method of the employeeService to update the employee's job.
        Employee updatedemployee = employeeService.updateEmployeeJob(id, newJobId);

        // Return the updated employee in the response body with a HTTP 200 OK status
        return ResponseEntity.ok(updatedemployee);
    }

    /**
     * Deletes an employee by their ID.
     *
     * @param id the ID of the employee to delete
     */
    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable int id){

        // This method handles DELETE requests to the "/api/employee/deleteById/{id}" URL to delete an employee by ID

        return employeeService.findById(id).map(employee -> {
            // Find the employee by ID
            employeeService.deleteById(id);

            // Delete the employee by calling the deleteById() method of the EmployeeService interface
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);

             // Return a ResponseEntity with HTTP 204 (NO_CONTENT) status to indicate successful deletion
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        // If no employee with the given ID is found, return a ResponseEntity with HTTP 404 (NOT_FOUND) status
    }

    /**
     * Retrieves all employees.
     *
     * @return a list of all employees
     */
    @GetMapping("/getAllEmployees")
    public ResponseEntity<List<Employee>> getAllEmployees(){
        // This method handles GET requests to the "/api/employees//getAllEmployees" URL

        // Retrieve all users by calling the getAllUsers() method of the EmployeeService interface
        List<Employee> employees = employeeService.getAllEmployees();

        // Return a ResponseEntity with the retrieved users and HTTP 200 status
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    /**
     * Retrieves an employee by their unique identifier (ID).
     *
     * @param id the employee ID
     * @return an Optional containing the employee, or an empty Optional if not found
     */
    @GetMapping("/findById/{id}")
    public ResponseEntity<Employee> findById(@PathVariable int id){

        // This method defines an API endpoint to search for a employee by id

        // Handle GET requests to the "findById/{id}" URL

        // The {id} URL parameter represents the id to search for

        return employeeService.findById(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Retrieves an employee by their email.
     *
     * @param email the employee email
     * @return an Optional containing the employee, or an empty Optional if not found
     */
    @GetMapping("/findByEmail/{email}")
    public ResponseEntity<Employee> findByEmail(@PathVariable String email){

        // This method defines an API endpoint to search for a employee by email address

        // Handle GET requests to the "findByEmail/{email}" URL

        // The {email} URL parameter represents the email address to search for

        return employeeService.findByEmail(email)
        .map(ResponseEntity::ok) // If a user is found, wrap it in a ResponseEntity with HTTP 200 status
        .orElse(ResponseEntity.notFound().build()); // If no user is found, return HTTP 404 status
    }

    /**
     * Retrieves an employee by their national identification number.
     *
     * @param nationalIdentificationNumber the employee national identification number
     * @return an Optional containing the employee, or an empty Optional if not found
     */
    @GetMapping("/findByNationalIdentificationNumber/{nationalIdentificationNumber}")
    public ResponseEntity<Employee> findByNationalIdentificationNumber(@PathVariable String nationalIdentificationNumber){

        // This method defines an API endpoint to search for a employee by national identification number

        // Handle GET requests to the "findByNationalIdentificationNumber/{nationalIdentificationNumber}" URL

        // The {nationalIdentificationNumber} URL parameter represents the email address to search for

        return employeeService.findByNationalIdentificationNumber(nationalIdentificationNumber)
        .map(ResponseEntity::ok) // If a user is found, wrap it in a ResponseEntity with HTTP 200 status
        .orElse(ResponseEntity.notFound().build()); // If no user is found, return HTTP 404 status
    }



    /**
     * Checks if an employee with the given email exists.
     *
     * @param email the employee email
     * @return true if an employee with the email exists, false otherwise
     */
    @GetMapping("/existsByEmail/{email}")
    public ResponseEntity<Boolean> existsByEmail(@PathVariable String email){
        
        // This method defines an API endpoint to check if a employee exists by email address

        // Handle GET requests to the "/exists-by-email/{email}" URL

        // The {email} URL parameter represents the email address to check

        // Check if a user with the given email exists
        boolean exists = employeeService.existsByEmail(email);

        // Return the result as a ResponseEntity with HTTP 200 status
        return ResponseEntity.ok(exists);
    }

     /**
     * Checks if an employee with the given national identification number exists.
     *
     * @param nationalIdentificationNumber the employee national identification number
     * @return true if an employee with the national identification number exists, false otherwise
     */
    @GetMapping("/existsByNationalIdentificationNumber/{nationalIdentificationNumber}")
    public ResponseEntity<Boolean> existsByNationalIdentificationNumber(@PathVariable String nationalIdentificationNumber){

        // This method defines an API endpoint to check if a employee exists by national identification number

        // Handle GET requests to the "/existsByNationalIdentificationNumber/{nationalIdentificationNumber}" URL

        // The {email} URL parameter represents the email address to check
        
        // Check if a user with the given email exists
        boolean exists = employeeService.existsByNationalIdentificationNumber(nationalIdentificationNumber);

        // Return the result as a ResponseEntity with HTTP 200 status
        return ResponseEntity.ok(exists); 
    }

    @DeleteMapping("/DeleteEmployeeJob/{id}")
    public void deleteEmployeeJob(@PathVariable int id){
        employeeService.deleteEmployeeJob(id);

    }
    @GetMapping("/SortEmployeesByNameAlphabetically")
    public List<Employee> sortEmployeesByNameAlphabetically(){return employeeService.sortEmployeesByNameAlphabetically();}
    @GetMapping("/SortEmployeesByNameAlphabeticallyReversed")
    public List<Employee> sortEmployeesByNameAlphabeticallyReversed(){return employeeService.sortEmployeesByNameAlphabeticallyReversed();}
    @GetMapping("/SortEmployeeByName/{fullname}")
    public Employee sortEmployeeByName(@PathVariable String fullname){return employeeService.sortEmployeeByName(fullname);}
    @GetMapping("/FindJobByTitle/{title}")
    public Job findJobByTitle(@PathVariable String title){return employeeService.findJobByTitle(title);}
   

}
