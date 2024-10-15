package com.lab.darackbang.service.statistic;

import com.lab.darackbang.entity.AgeYearStat;
import java.util.Optional;

public interface AgeYearStatService {

    Optional<AgeYearStat> findByAgeGroupAndYear(String ageGroup, String year);

    AgeYearStat create(AgeYearStat ageYearStat);

    AgeYearStat update(AgeYearStat ageYearStat);

}
