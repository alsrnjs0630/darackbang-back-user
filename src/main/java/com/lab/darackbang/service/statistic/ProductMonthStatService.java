package com.lab.darackbang.service.statistic;

import com.lab.darackbang.entity.ProductMonthStat;
import java.util.Optional;

public interface ProductMonthStatService {

    Optional<ProductMonthStat> findByPnoAndYearAndMonth(String pno, String year, String month);

    ProductMonthStat create(ProductMonthStat productMonthStat);

    ProductMonthStat update(ProductMonthStat productMonthStat);

}
