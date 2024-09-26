package com.lab.darackbang.mapper;

import com.lab.darackbang.dto.wishList.WishListDTO;
import com.lab.darackbang.entity.WishList;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface WishlistMapper {

    WishListDTO toDTO(WishList wishList);

    @Mapping(target = "product", ignore = true)
    @Mapping(target = "member", ignore = true)
    WishList toEntity(WishListDTO wishListDTO);
}
