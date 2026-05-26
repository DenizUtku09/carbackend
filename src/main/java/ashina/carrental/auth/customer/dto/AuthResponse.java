package ashina.carrental.auth.customer.dto;

public record AuthResponse(String token, long expiresAt, UserSummary user) {
    public record UserSummary(Long id, String email, String fullName) {}
}
