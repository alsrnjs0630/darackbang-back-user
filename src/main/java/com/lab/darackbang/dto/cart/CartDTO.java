package com.lab.darackbang.dto.cart;

import com.lab.darackbang.entity.CartItem;
import com.lab.darackbang.entity.Member;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Setter
@Getter
public class CartDTO {

    private Long id;

    private Member member;

    private List<CartItem> cartItems;
}

