package com.lab.darackbang.controller;

import com.lab.darackbang.entity.Order;
import com.lab.darackbang.service.order.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
@Slf4j
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/add")
    public Order orderAdd (@RequestBody List<Long> cartItemIds) {
        log.info("구매내역 컨트롤러 접근 성공");
        return orderService.registerOrder(cartItemIds);
    }
}
