package api_products.controllers.productConroller;


import api_products.DTOS.productDTOs.*;
import api_products.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("api/products")
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping("/findAll")
    public ResponseEntity<List<RecoveryProductDto>> findProducts() {
        try {
            return new ResponseEntity<>(productService.getProducts(), HttpStatus.OK);
        } catch (Exception ex) {
            throw new RuntimeException("Não foi possível realizar essa operação");
        }
    }
    @GetMapping("/findProduct/{productId}")
    public ResponseEntity<RecoveryProductDto> getProduct(@PathVariable Long productId){
        return
                new ResponseEntity<>(productService.geProductById(productId), HttpStatus.OK);
    }

    @PostMapping("/create/product")
    public ResponseEntity<RecoveryProductDto> createProduct(@RequestBody CreateProductDto productDto) {
        return new ResponseEntity<>(productService.createProduct(productDto), HttpStatus.CREATED);
    }

    @PostMapping({"/{productId}/productVariation"})
    public ResponseEntity<RecoveryProductDto> createProductVariation(@PathVariable Long productId,
                            @RequestBody CreateProductVariationDto createProductVariation) {
        return new ResponseEntity<>(productService.createProductVariation(productId, createProductVariation), HttpStatus.OK);
    }

    @PatchMapping("/{productId}")
    public ResponseEntity<RecoveryProductDto> updateProductPart(@PathVariable Long productId,
                                                                UpdateProductDto updateProductDto) {
        return new ResponseEntity<>(productService.updateProductPart(productId, updateProductDto), HttpStatus.OK);
    }

    @PutMapping("/{productId}/variation/{productVariationId}")
    public ResponseEntity<RecoveryProductDto> updateProductVariation(@PathVariable Long productId,
                                                                     @PathVariable Long productVariationId,
                                                                     @RequestBody UpdateProductVariationDto updateProductVariationDto){
        return new ResponseEntity<>(productService.updateProductVariation(productId, productId, updateProductVariationDto), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProductById(@PathVariable Long productId){
        productService.deleteProductId(productId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{productId}/variation/{productVariationId}")
    public ResponseEntity<Void> deleteProductVariationById(@PathVariable Long productId,
                                      @PathVariable Long productVariationId){
        productService.deleteProductVariationId(productId, productVariationId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }








}
