package com.lab.darackbang.service.order;

import com.lab.darackbang.entity.Member;
import com.lab.darackbang.entity.Order;
import com.lab.darackbang.entity.OrderItem;
import com.lab.darackbang.repository.CartItemRepository;
import com.lab.darackbang.repository.MemberRepository;
import com.lab.darackbang.repository.OrderRepository;
import com.lab.darackbang.security.dto.LoginDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{
    private final CartItemRepository cartItemRepository;
    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;

    @Override
    public Map<String, String> registerOrder(List<Long> cartItemIds) {
        log.info("구매내역 등록 시작-------------------");

        // Authentication 객체 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginDTO loginDTO = (LoginDTO) authentication.getPrincipal(); // principal을 LoginDTO로 캐스팅
        log.info("멤버 정보: " + loginDTO);

        Member member = memberRepository.findByUserEmail(loginDTO.getUserEmail()).orElseThrow();

        Order order = new Order();
        List<OrderItem> orderItems = new ArrayList<>();

        try {
            cartItemRepository.findAllById(cartItemIds).forEach(cartItem -> {
                OrderItem orderItem = new OrderItem();
                if(order.getTotalOrderPrice() == null) {
                    order.setTotalOrderPrice(0);
                }
                order.setTotalOrderPrice(order.getTotalOrderPrice() + (cartItem.getProductPrice() * cartItem.getQuantity()));
                // 구매상품 설정
                orderItem.setProduct(cartItem.getProduct());
                orderItem.setProductPrice(cartItem.getProductPrice());
                orderItem.setProductQuantity(cartItem.getQuantity());
                orderItem.setOrder(order);
                orderItems.add(orderItem);
            });

            order.setOrderItems(orderItems);        // 구매아이템 리스트 설정
            order.setOrderDate(LocalDate.now());    // 주문내역 주문일 설정
            order.setMember(member);                // 주문내역 회원정보 설정

            orderRepository.save(order);
            return Map.of("RESULT", "SUCCESS");
        } catch (Exception e) {
            e.printStackTrace();
            return Map.of("RESULT", "FAIL");
        }

    }
}
