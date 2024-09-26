package com.lab.darackbang.controller;

import com.lab.darackbang.dto.wishList.WishListDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Slf4j
@RequestMapping("api/wishlists")
 public class WishListController {

    // 관심상품 리스트 출력

    // 관심상품 등록
    @PostMapping
    public Map<String, String> wishlistRegister(WishListDTO wishListDTO) {


        return Map.of("RESULT", "SUCCESS");
    }

    // 관심상품 삭제
}
