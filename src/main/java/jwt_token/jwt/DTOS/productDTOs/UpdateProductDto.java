package jwt_token.jwt.DTOS.productDTOs;

public record UpdateProductDto(

        String name,

        String descricao,

        Boolean available
) {
}
