package com.lab.darackbang.service.wishlist;

import java.util.Map;

public interface WishlistService{
    Map<String,String> register(Long id);       // 관심상품 등록
    Map<String,String> delete(Long id);
}
