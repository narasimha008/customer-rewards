package com.mns.cr.service.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
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

    public Long calculatePoints() {
        long points = 0l;
        if (this.total > 50 && this.total <= 100) {
            points += (this.total.intValue() - 50) * 1;
        }
        if (this.total > 100) {
            //1 point for every dollar spent over $50
            points += 50;
            //2 points for every dollar spent over $100
            points += (this.total.intValue() - 100) * 2;
        }
        return points;
    }

}
