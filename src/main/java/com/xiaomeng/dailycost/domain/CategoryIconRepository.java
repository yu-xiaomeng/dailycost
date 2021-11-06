package com.xiaomeng.dailycost.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryIconRepository extends JpaRepository<CategoryIcon, String> {
}
