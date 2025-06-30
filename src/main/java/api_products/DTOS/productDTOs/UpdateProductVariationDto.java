package api_products.DTOS.productDTOs;

import java.math.BigDecimal;

public record UpdateProductVariationDto(

        String sizeName,

        String descricao,

        BigDecimal price,

        Boolean available
) {
}
