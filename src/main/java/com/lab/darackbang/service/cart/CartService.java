package com.lab.darackbang.service.cart;

import com.lab.darackbang.dto.cart.CartItemDTO;
import com.lab.darackbang.dto.cart.CartItemReqDTO;
import com.lab.darackbang.entity.CartItem;
import com.lab.darackbang.entity.Member;

import java.util.List;
import java.util.Map;

public interface CartService {

    Map<String, String> addCartItem(CartItemReqDTO request);
    void deleteCartItem(Member member, CartItem cartItem);
    void deleteCart(Member member);
    List<CartItemDTO> getCartList(Member member);
}
