package jwt_token.jwt.DTOS.productDTOs;

import java.util.List;

public record RecoveryProductDto(
        Integer id,
        String name,
        String descricao,
        String category,
        List<RecoveryProductVariationDto> recoveryProductVariations,
        Boolean available
) {
}
