package api_products.DTOS.userDTOs;


import api_products.enums.RoleName;

public record CreateUserDto (
    Long id,
    String username,
    String password,
    RoleName role


){

    @Override
    public Long id() {
        return id;
    }

    @Override
    public String username() {
        return username;
    }

    @Override
    public String password() {
        return password;
    }

    @Override
    public RoleName role() {
        return role;
    }
}
