package com.lab.darackbang.service.statistic;

import com.lab.darackbang.entity.AgeYearStat;
import com.lab.darackbang.repository.AgeYearStatRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AgeYearStatServiceImpl implements AgeYearStatService {
    private final AgeYearStatRepository ageYearStatRepository;

    @Override
    @Transactional(readOnly = true)
    public Optional<AgeYearStat> findByAgeGroupAndYear(String ageGroup, String year) {
        return ageYearStatRepository.findByAgeGroupAndYear(ageGroup, year);
    }

    @Override
    public AgeYearStat create(AgeYearStat ageYearStat) {
        return ageYearStatRepository.save(ageYearStat);
    }

    @Override
    public AgeYearStat update(AgeYearStat ageYearStat) {
        return ageYearStatRepository.save(ageYearStat);
    }
}
