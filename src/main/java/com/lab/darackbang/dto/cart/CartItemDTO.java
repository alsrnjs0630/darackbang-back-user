package com.lab.darackbang.dto.cart;

import com.lab.darackbang.entity.ProductImage;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class CartItemDTO {

    //장바구니아이템 아이디
    private Long id;
    //상품명
    private String productName;
    //상품 수량
    private Integer quantity;
    //상품 가격
    private Integer productPrice;
    //상품 이미지 리스트
    private List<ProductImage> productImages;
}
