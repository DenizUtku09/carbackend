package ashina.carrental.auth.config;

import ashina.carrental.auth.model.enums.ConfigurationParameter;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import java.security.PrivateKey;
import java.security.PublicKey;


@Configuration
@Getter
@Slf4j
public class TokenConfigurationParameter {


    private final String issuer;

    private final Integer accessTokenExpireMinute;

    private final Integer refreshTokenExpireDay;

    private final PrivateKey privateKey;

    private final PublicKey publicKey;

    public TokenConfigurationParameter(){

        this.issuer = ConfigurationParameter.CR.getDefaultValue();
        this.accessTokenExpireMinute = Integer.parseInt(
                ConfigurationParameter.AUTH_ACCESS_TOKEN_EXPIRE_MINUTE.getDefaultValue());
        this.refreshTokenExpireDay = Integer.parseInt(
                ConfigurationParameter.AUTH_REFRESH_TOKEN_EXPIRE_DAY.getDefaultValue());
        this.privateKey = null;
        this.publicKey = null;
    }


}
