package ashina.carrental.entities.concretes.users.usersLogin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Component;

@Component
public class SessionHelper {
    
    private Map<Integer, String> sessionTokens = new HashMap<>();

    public String generateSessionToken(int id) {
        if (sessionTokens.containsKey(id)) {
            return sessionTokens.get(id);
        }

        String sessionToken = UUID.randomUUID().toString();
        sessionTokens.put(id, sessionToken);
        return sessionToken;

    }

}
