package com.lab.darackbang.mapper.cart;


import com.lab.darackbang.dto.cart.CartItemDTO;
import com.lab.darackbang.entity.CartItem;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CartItemMapper {


    CartItemDTO toDTO(CartItem cartItem);

    CartItem toEntity(CartItemDTO cartItemDTO);

    @AfterMapping
    default void calledWithSourceAndTarget(CartItem cartItem, @MappingTarget CartItemDTO cartItemDTO) {
        cartItemDTO.setProductName(cartItem.getProduct().getProductName());
        cartItemDTO.setProductImages(cartItem.getProduct().getProductImages());
    }
}
