package com.lab.darackbang.service.statistic;

import com.lab.darackbang.entity.AgeQuarterStat;
import com.lab.darackbang.repository.AgeQuarterStatRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AgeQuarterStatServiceImpl implements AgeQuarterStatService {

    private final AgeQuarterStatRepository ageQuarterStatRepository;

    @Override
    @Transactional(readOnly = true)
    public Optional<AgeQuarterStat> findByAgeGroupAndYearAndQuarter(String ageGroup, String year, String quarter) {
        return ageQuarterStatRepository.findByAgeGroupAndYearAndQuarter(ageGroup, year, quarter);
    }

    @Override
    public AgeQuarterStat create(AgeQuarterStat ageQuarterStat) {
        return ageQuarterStatRepository.save(ageQuarterStat);
    }

    @Override
    public AgeQuarterStat update(AgeQuarterStat ageQuarterStat) {
        return ageQuarterStatRepository.save(ageQuarterStat);
    }
}
