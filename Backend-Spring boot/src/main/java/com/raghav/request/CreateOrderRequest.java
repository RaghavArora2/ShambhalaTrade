package com.raghav.request;

import com.raghav.domain.OrderType;

import com.raghav.model.Coin;
import lombok.Data;

import java.math.BigDecimal;


@Data
public class CreateOrderRequest {
    private String coinId;
    private double quantity;
    private OrderType orderType;
}
