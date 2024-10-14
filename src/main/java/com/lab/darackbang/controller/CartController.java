package com.lab.darackbang.controller;

import com.lab.darackbang.dto.cart.CartItemDTO;
import com.lab.darackbang.dto.cart.CartItemReqDTO;
import com.lab.darackbang.entity.CartItem;
import com.lab.darackbang.entity.Member;
import com.lab.darackbang.repository.CartItemRepository;
import com.lab.darackbang.repository.MemberRepository;
import com.lab.darackbang.security.dto.LoginDTO;
import com.lab.darackbang.service.cart.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("api/carts")
public class CartController {

    private final CartService cartService;
    private final MemberRepository memberRepository;
    private final CartItemRepository cartItemRepository;

    // 장바구니 아이템 추가
    @PostMapping("/add")
    public ResponseEntity<Map<String, String>> addCartItem( @RequestBody CartItemReqDTO request) {
        Map<String, String> result = cartService.addCartItem(request);
        return ResponseEntity.ok(result);
    }

    // 장바구니 아이템 삭제
    @DeleteMapping("/delete/{cartItemId}")
    public ResponseEntity<Void> deleteCartItem(@PathVariable Long cartItemId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginDTO loginDTO = (LoginDTO) authentication.getPrincipal();
        if (loginDTO != null) {
            Member member = memberRepository.findByUserEmail(loginDTO.getUserEmail())
                    .orElseThrow(() -> new NoSuchElementException("Member Not Found"));
            CartItem cartItem = cartItemRepository.findById(cartItemId)
                    .orElseThrow(() -> new NoSuchElementException("CartItem Not Found"));

            cartService.deleteCartItem(member, cartItem);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(403).build(); // Unauthorized
        }
    }

    // 장바구니 전체 삭제
    @DeleteMapping("/delete/{cartId}")
    public ResponseEntity<Void> deleteCart(@PathVariable Long cartId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginDTO loginDTO = (LoginDTO) authentication.getPrincipal();
        if (loginDTO != null) {
            Member member = memberRepository.findByUserEmail(loginDTO.getUserEmail())
                    .orElseThrow(() -> new NoSuchElementException("Member Not Found"));

            cartService.deleteCart(member);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(403).build(); // Unauthorized
        }
    }

    // 장바구니 목록 조회
    @GetMapping("/list")
    public ResponseEntity<List<CartItemDTO>> getCartList() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginDTO loginDTO = (LoginDTO) authentication.getPrincipal();
        if (loginDTO != null) {
            Member member = memberRepository.findByUserEmail(loginDTO.getUserEmail())
                    .orElseThrow(() -> new NoSuchElementException("Member Not Found"));

            List<CartItemDTO> cartList = cartService.getCartList(member);
            return ResponseEntity.ok(cartList);
        } else {
            return ResponseEntity.status(403).build(); // Unauthorized
        }
    }
}
