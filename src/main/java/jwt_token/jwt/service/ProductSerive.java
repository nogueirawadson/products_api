package jwt_token.jwt.service;

import jwt_token.jwt.DTOS.productDTOs.CreateProductDto;
import jwt_token.jwt.DTOS.productDTOs.RecoveryProductDto;
import jwt_token.jwt.enums.Category;
import jwt_token.jwt.mappers.ProductMapper;
import jwt_token.jwt.models.Product;
import jwt_token.jwt.models.ProductVariation;
import jwt_token.jwt.repository.ProductRepository;
import jwt_token.jwt.repository.ProductVariationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductSerive {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    ProductVariationRepository productVariationRepository;
    @Autowired
    private ProductMapper productMapper;

    // Método Responsável por criar umm produto

    public RecoveryProductDto createProduct(CreateProductDto createProductDto) {
        /* Coverte a lista de um ProductVariationDto em uma lista de ProductVariation
      utilizando o ProductMapper para fazer o mapeamento de cada elemento da lista
        */
        List<ProductVariation> productVariations =
                createProductDto.productVariations()
                        .stream()
                        .map(productVariationDto ->
                            productMapper.mapCreateProductVariationDtoToProductVariation(productVariationDto)).toList();
        // Cria um produto através de dados DTo
        Product product = new Product();
        product.setName(createProductDto.name());
        product.setDescricao(createProductDto.descricao());
        product.setCategory(Category.valueOf(createProductDto.name()));
        product.setAvailable(createProductDto.available());
        productRepository.save(product);






    }
}
