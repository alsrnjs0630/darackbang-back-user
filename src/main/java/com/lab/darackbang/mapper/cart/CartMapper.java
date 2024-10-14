package com.lab.darackbang.mapper.cart;


import com.lab.darackbang.dto.cart.CartDTO;
import com.lab.darackbang.entity.Cart;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper (componentModel = "spring")
public interface CartMapper {
    CartMapper INSTANCE = Mappers.getMapper(CartMapper.class);
    CartDTO toDTO(Cart cart);
    Cart toEntity(CartDTO cartDTO);
}
