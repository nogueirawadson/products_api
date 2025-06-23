package jwt_token.jwt.repository;


import jwt_token.jwt.models.Product;
import jwt_token.jwt.models.ProductVariation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("select pv from ProductVariation pv where pv.product.id = :productId and pv.id = :productVariationId")
    Optional<ProductVariation>
    findByProductIdAndProductVariationId(
            @Param("productId") Long productId,
            @Param("productVariationId") Long productVariationId);

}
