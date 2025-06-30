package api_products.service.product;

import api_products.DTOS.productDTOs.*;
import api_products.enums.Category;
import api_products.mappers.ProductMapper;
import api_products.models.Product;
import api_products.models.ProductVariation;
import api_products.repository.ProductRepository;
import api_products.repository.ProductVariationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    ProductVariationRepository productVariationRepository;
    @Autowired
    private ProductMapper productMapper;


    public RecoveryProductDto createProduct(CreateProductDto createProductDto) {

        List<ProductVariation> productVariations =
                createProductDto.productVariations()
                        .stream()
                        .map(productVariationDto ->
                                productMapper.mapCreateProductVariationDtoToProductVariation(productVariationDto)).toList();

        Product product = new Product();
        product.setName(createProductDto.name());
        product.setDescricao(createProductDto.descricao());
        product.setCategory(Category.valueOf(createProductDto.category().toUpperCase()));
        product.setAvailable(createProductDto.available());
        product.setProductVariations(productVariations);
        product.setAvailable(createProductDto.available());


        if (!product.getAvailable() && product.getProductVariations().stream().anyMatch(ProductVariation::getAvailable)) {
            throw new RuntimeException("A variação de produto não pode estar disponível se o produto não estiver disponível");
        }

        productVariations.forEach(productVariation -> productVariation.setProduct(product));
        Product productSaved = productRepository.save(product);
        return productMapper.mapProductToRecoveryProductDto(productSaved);
    }


    public RecoveryProductDto createProductVariation(Long productId, CreateProductVariationDto
            createProductVariationDto) {
        Product product =
                productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Produto não encontrado"));


        ProductVariation productVariation =
                productMapper.mapCreateProductVariationDtoToProductVariation(createProductVariationDto);

        productVariation.setProduct(product);
        ProductVariation productVariationSaved =
                productVariationRepository.save(productVariation);
        product.getProductVariations().add(productVariationSaved);
        productRepository.save(product);

        return productMapper.mapProductToRecoveryProductDto(productVariationSaved.getProduct());

    }

    // Metodo responsável por retornar todos os produtos

    public List<RecoveryProductDto> getProducts() {
        // Retorna todos os produtos salvos no banco
        List<Product> products = productRepository.findAll();

        // Retorna e mapeia para uma lista
        return products.stream().map(product -> productMapper.mapProductToRecoveryProductDto(product)).toList();
    }

    // Metodo responsavel por retornar o produto por id
    public RecoveryProductDto geProductById(Long productId) {
        // Procura por um produto salvo no banco
        Product product =
                productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Produto não encontrado"));
        // Retornando e mapeando os produtos para o tipo RecoveryProductDto
        return productMapper.mapProductToRecoveryProductDto(product);
    }

    // Atualiza um produto (sem atualizar as variações dele) @PatchMapping

    public RecoveryProductDto updateProductPart(Long productId, UpdateProductDto updateProductDto) {
        // Procura por um produto salvo no banco
        Product product =
                productRepository.findById(productId).orElseThrow(() ->
                        new RuntimeException("Produto não encontrado"));

        // Verifica se o valor passado é um Json e só assim altera

        if (updateProductDto.name() != null) {
            product.setName(updateProductDto.name());
        }
        if (updateProductDto.descricao() != null) {
            product.setDescricao(updateProductDto.descricao());
        }
        if (updateProductDto.available() != null) {
            product.setAvailable(updateProductDto.available());  // Se o produto estiver com available false, por padrão as variações também devem estar
            if (product.getAvailable()) {
                product.getProductVariations().forEach(productVariation ->
                        productVariation.setAvailable(false));
            }
        }
    // Retornando e mapeando os produtos para o tipo, RecoveryProductDto

        return productMapper.mapProductToRecoveryProductDto(productRepository.save(product));

}
    // Metodo responsável por atualizar uma variação de produto
        public RecoveryProductDto updateProductVariation(Long productId, Long productVariationInProductId, UpdateProductVariationDto updateProductVariationDto){
        // Verifica se o produto existe
            Product product =
                    productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Produto não encontrado"));
            // Procura pela variação do produto que já está salvo no banco
            ProductVariation productVariation =
                    product.getProductVariations().stream()
                            .filter(productVariationInProduct -> productVariationInProduct.getId().equals(productVariationInProductId))
                            .findFirst()
                            .orElseThrow(() ->
                                    new RuntimeException("Variação de produto não encontrada"));

            if(updateProductVariationDto.sizeName() != null){
                productVariation.setSizeName(updateProductVariationDto.sizeName());

            }
            if(updateProductVariationDto.descricao() != null) {
                productVariation.setDescricao(updateProductVariationDto.descricao());
            }

            if(updateProductVariationDto.available() != null) {
                productVariation.setAvailable(updateProductVariationDto.available());

                if(updateProductVariationDto.available() && !productVariation.getProduct().getAvailable()) {
                    throw new RuntimeException("A variação de tamanho não pode estar disponível se o produto estiver indisponível");
                }
                productVariation.setAvailable(updateProductVariationDto.available());
            }
            if (updateProductVariationDto.price() != null) {
                productVariation.setPrice(updateProductVariationDto.price());
            }

           Product productSaved = productRepository.save(product);


            return  productMapper.mapProductToRecoveryProductDto(productSaved);
        }


    public void deleteProductId(Long productId) {

        if(!productRepository.existsById(productId)) {
            throw new RuntimeException("Produto não encontrado");
        }
         productRepository.deleteById(productId);
    }

    public void deleteProductVariationId(Long productId, Long productVariationId){
        ProductVariation productVariation =
                productVariationRepository.findByProductIdAndProductVariationId(productId,productVariationId).orElseThrow(() ->
                new RuntimeException("Variação de produto não encontrada"));
        productVariationRepository.deleteById(productVariation.getId());
    }





    }

