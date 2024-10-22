package com.lab.darackbang.service.order;

import com.lab.darackbang.entity.Order;

import java.util.List;
import java.util.Map;

public interface OrderService {
    Order registerCartOrder(List<Long> cartItemIds);
    Order registerBuyNowOrder(Long productId, Integer quantity);
}
