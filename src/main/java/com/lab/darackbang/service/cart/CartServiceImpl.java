package com.lab.darackbang.service.cart;

import com.lab.darackbang.dto.cart.CartItemDTO;
import com.lab.darackbang.dto.cart.CartItemReqDTO;

import com.lab.darackbang.entity.Cart;
import com.lab.darackbang.entity.CartItem;
import com.lab.darackbang.entity.Member;
import com.lab.darackbang.mapper.cart.CartItemMapper;
import com.lab.darackbang.mapper.cart.CartMapper;
import com.lab.darackbang.repository.CartItemRepository;
import com.lab.darackbang.repository.CartRepository;
import com.lab.darackbang.repository.MemberRepository;
import com.lab.darackbang.repository.ProductRepository;
import com.lab.darackbang.security.dto.LoginDTO;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CartServiceImpl implements CartService {
    private final CartItemMapper cartItemMapper;
    private final CartMapper cartMapper;

    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;
    private final CartRepository cartRepository;


    /**
     * 장바구니 아이템 추가 코드
     * @param request 프론트에서 추가한 상품정보(상품아이디, 상품 주문수량)
     * @return
     */
    @Override
    public Map<String, String> addCartItem(CartItemReqDTO request) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginDTO loginDTO = (LoginDTO) authentication.getPrincipal();

        if (loginDTO != null) {

            memberRepository.findByUserEmail(loginDTO.getUserEmail()).ifPresentOrElse(existMember ->
                            productRepository.findById(request.getId()).ifPresent(product -> {

                                if (existMember.getCart() == null) {
                                    //카트 생성
                                    List<CartItem> newCartItemList = new ArrayList<>();

                                    //새로운 카트 생성
                                    Cart newCart = new Cart();
                                    newCart.setMember(existMember);
                                    //카트 아이템 생성
                                    CartItem newCartItem = new CartItem();
                                    newCartItem.setCart(newCart);
                                    newCartItem.setProduct(product);
                                    newCartItem.setProductPrice(product.getSalePrice()*request.getQuantity());
                                    newCartItem.setQuantity(request.getQuantity());
                                    newCartItemList.add(newCartItem);

                                    //카트에 카트아이템 할당
                                    newCart.setCartItems(newCartItemList);

                                    //카트 저장
                                    cartRepository.save(newCart);

                                } else {

                                    //같은 상품이 있는지 검색
                                    Optional<CartItem> existingCartItem = existMember.getCart().getCartItems().stream()
                                            .filter(cartItem -> cartItem.getProduct().getId().equals(request.getId()))
                                            .findFirst();

                                    // 같은 상품이 있으면 수량업데이트 없으면 새상품 카트아이템에 등록
                                    if (existingCartItem.isPresent()) {
                                        existingCartItem.get().updateQuantity(request.getQuantity());
                                    } else {
                                        CartItem newCartItem = new CartItem();
                                        newCartItem.setCart(existMember.getCart());
                                        newCartItem.setProduct(product);
                                        newCartItem.setQuantity(request.getQuantity());
                                        newCartItem.setProductPrice(product.getSalePrice()*request.getQuantity());
                                        existMember.getCart().getCartItems().add(newCartItem);
                                    }

                                    cartRepository.save(existMember.getCart());
                                }
                            }), () -> {
                        throw new NoSuchElementException("Member Not Found");
                    }
            );
        }
        return Map.of("RESULT", "SUCCESS");
    }

    //장바구니아이템 삭제
    @Override
    public void deleteCartItem(Member member, CartItem cartItem) {
        CartItem cartItems = findCartItem(cartItem.getId());
        checkCart(member, cartItems);
        cartItemRepository.delete(cartItems);
    }

    private CartItem findCartItem(Long id) {
        return cartItemRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Product Not Found"));
    }

    private void checkCart(@NotNull Member member, CartItem cartItems) {
        if (!member.getId().equals(cartItems.getCart().getMember().getId())) {
            throw new NoSuchElementException("Product Not Found");
        }
    }


    //장바구니 삭제
    @Override
    public void deleteCart(Member member) {
        cartRepository.deleteById(member.getId());
    }

    //장바구니 목록
    @Override
    public List<CartItemDTO> getCartList(Member member) {
        return cartRepository.findByMemberId(member.getId())
                .map(cart -> cart.getCartItems().stream()
                        .map(cartItemMapper::toDTO)
                        .toList())
                .orElse(Collections.emptyList());
    }


}
