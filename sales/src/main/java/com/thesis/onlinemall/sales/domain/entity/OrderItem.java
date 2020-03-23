package com.thesis.onlinemall.sales.domain.entity;

import java.math.BigDecimal;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
public class OrderItem {
  private String id;
  private String orderid;
  private String productid;
  private String productname;
  private BigDecimal unitprice;
  private int quanlity;
  private BigDecimal totalprice;
  private String url;

  public OrderItem(String id, String orderid, String productid, String productname,
      BigDecimal unitprice, int quanlity) {
    this.id = id;
    this.orderid = orderid;
    this.productid = productid;
    this.productname = productname;
    this.unitprice = unitprice;
    this.quanlity = quanlity;
    this.totalprice = this.unitprice.multiply(BigDecimal.valueOf(this.quanlity));
  }
}
