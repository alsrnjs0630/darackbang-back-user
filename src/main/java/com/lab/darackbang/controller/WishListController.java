package com.lab.darackbang.controller;

import com.lab.darackbang.dto.wishList.WishListDTO;
import com.lab.darackbang.repository.WishListRepository;
import com.lab.darackbang.service.WishlistService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("api/wishlists")
 public class WishListController {

    private final WishlistService wishlistService;
    private final WishListRepository wishListRepository;

    // 관심상품 리스트 출력

    // 관심상품 등록 -> 관심상품 테이블에 추가, 상품 관심상품카운트 + 1
    @PostMapping("/{id}")
    public Map<String, String> wishlistRegister(@PathVariable Long id) {
        return wishlistService.register(id);
    }

    // 관심상품 삭제
    @DeleteMapping("/{id}")
    public Map<String, String> wishlistDelete(@PathVariable Long id) {
        return null;
    }
}
