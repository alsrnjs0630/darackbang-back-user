package com.lab.darackbang.repository;

import com.lab.darackbang.entity.AgeMonthStat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AgeMonthStatRepository extends JpaRepository<AgeMonthStat, Long>,
        JpaSpecificationExecutor<AgeMonthStat> {

   Optional<AgeMonthStat> findByAgeGroupAndYearAndMonth(String ageGroup, String year, String month);
}