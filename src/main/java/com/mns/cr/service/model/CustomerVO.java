package com.mns.cr.service.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomerVO {

    private String name;

    private Long rewardPoints;

    private Double totalPurchases;

}
