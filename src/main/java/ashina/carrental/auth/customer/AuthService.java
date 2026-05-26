package ashina.carrental.auth.customer;

import ashina.carrental.auth.customer.dto.AuthResponse;
import ashina.carrental.auth.customer.dto.LoginRequest;
import ashina.carrental.auth.customer.dto.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class AuthService {

    private static final Pattern EMAIL_RX =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    private final AppUserRepository users;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthResponse register(RegisterRequest req) {
        String email = sanitizeEmail(req.email());
        String password = req.password();
        String fullName = req.fullName() == null ? "" : req.fullName().trim();

        if (email == null || !EMAIL_RX.matcher(email).matches()) {
            throw bad("Email looks invalid.");
        }
        if (password == null || password.length() < 6) {
            throw bad("Password must be at least 6 characters.");
        }
        if (fullName.isEmpty()) {
            throw bad("Full name is required.");
        }
        if (users.existsByEmail(email)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "An account with that email already exists.");
        }

        AppUser user = new AppUser();
        user.setEmail(email);
        user.setPasswordHash(passwordEncoder.encode(password));
        user.setFullName(fullName);
        user.setCreatedAt(Instant.now());
        AppUser saved = users.save(user);
        return buildResponse(saved);
    }

    public AuthResponse login(LoginRequest req) {
        String email = sanitizeEmail(req.email());
        String password = req.password();
        if (email == null || password == null) {
            throw bad("Email and password are required.");
        }
        AppUser user = users.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email or password."));
        if (!passwordEncoder.matches(password, user.getPasswordHash())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email or password.");
        }
        return buildResponse(user);
    }

    public AppUser requireByEmail(String email) {
        return users.findByEmail(sanitizeEmail(email))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Account no longer exists."));
    }

    private AuthResponse buildResponse(AppUser user) {
        JwtService.Issued issued = jwtService.issue(user);
        return new AuthResponse(
                issued.token(),
                issued.expiresAtEpochMs(),
                new AuthResponse.UserSummary(user.getId(), user.getEmail(), user.getFullName())
        );
    }

    private static String sanitizeEmail(String email) {
        return email == null ? null : email.trim().toLowerCase();
    }

    private static ResponseStatusException bad(String msg) {
        return new ResponseStatusException(HttpStatus.BAD_REQUEST, msg);
    }
}
