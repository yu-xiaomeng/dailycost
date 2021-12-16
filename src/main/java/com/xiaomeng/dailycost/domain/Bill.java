package com.xiaomeng.dailycost.domain;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Data
@Entity
public class Bill {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    private String categoryId;
    private String type;
    private double amount;
    private String note;
    private Date date;

    private String createdBy;
    private long createdTime;
    private long updatedTime;
}
