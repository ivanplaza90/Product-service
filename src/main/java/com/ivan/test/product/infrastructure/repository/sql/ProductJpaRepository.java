package com.ivan.test.product.infrastructure.repository.sql;

import com.ivan.test.product.infrastructure.repository.sql.model.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.Optional;

public interface ProductJpaRepository extends JpaRepository<ProductEntity, Integer> {
    Optional<ProductEntity> findFirstByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(int productId, int brandId, Date startDate, Date endDate);
}
