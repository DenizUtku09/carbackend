package ashina.carrental.auth.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TokenClaims {

    JWT_ID("jti"),
    TYPE("typ"),
    SUBJECT("sub"),
    ROLES("roles"),
    EMPLOYEE_ID("id"),
    EMAIL("EMAIL"),
    EMPLOYEE_JOB("job"),
    CUSTOMER_ID("id"),
    ISSUED_AT("iat"),
    EXPIRES_AT("exp"),
    ALGORITHM("alg");

    private final String value;

}
