package com.xiaomeng.dailycost.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface BillRepository extends JpaRepository<Bill, String> {
    @Query(value ="select * from bill where date = ?1 and username = ?2", nativeQuery = true)
    Bill findByDate(Date date, String username);

    @Query(value ="select * from bill where year(date) = year(?1) and month(date) = month(?1) and username = ?2", nativeQuery = true)
    List<Bill> findByMonth(Date date, String username);
}
