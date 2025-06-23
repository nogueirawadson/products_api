package jwt_token.jwt.security.userdetails;

import jwt_token.jwt.models.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

public class UserDetailsImpl implements UserDetails {

    private final User user;

    public UserDetailsImpl(User user) {
        this.user = user;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
         /*
         Este método converte a lista de papéis (roles) associados ao usuário
         em uma coleção de GrantedAuthorities, que é a forma que o Spring Security
         usa para representar papéis. Isso é feito mapeando cada papel para um
         novo SimpleGrantedAuthority, que é uma implementação simples de
         GrantedAuthority
        */

        return user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority
                        (role.getName().name())).collect(
                                Collectors.toList());
    }

    @Override
    public String getPassword() {
        // Retorna a credencial do usuário que criamos anteriormente
        return user.getPassword();
    }

    @Override

    public String getUsername() {
        // Retorna o nome de usuário do usuário que criamos anteriormente
        return user.getUsername();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        UserDetailsImpl that = (UserDetailsImpl) o;
        return Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(user);
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
