package com.lab.darackbang.service.statistic;

import com.lab.darackbang.entity.AgeMonthStat;
import java.util.Optional;

public interface AgeMonthStatService {

    Optional<AgeMonthStat> findByAgeGroupAndYearAndMonth(String ageGroup, String year, String month);

    AgeMonthStat create(AgeMonthStat ageMonthStat);

    AgeMonthStat update(AgeMonthStat ageMonthStat);

}
