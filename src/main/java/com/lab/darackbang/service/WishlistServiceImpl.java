package com.lab.darackbang.service;


import com.lab.darackbang.dto.member.MemberDTO;
import com.lab.darackbang.dto.wishList.WishListDTO;
import com.lab.darackbang.entity.Member;
import com.lab.darackbang.entity.WishList;
import com.lab.darackbang.mapper.WishlistMapper;
import com.lab.darackbang.repository.MemberRepository;
import com.lab.darackbang.repository.ProductRepository;
import com.lab.darackbang.repository.WishListRepository;
import com.lab.darackbang.security.dto.LoginDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class WishlistServiceImpl implements WishlistService {

    private final WishListRepository wishListRepository;
    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;

    // 관심상품 등록
    @Override
    public Map<String, String> register(Long id) {

        // 현재 로그인한 사용자의 회원 번호
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            return Map.of("RESULT", "사용자 정보를 찾을 수 없습니다.");
        }

        Object principal = authentication.getPrincipal(); // 현재 로그인한 회원 정보

        // 제품 정보 조회
        return productRepository.findById(id).map(product -> {
            if (principal instanceof LoginDTO) {
                // 현재 로그인한 사용자 정보
                LoginDTO loginDTO = (LoginDTO) principal;
                Member member = memberRepository.findByUserEmail(loginDTO.getUserEmail()).orElseThrow();

                // 위시리스트 생성
                WishList wishList = WishList.builder()
                        .member(member)
                        .product(product)
                        .build();

                // 위시리스트 저장 로직 추가
                wishListRepository.save(wishList); // wishList를 데이터베이스에 저장

                // 상품 좋아요 + 1
                product.setWishCount(product.getWishCount() + 1);
                productRepository.save(product);

                // 결과 저장
                return Map.of("RESULT", "위시리스트에 추가되었습니다.");
            } else {
                // 사용자 정보가 Member가 아닌 경우
                return Map.of("RESULT", "인증되지 않은 사용자입니다.");
            }
        }).orElseGet(() -> {
            // 제품이 존재하지 않는 경우
            return Map.of("RESULT", "제품 정보를 찾을 수 없습니다.");
        });
    }
}
