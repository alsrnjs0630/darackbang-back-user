package com.lab.darackbang.service.statistic;

import com.lab.darackbang.entity.ProductQuarterStat;
import java.util.Optional;

public interface ProductQuarterStatService {

    Optional<ProductQuarterStat> findByPnoAndYearAndQuarter(String pno, String year, String quarter);

    ProductQuarterStat create(ProductQuarterStat productQuarterStat);

    ProductQuarterStat update(ProductQuarterStat productQuarterStat);

}
