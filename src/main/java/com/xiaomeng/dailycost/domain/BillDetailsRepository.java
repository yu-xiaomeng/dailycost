package com.xiaomeng.dailycost.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface BillDetailsRepository extends JpaRepository<BillDetails, String> {
    @Query(value ="select * from bill_details where year(date) = year(?1) and month(date) = month(?1) and created_by = ?2", nativeQuery = true)
    List<BillDetails> findByDate(Date date, String username);

    @Query(value ="select * from bill_details where date = ?1 and created_by = ?2", nativeQuery = true)
    List<BillDetails> findByDay(Date date, String username);
}
