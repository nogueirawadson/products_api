package jwt_token.jwt.service;


import jwt_token.jwt.DTOS.userDTOs.CreateUserDto;
import jwt_token.jwt.DTOS.userDTOs.LoginUserDto;
import jwt_token.jwt.DTOS.RecoveryJwtTokenDto;
import jwt_token.jwt.models.Role;
import jwt_token.jwt.models.User;
import jwt_token.jwt.repository.userRepository;
import jwt_token.jwt.security.authetication.JwtTokenService;
import jwt_token.jwt.security.config.SecurityConfig;
import jwt_token.jwt.security.userdetails.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class userService {


    @Autowired
    private userRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private SecurityConfig securityConfig;

    // Método responsável por autenticar um usuário e retornar um Token Jwt
    public RecoveryJwtTokenDto authenticateUser(LoginUserDto loginUserDto) {

        // Cria um objeto de autenticação com o username e a senha do usuário
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(loginUserDto.username(), loginUserDto.password());
        //Autentica o usuário com as crendenciais fornecidas
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);


        // Obtém o objeto UserDetails do usuário autenticado
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        // Gera um token JWT para o usuário autenticado
        return new RecoveryJwtTokenDto(jwtTokenService.createToken(userDetails));

    }

    // Metodo reponsável por criar um usuário
    public void createUser(CreateUserDto createUserDto) {
        Role role = new Role();
        List<Role> roles = List.of(role);
        role.setName(createUserDto.role());
        //Cria um novo usuario com os dados fornecidos
        User newUser = new User();
        newUser.setRoles(roles.stream().toList());
        newUser.setUsername(createUserDto.username());
        // Codifica a senha do usuário com o algoritmo brcypt
        newUser.setPassword(securityConfig.passwordEncoder().encode(createUserDto.password()));
        // salva o novo usuário no banco de dados
        userRepository.save(newUser);
    }

    public User update(CreateUserDto createUserDto, Integer id) {
    User user = userRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Usuário não econtrado"));
            //Cria um novo usuario com os dados fornecidos
            user.setUsername(createUserDto.username());
            // Codifica a senha do usuário com o algoritmo brcypt
            user.setPassword(securityConfig.passwordEncoder().encode(createUserDto.password()));
            // salva o novo usuário no banco de dados
            userRepository.save(user);
        return userRepository.save(user);
    }

    public void delete(Integer id) {
        this.userRepository.deleteById(id);
    }

    }



