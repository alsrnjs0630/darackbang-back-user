package com.lab.darackbang.service.payment;

import com.lab.darackbang.entity.Cart;
import com.lab.darackbang.entity.Member;
import com.lab.darackbang.entity.Order;
import com.lab.darackbang.exception.UserNotFoundException;
import com.lab.darackbang.mapper.payment.PaymentMapper;
import com.lab.darackbang.repository.CartItemRepository;
import com.lab.darackbang.repository.CartRepository;
import com.lab.darackbang.repository.MemberRepository;
import com.lab.darackbang.repository.PaymentRepository;
import com.lab.darackbang.security.dto.LoginDTO;
import com.lab.darackbang.service.order.OrderService;
import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class PaymentServiceImpl implements PaymentService {
    private final OrderService orderService;
    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;
    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;
    private final MemberRepository memberRepository;

    @Value("${iamport.key}")
    private String restApiKey;

    @Value("${iamport.secret}")
    private String restApiSecret;

    private IamportClient iamportClient;

    @PostConstruct
    public void init() {
        try {
            this.iamportClient = new IamportClient(restApiKey, restApiSecret);
            log.info("IamportClient 초기화에 성공했습니다.");
        } catch (Exception e) {
            log.error("IamportClient 초기화에 실패했습니다: {} 에러: {}", e.getMessage(), e);
        }
    }

    @Override
    public Map<String, String> cartPayment(String impUid, List<Long> cartItemIds) throws IamportResponseException, IOException {

        try {
            log.info("결제 아이디: ", impUid);

            // 회원정보 조회
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            LoginDTO loginDTO = (LoginDTO) authentication.getPrincipal();
            Member member = memberRepository.findByUserEmail(loginDTO.getUserEmail())
                    .orElseThrow(() -> new UserNotFoundException("회원 정보를 찾을 수 없습니다."));

            IamportResponse<Payment> paymentIamportResponse = iamportClient.paymentByImpUid(impUid);

            // payment 객체 생성
            com.lab.darackbang.entity.Payment payment = paymentMapper.toEntity(paymentIamportResponse.getResponse());
            payment.setOrder(orderService.registerCartOrder(cartItemIds));

            // payment 저장
            paymentRepository.save(payment);

            // 결제한 회원의 카트 조회
            Cart cart = cartRepository.findByMemberId(member.getId())
                    .orElseThrow(() -> new RuntimeException("카트가 존재하지 않습니다."));

            // 구매 상품 카트 목록에서 삭제
            cartItemRepository.deleteAllById(cartItemIds);

            // 카트에 속한 상품 삭제
            cart.getCartItems().removeIf(cartItem -> cartItemIds.contains(cartItem.getId()));

            // 장바구니에 상품이 없다면 장바구니 삭제
            if (cart.getCartItems().isEmpty()) {
                cartRepository.deleteById(cart.getId());
                log.info("장바구니 상품 모두 구매. 장바구니 삭제");
            }
            return Map.of("RESULT", "SUCCESS");

        } catch (IamportResponseException | IOException e) {
            log.info("결제 실패 : ", e);
            e.printStackTrace();
            return Map.of("RESULT", "FAIL");
        } catch (UserNotFoundException e) {
            log.info("유저 정보를 찾을 수 없습니다. : ", e);
            return Map.of("RESULT", "FAIL");
        } catch (RuntimeException e) {
            log.info("카트 정보를 찾을 수 없습니다. : ", e);
            return Map.of("RESULT", "FAIL");
        }
    }

    @Override
    public Map<String, String> buyNowPayment(String impUid, Long productId, Integer quantity) throws IamportResponseException, IOException {

        try {
            log.info("결제 아이디: ", impUid);

            // 회원정보 조회
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            LoginDTO loginDTO = (LoginDTO) authentication.getPrincipal();
            Member member = memberRepository.findByUserEmail(loginDTO.getUserEmail())
                    .orElseThrow(() -> new UserNotFoundException("회원 정보를 찾을 수 없습니다."));

            IamportResponse<Payment> paymentIamportResponse = iamportClient.paymentByImpUid(impUid);

            // payment 객체 생성
            com.lab.darackbang.entity.Payment payment = paymentMapper.toEntity(paymentIamportResponse.getResponse());

            payment.setOrder(orderService.registerBuyNowOrder(productId, quantity));

            // payment 저장
            paymentRepository.save(payment);

            return Map.of("RESULT", "SUCCESS");

        } catch (IamportResponseException | IOException e) {
            log.info("결제 실패 : ", e);
            e.printStackTrace();
            return Map.of("RESULT", "FAIL");
        } catch (UserNotFoundException e) {
            log.info("유저 정보를 찾을 수 없습니다. : ", e);
            return Map.of("RESULT", "FAIL");
        } catch (RuntimeException e) {
            log.info("카트 정보를 찾을 수 없습니다. : ", e);
            return Map.of("RESULT", "FAIL");
        }
    }


}