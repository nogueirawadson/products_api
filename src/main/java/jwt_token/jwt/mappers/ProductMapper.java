package jwt_token.jwt.mappers;

import jwt_token.jwt.DTOS.productDTOs.CreateProductVariationDto;
import jwt_token.jwt.DTOS.productDTOs.RecoveryProductDto;
import jwt_token.jwt.DTOS.productDTOs.RecoveryProductVariationDto;
import jwt_token.jwt.models.Product;
import jwt_token.jwt.models.ProductVariation;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(target = "productVariations", qualifiedBy = "mapProductVariationToRecoveryProductVariationDto")
    RecoveryProductDto mapProductToRecoveryDto (Product product);

    @Named("mapProductVariationToRecoveryProductVariationDto")
    @IterableMapping(qualifiedBy = "mapProductVariationToRecoveryProductVariationDto")
            List<RecoveryProductVariationDto>
    mapProductVariationToRecoveryProductVariationDto(List<ProductVariation> productVariations);

    @Named("mapProductVariationToRecoveryProductVariationDto")
    RecoveryProductVariationDto
    mapProductVariationToRecoveryProductVariationDto(ProductVariation productVariation);
    ProductVariation mapCreateProductVariationDtoToProductVariation(CreateProductVariationDto createProductVariationDto);

}
