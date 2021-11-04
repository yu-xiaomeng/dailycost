package com.xiaomeng.dailycost.domain;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@Entity
public class BillDetails {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @NotNull(message="categoryId must not be null")
    private String categoryId;

    @NotNull(message="type must not be null")

    private String type;

    @NotNull(message="amount must not be null")
    @DecimalMin(value="0.0")
    private double amount;

    @Size(max=100, message="note length must less than 100")
    private String note;

    @DateTimeFormat(pattern = "yyyy-mm-dd")
    @NotNull(message="date must not be null")
    private Date date;

    private String createdBy;
    private long createdTime;
    private long updatedTime;
}
