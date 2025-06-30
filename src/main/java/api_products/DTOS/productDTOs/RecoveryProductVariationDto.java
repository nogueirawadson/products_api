package api_products.DTOS.productDTOs;

import java.math.BigDecimal;

public record RecoveryProductVariationDto(
        Integer id,
        String sizeName,
        String descricao,
        BigDecimal price,
        Boolean available
) {
}
