package api_products.DTOS.productDTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CreateProductVariationDto(
        @NotBlank
        String sizeName,
        @NotBlank
        String descricao,
        @NotNull
        BigDecimal price,
        @NotNull
        Boolean available

) {
    @Override
    public String sizeName() {
        return sizeName;
    }

    @Override
    public String descricao() {
        return descricao;
    }

    @Override
    public BigDecimal price() {
        return price;
    }

    @Override
    public Boolean available() {
        return available;
    }
}
