package jwt_token.jwt.security.userdetails;

import jwt_token.jwt.models.User;
import jwt_token.jwt.repository.userRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceIpml implements UserDetailsService {


private final userRepository userRepository;

    public UserDetailsServiceIpml(userRepository userRepository) {
        this.userRepository = userRepository;
    }

    /*     O método loadUserByUsername() é um método da interface UserDetailsService,
    *      e é usado para carregar os detalhes do usuário com base no nome de usuário fornecido.
    *      Esse método é chamado automaticamente pelo Spring durante o processo de autenticação,
    *      e é responsável por retornar um UserDetails com base no nome de usuário fornecido.
    * */

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(() ->
        new  RuntimeException("Usuário não encontrado"));
        return new UserDetailsImpl(user);

    }
}
