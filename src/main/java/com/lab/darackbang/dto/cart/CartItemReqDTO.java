package com.lab.darackbang.dto.cart;

import lombok.*;

@Getter
@Setter
public class CartItemReqDTO {
    //상품아이디
    private Long id;

    //주문 수량
    private Integer quantity;
}
