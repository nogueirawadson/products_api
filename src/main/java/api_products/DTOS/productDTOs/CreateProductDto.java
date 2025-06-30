package api_products.DTOS.productDTOs;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CreateProductDto(
        @NotBlank
        String name,
        @NotBlank
        String descricao,
        @NotBlank
        String category,
        @NotEmpty
        List<@Valid CreateProductVariationDto> productVariations,
        @NotNull
        Boolean available
) {
    @Override
    public String name() {
        return name;
    }

    @Override
    public String descricao() {
        return descricao;
    }

    @Override
    public String category() {
        return category;
    }

    @Override
    public List<CreateProductVariationDto> productVariations() {
        return productVariations;
    }

    @Override
    public Boolean available() {
        return available;
    }
}
