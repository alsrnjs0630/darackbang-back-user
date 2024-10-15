package com.lab.darackbang.repository;

import com.lab.darackbang.entity.ProductYearStat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductYearStatRepository extends JpaRepository<ProductYearStat, Long> ,
        JpaSpecificationExecutor<ProductYearStat> {

    Optional<ProductYearStat> findByPnoAndYear(String pno, String year);
}