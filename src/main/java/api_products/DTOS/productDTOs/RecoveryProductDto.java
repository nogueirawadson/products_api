package api_products.DTOS.productDTOs;

import java.util.List;

public record RecoveryProductDto(
        Integer id,
        String name,
        String descricao,
        String category,
        List<RecoveryProductVariationDto> productVariations,
        Boolean available
) {
}
