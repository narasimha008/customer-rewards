package com.mns.cr.service.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    @JoinColumn
    private Customer customer;
    private Double total;

    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    private Date saveDate;

}
