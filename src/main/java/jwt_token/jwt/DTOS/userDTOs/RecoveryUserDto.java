package jwt_token.jwt.DTOS.userDTOs;

import jwt_token.jwt.models.Role;

import java.util.List;

public record RecoveryUserDto(
        Integer id,
        String username,
        List<Role> roles
) {
}
