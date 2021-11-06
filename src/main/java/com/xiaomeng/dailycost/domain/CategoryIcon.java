package com.xiaomeng.dailycost.domain;

import lombok.Data;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Entity
public class CategoryIcon {
    @Id
    private String id;
    private String name;
    private String url;
}
