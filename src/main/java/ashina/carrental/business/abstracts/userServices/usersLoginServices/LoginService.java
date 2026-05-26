package ashina.carrental.business.abstracts.userServices.usersLoginServices;

import ashina.carrental.entities.concretes.users.usersLogin.LoginRequest;
import ashina.carrental.entities.concretes.users.usersLogin.LoginResponse;

public interface LoginService {

    LoginResponse login(LoginRequest request);
    
}
