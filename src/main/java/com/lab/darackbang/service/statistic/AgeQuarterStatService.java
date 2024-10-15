package com.lab.darackbang.service.statistic;

import com.lab.darackbang.entity.AgeQuarterStat;
import java.util.Optional;

public interface AgeQuarterStatService {

    Optional<AgeQuarterStat> findByAgeGroupAndYearAndQuarter(String ageGroup, String year, String quarter);

    AgeQuarterStat create(AgeQuarterStat ageQuarterStat);

    AgeQuarterStat update(AgeQuarterStat ageQuarterStat);

}
