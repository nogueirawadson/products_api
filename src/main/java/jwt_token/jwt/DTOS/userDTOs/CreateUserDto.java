package jwt_token.jwt.DTOS.userDTOs;


import jwt_token.jwt.enums.RoleName;

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
