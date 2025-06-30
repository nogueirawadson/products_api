package api_products.DTOS.productDTOs;

public record UpdateProductDto(

        String name,

        String descricao,

        Boolean available
) {
}
