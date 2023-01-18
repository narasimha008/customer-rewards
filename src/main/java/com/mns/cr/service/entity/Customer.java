package com.mns.cr.service.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Customer {

    @Id
    @GeneratedValue
    @NonNull
    private Integer id;

    @NonNull
    private String name;

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Transaction> transactions;

    public Long calculateRewardPoints() {
        if (transactions == null || transactions.isEmpty()) {
            return 0l;
        }
        return transactions.parallelStream().map(t -> t.calculatePoints().intValue()).reduce(0, (t1, t2) -> t1 + t2).longValue();
    }

    public Double calculateTotalPurchases() {
        if (transactions == null || transactions.isEmpty()) {
            return 0d;
        }
        return transactions.parallelStream().map(t -> t.getTotal().doubleValue()).reduce(0d, (t1, t2) -> t1 + t2).doubleValue();
    }
}
