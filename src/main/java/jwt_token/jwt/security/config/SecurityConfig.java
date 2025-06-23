package jwt_token.jwt.security.config;


import jwt_token.jwt.security.authetication.UserAutheticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// é importante definirmos os nossos endpoints que não requerem autenticação,
// e essa configuração nós fazemos na classe de configuração do Spring Security
@Configuration
@EnableWebSecurity
public class SecurityConfig {
@Autowired
private UserAutheticationFilter userAutheticationFilter;


   public static final String [] ENDPOINTS_WITH_AUTHENTICATION_NOT_REQUIRED = {
           "/users/login",
           "/users/create",// Url que usaremos para fazer login
           "/users", //  Url que usaremos para criar um usuário
           "users/delete/*",


    };

   // Endpoints que requerem autenticação para serem acessados
    public static final String [] ENDPOINTS_WITH_AUTHENTICATION_REQUIRED = {
            "/users/test",
           "/users/update/*",
           "/users/findAll",

   };

    // Endpoints que só podem ser acessados por usuários com permissão de cliente
    public static final String [] ENDPOINTS_CUSTOMER = {
            "/users/test/customer"
    };
    // Endpoints que só podem ser acessados por usuários com permissão de administrador
    public static final String [] ENDPOINTS_ADMIN = {
            "/users/test/admin",
    };
// SecurityFilterChain(): Este metodo cria uma SecurityFilterChain,
//  que é a configuração principal de segurança do Spring Security para a aplicação.
//  Ele define a política de autorização para os endpoints da API REST.
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurituy) throws Exception {
        return httpSecurituy.csrf(AbstractHttpConfigurer::disable) //Desativa a proteção contra CSRf
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Configura a politica de criação de sessão com stateless
                .authorizeHttpRequests(auth -> auth // Habilita a autorização para as requisições http
                .requestMatchers(ENDPOINTS_WITH_AUTHENTICATION_NOT_REQUIRED).permitAll()
                .requestMatchers(ENDPOINTS_WITH_AUTHENTICATION_REQUIRED).authenticated()
                .requestMatchers(ENDPOINTS_CUSTOMER).hasRole("CUSTOMER") // Repare que não é necessário colocar "ROLE" antes do nome
                .requestMatchers(ENDPOINTS_ADMIN).hasRole("ADMIN")
                .anyRequest().authenticated())
                // Adiciona um filtro de autenticação de usuário que criamos, antes do filtro de segurança padrão do Spring Security
                .addFilterBefore(userAutheticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    //authenticationManager(): Este metodo retorna uma instância de AuthenticationManager.
    // Essa instância é utilizada pelo Spring Security para realizar a autenticação de um usuário.
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
      return authenticationConfiguration.getAuthenticationManager();
    }

    // passwordEncoder(): Este metodo retorna uma instância de PasswordEnconder que é utilizada pelo Spring Security
    //  para codificar as senhas dos usuários de forma segura, protegendo as informações confidenciais.
    //  estamos usando o algoritmo bcrypt para codificar essas senhas.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
