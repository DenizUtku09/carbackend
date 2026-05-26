package ashina.carrental.auth.model.mapper;

import ashina.carrental.auth.model.Token;
import ashina.carrental.auth.model.dto.response.TokenResponse;

public class TokenToTokenResponseMapper {

    public static TokenResponse map(Token token) {
        return TokenResponse.builder()
                .accessToken(token.getAccessToken())
                .accessTokenExpiresAt(token.getAccessTokenExpiresAt())
                .refreshToken(token.getRefreshToken())
                .build();
    }
}
