package com.lab.darackbang.mapper;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lab.darackbang.dto.product.ProductDTO;
import com.lab.darackbang.entity.*;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;
import lombok.ToString;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ProductImageMapper.class})
public interface ProductMapper {

    ProductDTO toDTO(Product product);

    @Mapping(source = "productImages", target = "productImages")
    @Mapping(target = "qandas", ignore = true)
    @Mapping(target = "productReviews", ignore = true)
    @Mapping(target = "subscribes", ignore = true)
    @Mapping(target = "wishLists", ignore = true)
    Product toEntity(ProductDTO productDTO);
}