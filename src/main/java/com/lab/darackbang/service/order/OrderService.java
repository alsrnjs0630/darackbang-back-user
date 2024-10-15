package com.lab.darackbang.service.order;

import java.util.List;
import java.util.Map;

public interface OrderService {
    Map<String, String> registerOrder(List<Long> cartItemIds);
}
