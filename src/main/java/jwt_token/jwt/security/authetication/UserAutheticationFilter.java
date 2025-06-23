package jwt_token.jwt.security.authetication;

import com.auth0.jwt.exceptions.JWTCreationException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jwt_token.jwt.repository.userRepository;
import jwt_token.jwt.security.config.SecurityConfig;
import jwt_token.jwt.security.userdetails.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

// Cria um filtro personalizado para verificar se o usuário é um usuário válido e autenticá-lo.
@Component
public class UserAutheticationFilter extends OncePerRequestFilter {
    @Autowired
    private JwtTokenService jwtTokenService; // Service que definimos anteriormente

    @Autowired
    private userRepository userRepository; // Repository que definimos anteriormente


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // Verifica se o endpoint requer autenticação antes de processar a requisição
        if (checkIfEndpointIsNotPublic(request)) {
            String token = recoveryToken(request); // Recupera o token do cabeçalho AUthorization da requisição
            if (token != null && !token.isBlank())  {
                try {


                String subject = jwtTokenService.getSubjectFromToken(token);// Obtém o assunto(nesse caso, o nome de usuário) do token
                userRepository.findByUsername(subject).ifPresent(user -> {
                    UserDetailsImpl userDetails = new UserDetailsImpl(user); // Cria um Details com o usuário encontrado
                    // Cria um objet de autenticação do Spring Security
                    Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                    // Define o objeto de autenticação no contexto do Spring Security
                    SecurityContextHolder.getContext().setAuthentication(authentication);

                }); // busca o usuário pelo nome (que é o assunto do token)
                    } catch (JWTCreationException e) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    return;
                }

            }
        }
        filterChain.doFilter(request, response); // Continua o processamento da requisição
    }

    // Recupera o token do cabeçalho Authorization da requisição
    private String recoveryToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer "))  {
            return authorizationHeader.substring(7);
        }
        return null;
    }

    // Verifica se o endpoint requer autenticação antes de processar a requisição
    private boolean checkIfEndpointIsNotPublic(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        return !Arrays.asList(SecurityConfig.ENDPOINTS_WITH_AUTHENTICATION_NOT_REQUIRED).contains(requestURI);
    }
}
