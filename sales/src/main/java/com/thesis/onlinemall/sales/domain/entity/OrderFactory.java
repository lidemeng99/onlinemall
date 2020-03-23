package com.thesis.onlinemall.sales.domain.entity;

import java.math.BigDecimal;

public class OrderFactory {

  public static Order create(String orderid, String openid, BigDecimal postage,
      String modifier
  ) {
    return new Order(orderid, openid, postage, modifier);

  }

}
