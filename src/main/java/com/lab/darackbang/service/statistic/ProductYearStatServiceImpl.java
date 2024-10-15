package com.lab.darackbang.service.statistic;

import com.lab.darackbang.entity.ProductYearStat;
import com.lab.darackbang.repository.ProductYearStatRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class ProductYearStatServiceImpl implements ProductYearStatService{

    private final ProductYearStatRepository productYearStatRepository;

    @Override
    @Transactional(readOnly = true)
    public Optional<ProductYearStat> findByPnoAndYear(String pno, String year) {
        return productYearStatRepository.findByPnoAndYear(pno,year);
    }

    @Override
    public ProductYearStat create(ProductYearStat productYearStat) {
        return productYearStatRepository.save(productYearStat);
    }

    @Override
    public ProductYearStat update(ProductYearStat productYearStat) {
        return productYearStatRepository.save(productYearStat);
    }
}
