package api_products.mappers;

import api_products.DTOS.productDTOs.CreateProductVariationDto;
import api_products.DTOS.productDTOs.RecoveryProductDto;
import api_products.DTOS.productDTOs.RecoveryProductVariationDto;
import api_products.models.Product;
import api_products.models.ProductVariation;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "productVariations", qualifiedByName = "mapProductVariationToRecoveryProductVariationDto")
    RecoveryProductDto mapProductToRecoveryProductDto (Product product);

    @Named("mapProductVariationToRecoveryProductVariationDto")
    @IterableMapping(qualifiedByName = "mapProductVariationToRecoveryProductVariationDto")
            List<RecoveryProductVariationDto>
    mapProductVariationToRecoveryProductVariationDto(List<ProductVariation> productVariations);

    @Named("mapProductVariationToRecoveryProductVariationDto")
    RecoveryProductVariationDto
    mapProductVariationToRecoveryProductVariationDto(ProductVariation productVariation);
    ProductVariation mapCreateProductVariationDtoToProductVariation(CreateProductVariationDto createProductVariationDto);

}
