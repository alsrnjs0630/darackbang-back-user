package com.lab.darackbang.service.statistic;

import com.lab.darackbang.entity.AgeMonthStat;
import com.lab.darackbang.repository.AgeMonthStatRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AgeMonthStatServiceImpl implements AgeMonthStatService{

    private final AgeMonthStatRepository ageMonthStatRepository;

    @Override
    @Transactional(readOnly = true)
    public Optional<AgeMonthStat> findByAgeGroupAndYearAndMonth(String ageGroup, String year, String month) {
        return ageMonthStatRepository.findByAgeGroupAndYearAndMonth(ageGroup,year,month);
    }

    @Override
    public AgeMonthStat create(AgeMonthStat ageMonthStat) {
        return ageMonthStatRepository.save(ageMonthStat);
    }

    @Override
    public AgeMonthStat update(AgeMonthStat ageMonthStat) {
        return ageMonthStatRepository.save(ageMonthStat);
    }
}
