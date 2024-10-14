package com.lab.darackbang.controller;


import com.lab.darackbang.dto.Order.OrderDTO;
import com.lab.darackbang.entity.Order;
import com.lab.darackbang.entity.OrderItem;
import com.lab.darackbang.repository.CartItemRepository;
import com.lab.darackbang.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
@Slf4j
public class OrderController {
    private final OrderRepository orderRepository;
    private final CartItemRepository cartItemRepository;

    @GetMapping
    public Order addOrder(List<Long> cartItemIds) {
        Order order = new Order();
        List<OrderItem> orderItems = new ArrayList<>();

        cartItemRepository.findAllById(cartItemIds).forEach(cartItem -> {
           order.setOrderDate(LocalDate.now());
           order.setTotalOrderPrice(
                   order.getTotalOrderPrice() == null ? 0 : order.getTotalOrderPrice() + (cartItem.getProductPrice() * cartItem.getQuantity())
           );
          // order.setMember();

        });

        return null;
    }
}
