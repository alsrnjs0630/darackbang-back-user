package com.lab.darackbang.service.wishlist;

import com.lab.darackbang.entity.Member;
import com.lab.darackbang.entity.WishList;
import com.lab.darackbang.repository.MemberRepository;
import com.lab.darackbang.repository.ProductRepository;
import com.lab.darackbang.repository.WishListRepository;
import com.lab.darackbang.security.dto.LoginDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class WishlistServiceImpl implements WishlistService {

    private final WishListRepository wishListRepository;
    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;

    /*
    * 관심상품 등록
    * */
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

                if(wishListRepository.findByProductIdAndMemberId(id,member.getId())==null) {
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
                    return Map.of("RESULT", "이미 위시리스트에 등록되어있습니다.");
                }
            } else {
                // 사용자 정보가 Member가 아닌 경우
                return Map.of("RESULT", "인증되지 않은 사용자입니다.");
            }
        }).orElseGet(() -> {
            // 제품이 존재하지 않는 경우
            return Map.of("RESULT", "제품 정보를 찾을 수 없습니다.");
        });
    }

    /*
    * 관심상품 삭제
    * */
    @Override
    public Map<String, String> delete(Long id) {

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

                /*
                * 현재 로그인한 사용자 id와 파라미터값으로 가져온 상품 id로 관심상품 테이블에서 레코드 조회하여 조회된 레코드 삭제
                * */
                WishList wishList = wishListRepository.findByProductIdAndMemberId(id, member.getId());

                // 테이블에서 관심상품 삭제
                wishListRepository.delete(wishList);

                // 삭제 된 관심상품에 해당하는 상품 좋아요 카운트 -1
                product.setWishCount(product.getWishCount() - 1);
                productRepository.save(product);

                // 결과 저장
                return Map.of("RESULT", "위시리스트에서 삭제되었습니다.");
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
