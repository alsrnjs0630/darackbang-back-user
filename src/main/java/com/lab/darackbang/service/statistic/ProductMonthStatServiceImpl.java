package com.lab.darackbang.service.statistic;

import com.lab.darackbang.entity.ProductMonthStat;
import com.lab.darackbang.repository.ProductMonthStatRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ProductMonthStatServiceImpl implements ProductMonthStatService{

    private final ProductMonthStatRepository productMonthStatRepository;

    @Override
    @Transactional(readOnly = true)
    public Optional<ProductMonthStat> findByPnoAndYearAndMonth(String pno, String year, String month) {
        return productMonthStatRepository.findByPnoAndYearAndMonth(pno,year,month);
    }

    @Override
    public ProductMonthStat create(ProductMonthStat productMonthStat) {
        return productMonthStatRepository.save(productMonthStat);
    }

    @Override
    public ProductMonthStat update(ProductMonthStat productMonthStat) {
        return productMonthStatRepository.save(productMonthStat);
    }
}
