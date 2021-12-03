package com.xiaomeng.dailycost.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {
    @Query(value ="select * from category where name = ?1 and created_by = ?2", nativeQuery = true)
    Optional<Category> findByName(String name, String username);

    @Query(value ="select * from category where id = ?1 and type = ?2 and created_by = ?3", nativeQuery = true)
    Optional<Category> findByIdTypeUser(String id, String type, String username);

    @Query(value ="select * from category where created_by = ?1", nativeQuery = true)
    List<Category> findAllByUser(String username);

    @Query(value ="select * from category where id = ?1 and created_by = ?2", nativeQuery = true)
    Optional<Category> findByIdUser(String id, String username);
}
