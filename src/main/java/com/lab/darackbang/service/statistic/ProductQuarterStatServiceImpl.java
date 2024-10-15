package com.lab.darackbang.service.statistic;

import com.lab.darackbang.entity.ProductQuarterStat;
import com.lab.darackbang.repository.ProductQuarterStatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductQuarterStatServiceImpl implements ProductQuarterStatService{

    private final ProductQuarterStatRepository productQuarterStatRepository;

    @Override
    @Transactional(readOnly = true)
    public Optional<ProductQuarterStat> findByPnoAndYearAndQuarter(String pno, String year, String quarter) {
        return productQuarterStatRepository.findByPnoAndYearAndQuarter(pno,year,quarter);
    }

    @Override
    public ProductQuarterStat create(ProductQuarterStat productQuarterStat) {
        return productQuarterStatRepository.save(productQuarterStat);
    }

    @Override
    public ProductQuarterStat update(ProductQuarterStat productQuarterStat) {
        return productQuarterStatRepository.save(productQuarterStat);
    }
}
