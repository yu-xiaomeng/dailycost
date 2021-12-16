package com.xiaomeng.dailycost.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface BillRepository extends JpaRepository<Bill, String> {
    @Query(value ="select * from bill where year(date) = year(?1) and month(date) = month(?1) and created_by = ?2", nativeQuery = true)
    List<Bill> findByMonth(Date date, String username);

    @Query(value ="select * from bill where date = ?1 and created_by = ?2", nativeQuery = true)
    List<Bill> findByDay(Date date, String username);

    @Query(value ="select sum(amount), `type`, year(`date`), month(`date`) from bill where (created_by = ?2 and year(`date`) = year(?1) and month(`date`) = month(?1)) group by year(`date`), month(`date`), `type`", nativeQuery = true)
    List<Object[]> findMonthlyBill(Date date, String username);

    @Query(value ="select sum(amount) from bill where year(`date`) = year(?1) and month(`date`) = month(?1) and `type` = ?2 and created_by = ?3 ", nativeQuery = true)
    Double monthlyBillStat(Date date, String type, String username);

    @Modifying
    @Query(value ="delete from bill where category_id = ?1 ", nativeQuery = true)
    void deleteByCategoryId (String id);

}
