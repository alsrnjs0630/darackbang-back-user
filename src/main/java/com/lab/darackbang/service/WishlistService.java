package com.lab.darackbang.service;

import com.lab.darackbang.dto.wishList.WishListDTO;

import java.util.Map;

public interface WishlistService{
    Map<String,String> register(Long id);       // 관심상품 등록
}
