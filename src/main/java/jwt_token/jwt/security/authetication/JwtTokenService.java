package jwt_token.jwt.security.authetication;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import jwt_token.jwt.security.userdetails.UserDetailsImpl;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Service
public class JwtTokenService {

    private static final String SECRET_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9"; // // Chave secreta utilizada para gerar e verificar o token
    private static final String ISSUER = "jwt_token";

    public String createToken(UserDetailsImpl user) {
        try{
        Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);

            List<String> roles = user.getAuthorities()
                    .stream()
                    .map(GrantedAuthority::getAuthority)
                    .toList();
        return JWT.create()
                .withIssuer(ISSUER) // define o emissor do token
                .withIssuedAt(creationDate()) // Define a data de emissão de token
                .withExpiresAt(expirationDate()) // Define a expeiração do token
                .withSubject(user.getUsername()) // Define o assunto (neste caso, o nome de usuário)
                .withClaim("roles", roles) // aqui são inseridas as roles
                .sign(algorithm); // Assina o token usando o algoritimo especificado

        } catch (JWTCreationException exception) {
            throw new JWTCreationException("Erro ao gerar token", exception);

        }
    }

    public String getSubjectFromToken(String token) {
        try {
            // Define o algoritmo HMAC SHA256 para verificar a assinatura do token passando a chave secreta definida
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
            return JWT.require(algorithm)
                    .withIssuer(ISSUER) // Define o emissor do Token
                    .build()
                    .verify(token)// Verifica a validade do token
                    .getSubject(); // Obtem o assunto, (nesse caso, o nome de usuário) do token

        } catch (JWTCreationException exception) {
            throw new JWTCreationException("Erro ao gerar token", exception);
        }
    }

    private Instant creationDate() {
        return ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")).toInstant();
    }

    private Instant expirationDate() {
        return ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")).plusHours(5).toInstant();

    }


}
