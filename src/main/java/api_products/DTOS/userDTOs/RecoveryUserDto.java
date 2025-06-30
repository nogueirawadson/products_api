package api_products.DTOS.userDTOs;

import api_products.models.Role;

import java.util.List;

public record RecoveryUserDto(
        Integer id,
        String username,
        List<Role> roles
) {
}
