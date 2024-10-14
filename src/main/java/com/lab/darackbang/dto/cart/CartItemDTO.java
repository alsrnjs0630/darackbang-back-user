package com.lab.darackbang.dto.cart;

import lombok.Getter;
import lombok.Setter;

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
}
