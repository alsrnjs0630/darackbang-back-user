package com.lab.darackbang.service.order;

import com.lab.darackbang.entity.*;
import com.lab.darackbang.repository.CartItemRepository;
import com.lab.darackbang.repository.MemberRepository;
import com.lab.darackbang.repository.OrderRepository;
import com.lab.darackbang.security.dto.LoginDTO;
import com.lab.darackbang.service.statistic.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {
    private final CartItemRepository cartItemRepository;
    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;

    private final AgeMonthStatService ageMonthStatService;

    private final AgeYearStatService ageYearStatService;

    private final AgeQuarterStatService ageQuarterStatService;

    private final ProductYearStatService productYearStatService;

    private final ProductMonthStatService productMonthStatService;

    private final ProductQuarterStatService productQuarterStatService;


    @Override
    public Map<String, String> registerOrder(List<Long> cartItemIds) {
        log.info("구매내역 등록 시작-------------------");

        // Authentication 객체 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginDTO loginDTO = (LoginDTO) authentication.getPrincipal(); // principal을 LoginDTO로 캐스팅
        log.info("멤버 정보: {} ", loginDTO);

        Member member = memberRepository.findByUserEmail(loginDTO.getUserEmail()).orElseThrow();

        Order order = new Order();
        List<OrderItem> orderItems = new ArrayList<>();

        try {
            cartItemRepository.findAllById(cartItemIds).forEach(cartItem -> {
                OrderItem orderItem = new OrderItem();
                if (order.getTotalOrderPrice() == null) {
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

            Order newOrder = orderRepository.save(order);

            //통계데이터 생성
            createStat(newOrder);

            return Map.of("RESULT", "SUCCESS");
        } catch (Exception e) {
            log.error("error {}", e.getMessage());
            return Map.of("RESULT", "FAIL");
        }

    }
    /**
     * 주문 상품 통계 데이터 생성
     * @param order
     */
    private void createStat(Order order) {

        String year = String.valueOf(order.getOrderDate().getYear());
        String quarter = String.valueOf((order.getOrderDate().getMonthValue() - 1) / 3 + 1);
        String month = String.valueOf(order.getOrderDate().getMonthValue());

        //연령별 연 총주문 금액 생성및 업데이트
        ageYearStatService.findByAgeGroupAndYear(order.getMember().getAgeGroup(), year).ifPresentOrElse(ageYearStat -> {
                    ageYearStat.setSaleTotalPrice(ageYearStat.getSaleTotalPrice() + order.getTotalOrderPrice());
                    ageYearStatService.update(ageYearStat);
                }, () -> {
                    AgeYearStat ageYearStat = new AgeYearStat();
                    ageYearStat.setAgeGroup(order.getMember().getAgeGroup());
                    ageYearStat.setYear(year);
                    ageYearStat.setSaleTotalPrice(order.getTotalOrderPrice());
                    ageYearStatService.create(ageYearStat);
                }
        );

        //연령별 분기 총주문 금액 생성및 업데이트
        ageQuarterStatService.findByAgeGroupAndYearAndQuarter(order.getMember().getAgeGroup(), year, quarter).ifPresentOrElse(ageQuarterStat -> {
                    ageQuarterStat.setSaleTotalPrice(ageQuarterStat.getSaleTotalPrice() + order.getTotalOrderPrice());
                    ageQuarterStatService.update(ageQuarterStat);
                }, () -> {
                    AgeQuarterStat ageQuarterStat = new AgeQuarterStat();
                    ageQuarterStat.setAgeGroup(order.getMember().getAgeGroup());
                    ageQuarterStat.setYear(year);
                    ageQuarterStat.setQuarter(quarter);
                    ageQuarterStat.setSaleTotalPrice(order.getTotalOrderPrice());
                    ageQuarterStatService.create(ageQuarterStat);
                }
        );

        //연령별 달 총주문 금액 생성및 업데이트
        ageMonthStatService.findByAgeGroupAndYearAndMonth(order.getMember().getAgeGroup(), year, month).ifPresentOrElse(ageMonthStat -> {
                    ageMonthStat.setSaleTotalPrice(ageMonthStat.getSaleTotalPrice() + order.getTotalOrderPrice());
                    ageMonthStatService.update(ageMonthStat);
                }, () -> {
                    AgeMonthStat ageMonthStat = new AgeMonthStat();
                    ageMonthStat.setAgeGroup(order.getMember().getAgeGroup());
                    ageMonthStat.setYear(year);
                    ageMonthStat.setMonth(month);
                    ageMonthStat.setSaleTotalPrice(order.getTotalOrderPrice());
                    ageMonthStatService.create(ageMonthStat);
                }
        );

        order.getOrderItems().forEach(orderItem -> {

            //상품별 년 총주문 금액 생성및 업데이트
            productYearStatService.findByPnoAndYear(orderItem.getProduct().getPno(), year).ifPresentOrElse(productYearStat -> {
                        productYearStat.setProductName(orderItem.getProduct().getProductName());
                        productYearStat.setSaleTotalPrice(orderItem.getProductPrice()*orderItem.getProductQuantity()+productYearStat.getSaleTotalPrice());
                        productYearStatService.update(productYearStat);
                    },
                    () -> {
                        ProductYearStat productYearStat = new ProductYearStat();
                        productYearStat.setPno(orderItem.getProduct().getPno());
                        productYearStat.setProductName(orderItem.getProduct().getProductName());
                        productYearStat.setYear(year);
                        productYearStat.setSaleTotalPrice(orderItem.getProductPrice());
                        productYearStatService.create(productYearStat);
                    }
            );

            //상품별 분기 총주문 금액 생성및 업데이트
            productQuarterStatService.findByPnoAndYearAndQuarter(orderItem.getProduct().getPno(), year,quarter).ifPresentOrElse(productQuarterStat -> {
                        productQuarterStat.setProductName(orderItem.getProduct().getProductName());
                        productQuarterStat.setSaleTotalPrice(orderItem.getProductPrice()*orderItem.getProductQuantity()+productQuarterStat.getSaleTotalPrice());
                        productQuarterStatService.update(productQuarterStat);
                    },
                    () -> {
                        ProductQuarterStat productQuarterStat = new ProductQuarterStat();
                        productQuarterStat.setPno(orderItem.getProduct().getPno());
                        productQuarterStat.setProductName(orderItem.getProduct().getProductName());
                        productQuarterStat.setYear(year);
                        productQuarterStat.setQuarter(quarter);
                        productQuarterStat.setSaleTotalPrice(orderItem.getProductPrice());
                        productQuarterStatService.create(productQuarterStat);
                    }
            );

            //상품별 달 총주문 금액 생성및 업데이트
            productMonthStatService.findByPnoAndYearAndMonth(orderItem.getProduct().getPno(), year,month).ifPresentOrElse(productMonthStat ->  {
                        productMonthStat.setProductName(orderItem.getProduct().getProductName());
                        productMonthStat.setSaleTotalPrice(orderItem.getProductPrice()*orderItem.getProductQuantity()+productMonthStat.getSaleTotalPrice());
                        productMonthStatService.update(productMonthStat);
                    },
                    () -> {
                        ProductMonthStat productMonthStat = new ProductMonthStat();
                        productMonthStat.setPno(orderItem.getProduct().getPno());
                        productMonthStat.setProductName(orderItem.getProduct().getProductName());
                        productMonthStat.setYear(year);
                        productMonthStat.setMonth(month);
                        productMonthStat.setSaleTotalPrice(orderItem.getProductPrice());
                        productMonthStatService.create(productMonthStat);
                    }
            );
        });
    }
}
