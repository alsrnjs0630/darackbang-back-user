package com.lab.darackbang.service.statistic;

import com.lab.darackbang.entity.ProductYearStat;

import java.util.Optional;

public interface ProductYearStatService {

    Optional<ProductYearStat> findByPnoAndYear(String pno, String year);

    ProductYearStat create(ProductYearStat productYearStat);

    ProductYearStat update(ProductYearStat productYearStat);

}
