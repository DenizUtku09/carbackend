package ashina.carrental.business.concretes.usersManagers.usersLoginManagers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ashina.carrental.business.abstracts.userServices.EmployeeService;
import ashina.carrental.business.abstracts.userServices.usersLoginServices.LoginService;
import ashina.carrental.entities.concretes.users.Employee;
import ashina.carrental.entities.concretes.users.usersLogin.LoginRequest;
import ashina.carrental.entities.concretes.users.usersLogin.LoginResponse;
import ashina.carrental.entities.concretes.users.usersLogin.SessionHelper;

@Service
public class LoginManager implements LoginService{

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private SessionHelper sessionHelper;

    @Override
    public LoginResponse login(LoginRequest request) {

        String email = request.getEmail();
        String password = request.getPassword();

        Optional<Employee> employeeOptional = employeeService.findByEmail(email);
        if (employeeOptional.isPresent()) {
            Employee employee = employeeOptional.get();
            if (employee.getPassword().equals(password)) {
                String sessionToken = sessionHelper.generateSessionToken(employee.getId());
                LoginResponse response = new LoginResponse();
                response.setSessionToken(sessionToken);
                return response;
            }
        }
        throw new RuntimeException("Invalid Email or Password");
    }


    
}
