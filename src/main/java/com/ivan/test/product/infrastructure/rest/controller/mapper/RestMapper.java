package com.ivan.test.product.infrastructure.rest.controller.mapper;

import com.ivan.test.product.domain.model.GetProductParameters;
import com.ivan.test.product.domain.model.Money;
import com.ivan.test.product.domain.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import static org.mapstruct.InjectionStrategy.CONSTRUCTOR;
import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(componentModel = "spring", injectionStrategy = CONSTRUCTOR, unmappedTargetPolicy = IGNORE)
public interface RestMapper {

    SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd-HH.mm.ss");

    @Mapping(target = "timestamp", source = "timestamp", qualifiedByName = "mapToDate")
    GetProductParameters mapToGetProductParameters(int productId, int brandId, String timestamp) throws ParseException ;

    @Named("mapToDate")
    default Date mapToDate(String timestamp) throws ParseException {
        if(timestamp == null) {
            return null;
        }
        return DATE_FORMAT.parse(timestamp);
    }

    default Map<String, Object> mapToProductResponse(Product product) {
        return Map.of(
        "productId", product.getProductId(),
        "brandId", product.getBrandId(),
        "startDate", product.getStartDate().toString(),
        "endDate", product.getEndDate().toString(),
        "priceList", product.getPriceList(),
        "priority", product.getPriority(),
        "price", mapToMoneyResponse(product.getPrice())
        );
    }

    default Map<String, Object> mapToMoneyResponse(Money money) {
        return Map.of(
            "amount", money.getAmount(),
            "currency", money.getCurrency()
        );
    }
}
